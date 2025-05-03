package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.model.SignatureEntity;

public interface SignatureService {
    byte[] sign(byte[] data);
    boolean verify(byte[] data, byte[] signatureBytes);
    byte[] getDataToSign(SignatureEntity entity);
}
