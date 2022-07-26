package com.veljkocerovic.timeformeal.user.auth;

import com.veljkocerovic.timeformeal.user.model.JwtRequestModel;
import com.veljkocerovic.timeformeal.user.model.JwtResponseModel;
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
    @PostMapping("/authenticate")
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
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userDetails = authService.loadUserByUsername(jwtRequest.getUsername());

        //Generate token
        String token = jwtUtils.generateToken(userDetails);

        return new JwtResponseModel(token);
    }


}
