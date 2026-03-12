package com.logitrack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logitrack.dto.DashboardResponse;
import com.logitrack.dto.MaintenanceResponse;
import com.logitrack.dto.UtilizationRankingDto;
import com.logitrack.dto.VolumeByCategoryDto;
import com.logitrack.model.Maintenance;
import com.logitrack.repository.projection.UtilizationRanking;
import com.logitrack.repository.projection.VolumeByCategory;
import com.logitrack.service.DashboardService;
import com.logitrack.service.MaintenanceService;

@RestController
@RequestMapping("/api")
public class DashboardApiController {

    private final DashboardService dashboardService;
    private final MaintenanceService maintenanceService;

    public DashboardApiController(DashboardService dashboardService, MaintenanceService maintenanceService) {
        this.dashboardService = dashboardService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(@RequestParam(name = "vehicleId", required = false) Integer vehicleId) {
        DashboardResponse response = new DashboardResponse();
        response.setTotalKmAll(dashboardService.totalKmAll());
        response.setTotalKmByVehicle(vehicleId == null ? null : dashboardService.totalKmByVehicle(vehicleId));
        response.setVolumeByCategory(dashboardService.volumeByCategory().stream()
                .map(this::toVolumeByCategoryDto)
                .toList());
        response.setNextMaintenances(maintenanceService.nextMaintenances().stream()
                .map(this::toMaintenanceResponse)
                .toList());
        response.setTopUtilization(toUtilizationRankingDto(dashboardService.topUtilization()));
        response.setProjectedMaintenanceCost(maintenanceService.projectedMonthlyCost());
        return response;
    }

    private VolumeByCategoryDto toVolumeByCategoryDto(VolumeByCategory row) {
        return new VolumeByCategoryDto(row.getType(), row.getTotal());
    }

    private UtilizationRankingDto toUtilizationRankingDto(UtilizationRanking ranking) {
        if (ranking == null) {
            return null;
        }
        UtilizationRankingDto dto = new UtilizationRankingDto();
        dto.setVehicleId(ranking.getVehicleId());
        dto.setPlate(ranking.getPlate());
        dto.setModel(ranking.getModel());
        dto.setType(ranking.getType());
        dto.setTotalKm(ranking.getTotalKm());
        return dto;
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
