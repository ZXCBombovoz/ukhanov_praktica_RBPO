package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.model.SignatureEntity;
import ru.mtuci.ukhanov.model.SignatureStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SignatureManagementService {

    SignatureEntity createSignature(SignatureEntity entity, String changedBy);
    SignatureEntity updateSignature(UUID id, SignatureEntity updatedEntity, String changedBy);
    void deleteSignature(UUID id, String changedBy);
    List<SignatureEntity> getAllActualSignatures();
    List<SignatureEntity> getSignaturesUpdatedAfter(LocalDateTime since);
    List<SignatureEntity> getSignaturesByIds(List<UUID> ids);
    List<SignatureEntity> getSignaturesByStatus(SignatureStatus status);
}
