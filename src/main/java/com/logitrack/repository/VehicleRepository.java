package com.logitrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logitrack.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findAllByOrderByPlateAsc();
}
