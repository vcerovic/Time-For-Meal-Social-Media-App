package com.veljkocerovic.timeformeal.api.user;

import com.veljkocerovic.timeformeal.api.recipe.Recipe;
import com.veljkocerovic.timeformeal.api.recipe.RecipeRepository;
import com.veljkocerovic.timeformeal.api.user.models.UserUpdateModel;
import com.veljkocerovic.timeformeal.api.tokens.password.PasswordResetToken;
import com.veljkocerovic.timeformeal.api.tokens.password.PasswordResetTokenRepository;
import com.veljkocerovic.timeformeal.api.tokens.verification.VerificationToken;
import com.veljkocerovic.timeformeal.api.tokens.verification.VerificationTokenRepository;
import com.veljkocerovic.timeformeal.exceptions.ImageSizeLimitException;
import com.veljkocerovic.timeformeal.exceptions.UserAlreadyExistsException;
import com.veljkocerovic.timeformeal.exceptions.UserNotFoundException;
import com.veljkocerovic.timeformeal.utils.FileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(AppUser appUser) throws UserAlreadyExistsException {
        checkIfUsernameAlreadyExists(appUser.getUsername());
        checkIfEmailAlreadyExists(appUser.getEmail());

        //Encrypt password
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        //Set default user image
        appUser.setImage("no_user_image.jpg");

        userRepository.save(appUser);
    }


    @Override
    public void deleteUser(Integer userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);

        //Delete verification token if present
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository
                .findTokenByUserId(userId);
        optionalVerificationToken.ifPresent(verificationToken -> verificationTokenRepository
                .delete(verificationToken));


        //Delete reset password token if present
        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenRepository
                .findTokenByUserId(userId);
        optionalPasswordResetToken.ifPresent(passwordResetToken -> passwordResetTokenRepository
                .delete(passwordResetToken));


        int changed = userRepository.deleteUserById(user.getId());

        if(changed < 1){
            throw new RuntimeException("Something went wrong, user is not deleted.");
        }

        //Delete his image
        if(!user.getImage().equals("no_user_image.jpg"))
            FileUtil.deleteFile(FileUtil.userImageDir + user.getImage());

        //Delete all recipe images
        user.getRecipes().forEach(recipe -> FileUtil.deleteFile(FileUtil.recipeImageDir + recipe.getImage()));
    }

    @Override
    public AppUser findUserById(Integer userId) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("AppUser with " + userId + " doesn't exist."));
    }

    @Override
    public void updateUser(Integer userId, UserUpdateModel newUserModel) throws
            UserNotFoundException, UserAlreadyExistsException, ImageSizeLimitException {
        AppUser oldUser = findUserById(userId);
        MultipartFile image = newUserModel.getImage();

        //Check if username is taken
        if(!oldUser.getUsername().equals(newUserModel.getUsername())){
            checkIfUsernameAlreadyExists(newUserModel.getUsername());
            oldUser.setUsername(newUserModel.getUsername());
        }

        //Check if email is taken
        if(!oldUser.getEmail().equals(newUserModel.getEmail())){
            checkIfEmailAlreadyExists(newUserModel.getEmail());
            oldUser.setEmail(newUserModel.getEmail());
        }

        //Check if uploaded image is smaller than 2mb
        if (image.getSize() > 2e+6) {
            throw new ImageSizeLimitException("Only 2mb image size is allowed");
        }

        //Check if new passed image doesn't equal to old one
        if(!oldUser.getImage().equals(image.getName())){
            //Renaming image file
            String newUserImageName = newUserModel.getUsername().replaceAll(" ", "_").toLowerCase()
                    + "_" + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

            //Deleting old image if doesn't equal to default
            if(!oldUser.getImage().equals("no_user_image.jpg"))
                FileUtil.deleteFile(FileUtil.userImageDir + oldUser.getImage());

            try {
                FileUtil.saveFile(FileUtil.userImageDir, newUserImageName, image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            oldUser.setImage(newUserImageName);

        }

        //Save user
        userRepository.save(oldUser);
    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User with " + email + " email doesn't exist."));
    }

    @Override
    public AppUser findUserByUsername(String username) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findByUsername(username);

        return optionalUser.orElseThrow(() -> new UserNotFoundException("User with " + username + " doesn't exist."));
    }

    @Override
    public byte[] getUserImage(Integer userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        byte[] image;
       try(InputStream inputStream =  new FileInputStream(FileUtil.userImageDir + user.getImage())){
           image = IOUtils.toByteArray(inputStream);
       } catch (IOException exc){
           throw new RuntimeException(exc);
       }

        return image;
    }

    @Override
    public List<Recipe> getAllUserRecipes(Integer userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        return recipeRepository.findAllByUserId(user.getId());
    }

    private void checkIfEmailAlreadyExists(String email) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("User with " +
                optionalUser.get().getEmail() + " email already exists.");
    }

    private void checkIfUsernameAlreadyExists(String username) throws UserAlreadyExistsException {
        //Check if username already exists
        Optional<AppUser> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) throw new UserAlreadyExistsException("User " +
                optionalUser.get().getUsername() + " already exists.");
    }
}
