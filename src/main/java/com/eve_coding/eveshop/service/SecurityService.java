package com.eve_coding.eveshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }
        return authentication.isAuthenticated();
    }

    public void autoLogin(String email, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());

        authenticationManager.authenticate(token);

        if (token.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
