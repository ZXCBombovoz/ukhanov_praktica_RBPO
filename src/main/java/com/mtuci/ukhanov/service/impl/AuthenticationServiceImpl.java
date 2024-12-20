package com.mtuci.ukhanov.service.impl;

import com.mtuci.ukhanov.models.ApplicationUser;
import com.mtuci.ukhanov.service.AutheneticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AutheneticationService {
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(ApplicationUser user, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), password)
        ).isAuthenticated();
    }
}
