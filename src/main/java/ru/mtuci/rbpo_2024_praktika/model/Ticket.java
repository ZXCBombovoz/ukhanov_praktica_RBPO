package ru.mtuci.rbpo_2024_praktika.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Ticket {
    private Date nowDate, activationDate, expirationDate;
    private Long expiration;

    private Long userID, deviceID;
    private boolean isBlockedLicence;
    private String digitalSignature;
    private String description;
}
