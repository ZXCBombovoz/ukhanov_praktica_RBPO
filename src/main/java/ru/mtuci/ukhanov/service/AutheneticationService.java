package ru.mtuci.ukhanov.service;


import ru.mtuci.ukhanov.model.ApplicationUser;

public interface AutheneticationService {
    boolean authenticate(ApplicationUser user, String password);
}
