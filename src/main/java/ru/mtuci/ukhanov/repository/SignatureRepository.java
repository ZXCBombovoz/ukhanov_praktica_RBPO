package ru.mtuci.ukhanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.ukhanov.model.SignatureEntity;
import ru.mtuci.ukhanov.model.SignatureStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SignatureRepository extends JpaRepository<SignatureEntity, UUID> {
    List<SignatureEntity> findByStatus(SignatureStatus status);
    List<SignatureEntity> findByUpdatedAtAfter(LocalDateTime since);
    List<SignatureEntity> findByIdIn(List<UUID> ids);
}