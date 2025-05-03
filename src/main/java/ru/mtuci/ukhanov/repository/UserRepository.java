package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByLogin(String login);
    Optional<ApplicationUser> findByEmail(String email);
}