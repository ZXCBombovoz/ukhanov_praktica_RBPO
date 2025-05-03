package ru.mtuci.ukhanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.ukhanov.model.SignatureAudit;

public interface SignatureAuditRepository extends JpaRepository<SignatureAudit, Long> {
}