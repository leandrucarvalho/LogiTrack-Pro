package com.logitrack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.logitrack.exception.ResourceNotFoundException;
import com.logitrack.model.Maintenance;
import com.logitrack.model.Vehicle;
import com.logitrack.repository.MaintenanceRepository;
import com.logitrack.repository.VehicleRepository;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private MaintenanceService maintenanceService;

    @Test
    void getByIdReturnsMaintenanceWhenFound() {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(7);
        when(maintenanceRepository.findById(7)).thenReturn(Optional.of(maintenance));

        Maintenance result = maintenanceService.getById(7);

        assertEquals(7, result.getId());
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(maintenanceRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.getById(99));
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(maintenanceRepository.existsById(12)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.delete(12));
    }

    @Test
    void getVehicleReturnsVehicleWhenFound() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(3);
        when(vehicleRepository.findById(3)).thenReturn(Optional.of(vehicle));

        Vehicle result = maintenanceService.getVehicle(3);

        assertEquals(3, result.getId());
    }
}
