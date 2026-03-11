package com.logitrack.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.logitrack.model.Maintenance;
import com.logitrack.model.Vehicle;
import com.logitrack.repository.MaintenanceRepository;
import com.logitrack.repository.VehicleRepository;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, VehicleRepository vehicleRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Maintenance> listAll() {
        return maintenanceRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
    }

    public Maintenance getById(Integer id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutencao nao encontrada"));
    }

    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public void delete(Integer id) {
        maintenanceRepository.deleteById(id);
    }

    public Vehicle getVehicle(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veiculo nao encontrado"));
    }

    public List<Vehicle> listVehicles() {
        return vehicleRepository.findAllByOrderByPlateAsc();
    }

    public List<Maintenance> nextMaintenances() {
        return maintenanceRepository.nextMaintenances();
    }

    public BigDecimal projectedMonthlyCost() {
        return maintenanceRepository.projectedMonthlyCost();
    }
}
