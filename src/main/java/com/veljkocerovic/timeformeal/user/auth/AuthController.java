package com.veljkocerovic.timeformeal.user.auth;

import com.veljkocerovic.timeformeal.user.models.JwtRequestModel;
import com.veljkocerovic.timeformeal.user.models.JwtResponseModel;
import com.veljkocerovic.timeformeal.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;


    //AUTHENTICATE
    @PostMapping("/login")
    public JwtResponseModel authenticateUser(@RequestBody JwtRequestModel jwtRequest) throws Exception {
        //Authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }

        //Create spring user
        UserDetails userDetails = authService.loadUserByUsername(jwtRequest.getUsername());

        //Generate jwt token
        String token = jwtUtils.generateToken(userDetails);

        //Send back jwt token
        return new JwtResponseModel(token);
    }


}
