package ru.mtuci.ukhanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.ukhanov.model.SignatureHistory;

public interface SignatureHistoryRepository extends JpaRepository<SignatureHistory, Long> {
}