package ru.mtuci.ukhanov.service;

import ru.mtuci.ukhanov.model.SignatureEntity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface SignatureService {
    byte[] sign(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
    boolean verify(byte[] data, byte[] signatureBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
    byte[] getDataToSign(SignatureEntity entity);
}
