package com.logitrack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logitrack.dto.MaintenanceRequest;
import com.logitrack.dto.MaintenanceResponse;
import com.logitrack.model.Maintenance;
import com.logitrack.model.Vehicle;
import com.logitrack.service.MaintenanceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/maintenances")
@Validated
public class MaintenanceApiController {

    private final MaintenanceService maintenanceService;

    public MaintenanceApiController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public List<MaintenanceResponse> list() {
        return maintenanceService.listAll().stream()
                .map(this::toMaintenanceResponse)
                .toList();
    }

    @PostMapping
    public MaintenanceResponse create(@Valid @RequestBody MaintenanceRequest request) {
        Maintenance maintenance = new Maintenance();
        applyRequest(request, maintenance);
        return toMaintenanceResponse(maintenanceService.save(maintenance));
    }

    @PutMapping("/{id}")
    public MaintenanceResponse update(
            @PathVariable("id") @Min(value = 1, message = "id deve ser maior que zero") Integer id,
            @Valid @RequestBody MaintenanceRequest request) {
        Maintenance maintenance = maintenanceService.getById(id);
        applyRequest(request, maintenance);
        return toMaintenanceResponse(maintenanceService.save(maintenance));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Min(value = 1, message = "id deve ser maior que zero") Integer id) {
        maintenanceService.delete(id);
    }

    private void applyRequest(MaintenanceRequest request, Maintenance maintenance) {
        Vehicle vehicle = maintenanceService.getVehicle(request.getVehicleId());
        maintenance.setVehicle(vehicle);
        maintenance.setStartDate(request.getStartDate());
        maintenance.setExpectedEndDate(request.getExpectedEndDate());
        maintenance.setServiceType(request.getServiceType());
        maintenance.setEstimatedCost(request.getEstimatedCost());
        maintenance.setStatus(request.getStatus());
    }

    private MaintenanceResponse toMaintenanceResponse(Maintenance maintenance) {
        MaintenanceResponse dto = new MaintenanceResponse();
        dto.setId(maintenance.getId());
        dto.setVehicle(VehicleApiController.toVehicleDto(maintenance.getVehicle()));
        dto.setStartDate(maintenance.getStartDate());
        dto.setExpectedEndDate(maintenance.getExpectedEndDate());
        dto.setServiceType(maintenance.getServiceType());
        dto.setEstimatedCost(maintenance.getEstimatedCost());
        dto.setStatus(maintenance.getStatus());
        return dto;
    }
}
