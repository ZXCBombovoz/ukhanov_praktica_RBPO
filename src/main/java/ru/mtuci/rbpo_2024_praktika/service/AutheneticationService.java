package ru.mtuci.rbpo_2024_praktika.service;


import com.mtuci.ukhanov.models.ApplicationUser;

public interface AutheneticationService {
    boolean authenticate(ApplicationUser user, String password);
}
