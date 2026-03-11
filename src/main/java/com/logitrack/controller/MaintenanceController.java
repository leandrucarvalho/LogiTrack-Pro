package com.logitrack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.logitrack.dto.MaintenanceForm;
import com.logitrack.model.Maintenance;
import com.logitrack.model.MaintenanceStatus;
import com.logitrack.model.Vehicle;
import com.logitrack.service.MaintenanceService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("maintenances", maintenanceService.listAll());
        return "maintenances/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        MaintenanceForm form = new MaintenanceForm();
        form.setStatus(MaintenanceStatus.PENDENTE);
        fillFormModel(model, form);
        return "maintenances/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Maintenance maintenance = maintenanceService.getById(id);
        MaintenanceForm form = toForm(maintenance);
        fillFormModel(model, form);
        return "maintenances/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") MaintenanceForm form, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            fillFormModel(model, form);
            return "maintenances/form";
        }

        Maintenance maintenance = new Maintenance();
        applyForm(form, maintenance);
        maintenanceService.save(maintenance);
        return "redirect:/maintenances";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("form") MaintenanceForm form,
            BindingResult bindingResult,
            Model model) {
        Maintenance maintenance = maintenanceService.getById(id);

        if (bindingResult.hasErrors()) {
            form.setId(id);
            fillFormModel(model, form);
            return "maintenances/form";
        }

        applyForm(form, maintenance);
        maintenanceService.save(maintenance);
        return "redirect:/maintenances";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        try {
            maintenanceService.delete(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/maintenances";
    }

    private void fillFormModel(Model model, MaintenanceForm form) {
        List<Vehicle> vehicles = maintenanceService.listVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("statuses", MaintenanceStatus.values());
        model.addAttribute("form", form);
    }

    private MaintenanceForm toForm(Maintenance maintenance) {
        MaintenanceForm form = new MaintenanceForm();
        form.setId(maintenance.getId());
        form.setVehicleId(maintenance.getVehicle().getId());
        form.setStartDate(maintenance.getStartDate());
        form.setExpectedEndDate(maintenance.getExpectedEndDate());
        form.setServiceType(maintenance.getServiceType());
        form.setEstimatedCost(maintenance.getEstimatedCost());
        form.setStatus(maintenance.getStatus());
        return form;
    }

    private void applyForm(MaintenanceForm form, Maintenance maintenance) {
        Vehicle vehicle = maintenanceService.getVehicle(form.getVehicleId());
        maintenance.setVehicle(vehicle);
        maintenance.setStartDate(form.getStartDate());
        maintenance.setExpectedEndDate(form.getExpectedEndDate());
        maintenance.setServiceType(form.getServiceType());
        maintenance.setEstimatedCost(form.getEstimatedCost());
        maintenance.setStatus(form.getStatus());
    }
}
