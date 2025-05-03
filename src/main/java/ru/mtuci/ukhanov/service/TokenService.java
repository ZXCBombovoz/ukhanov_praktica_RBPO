package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.model.ApplicationUser;
import ru.mtuci.ukhanov.model.UserSession;

import java.util.Optional;

public interface TokenService {
    public UserSession issueTokenPair(ApplicationUser user, String deviceId);
    public Optional<UserSession> refreshTokenPair(String refreshToken, String deviceId);
    public void blockAllSessionsForUser(String email);
}
