package ru.mtuci.ukhanov.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDeviceRequest {
    private Long id, user_id;
    private String name, macAddress;
}
