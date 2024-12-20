package ru.mtuci.ukhanov.service;


import ru.mtuci.ukhanov.models.ApplicationUser;

public interface AutheneticationService {
    boolean authenticate(ApplicationUser user, String password);
}
