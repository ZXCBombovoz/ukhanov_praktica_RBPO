package ru.mtuci.rbpo_2024_praktika.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataLicenseTypeRequest {
    private Long id;
    private String name, description;
    private Long default_duration;
}
