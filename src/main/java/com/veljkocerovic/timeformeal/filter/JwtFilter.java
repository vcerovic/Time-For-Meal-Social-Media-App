package com.veljkocerovic.timeformeal.filter;


import com.veljkocerovic.timeformeal.user.auth.AuthService;
import com.veljkocerovic.timeformeal.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        //Get token and username from passed jwt token
        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = jwtUtils.getUsernameFromToken(token);
        }

        //If username is not null and there isn't user logged in
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Load user details
            UserDetails userDetails = authService.loadUserByUsername(userName);

            //Validate token
            if(jwtUtils.validateToken(token, userDetails)) {
                //Create username&password token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                //Add username and password to security context (log in user)
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        //Do filter on every request
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
