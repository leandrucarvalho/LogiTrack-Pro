package com.logitrack.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.logitrack.model.Vehicle;
import com.logitrack.repository.projection.UtilizationRanking;
import com.logitrack.service.DashboardService;
import com.logitrack.service.MaintenanceService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final MaintenanceService maintenanceService;

    public DashboardController(DashboardService dashboardService, MaintenanceService maintenanceService) {
        this.dashboardService = dashboardService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@RequestParam(name = "vehicleId", required = false) Integer vehicleId, Model model) {
        List<Vehicle> vehicles = dashboardService.listVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("selectedVehicleId", vehicleId);

        model.addAttribute("totalKmAll", dashboardService.totalKmAll());
        if (vehicleId != null) {
            model.addAttribute("totalKmByVehicle", dashboardService.totalKmByVehicle(vehicleId));
        }

        model.addAttribute("volumeByCategory", dashboardService.volumeByCategory());
        model.addAttribute("nextMaintenances", maintenanceService.nextMaintenances());

        UtilizationRanking topUtilization = dashboardService.topUtilization();
        model.addAttribute("topUtilization", topUtilization);
        model.addAttribute("projectedMaintenanceCost", maintenanceService.projectedMonthlyCost());

        return "dashboard";
    }
}
