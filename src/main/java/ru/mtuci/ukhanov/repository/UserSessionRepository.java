package ru.mtuci.ukhanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.ukhanov.model.SessionStatus;
import ru.mtuci.ukhanov.model.UserSession;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByRefreshToken(String refreshToken);
    List<UserSession> findByEmail(String email);
    Optional<UserSession> findByAccessToken(String accessToken);
    List<UserSession> findByEmailAndDeviceIdAndStatus(String email, String deviceId, SessionStatus status);
}