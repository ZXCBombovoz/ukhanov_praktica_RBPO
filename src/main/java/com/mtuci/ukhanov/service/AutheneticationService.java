package com.mtuci.ukhanov.service;


import com.mtuci.ukhanov.models.ApplicationUser;

public interface AutheneticationService {
    boolean authenticate(ApplicationUser user, String password);
}
