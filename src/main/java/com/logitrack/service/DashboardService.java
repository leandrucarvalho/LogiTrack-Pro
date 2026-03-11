package com.logitrack.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.logitrack.model.Vehicle;
import com.logitrack.repository.TripRepository;
import com.logitrack.repository.VehicleRepository;
import com.logitrack.repository.projection.UtilizationRanking;
import com.logitrack.repository.projection.VolumeByCategory;

@Service
public class DashboardService {

    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;

    public DashboardService(VehicleRepository vehicleRepository, TripRepository tripRepository) {
        this.vehicleRepository = vehicleRepository;
        this.tripRepository = tripRepository;
    }

    public List<Vehicle> listVehicles() {
        return vehicleRepository.findAllByOrderByPlateAsc();
    }

    public BigDecimal totalKmAll() {
        return tripRepository.totalKmAll();
    }

    public BigDecimal totalKmByVehicle(Integer vehicleId) {
        return tripRepository.totalKmByVehicle(vehicleId);
    }

    public List<VolumeByCategory> volumeByCategory() {
        return tripRepository.volumeByCategory();
    }

    public UtilizationRanking topUtilization() {
        List<UtilizationRanking> ranking = tripRepository.topUtilization();
        return ranking.isEmpty() ? null : ranking.get(0);
    }
}
