package ru.mtuci.rbpo_2024_praktika.repositories;

import com.mtuci.ukhanov.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByLogin(String login);
}
