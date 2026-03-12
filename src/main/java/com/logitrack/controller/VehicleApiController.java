package com.logitrack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logitrack.dto.VehicleDto;
import com.logitrack.model.Vehicle;
import com.logitrack.service.DashboardService;

@RestController
@RequestMapping("/api")
public class VehicleApiController {

    private final DashboardService dashboardService;

    public VehicleApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/vehicles")
    public List<VehicleDto> vehicles() {
        return dashboardService.listVehicles().stream()
                .map(VehicleApiController::toVehicleDto)
                .toList();
    }

    public static VehicleDto toVehicleDto(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return new VehicleDto(vehicle.getId(), vehicle.getPlate(), vehicle.getModel(),
                vehicle.getType() == null ? null : vehicle.getType().name(),
                vehicle.getYear());
    }
}
