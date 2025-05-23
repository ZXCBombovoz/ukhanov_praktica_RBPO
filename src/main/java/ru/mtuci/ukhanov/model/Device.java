package ru.mtuci.ukhanov.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Device")
public class Device {
    @GeneratedValue
    @Id
    private Long id;
    private String name, macAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device")
    private List<DeviceLicense> deviceLicenses;
}
