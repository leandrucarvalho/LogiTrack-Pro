package com.logitrack.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.logitrack.model.Trip;
import com.logitrack.repository.projection.UtilizationRanking;
import com.logitrack.repository.projection.VolumeByCategory;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query(value = "select coalesce(sum(v.km_percorrida), 0) from viagens v", nativeQuery = true)
    BigDecimal totalKmAll();

    @Query(value = "select coalesce(sum(v.km_percorrida), 0) from viagens v where v.veiculo_id = :vehicleId", nativeQuery = true)
    BigDecimal totalKmByVehicle(@Param("vehicleId") Integer vehicleId);

    @Query(value = "select ve.tipo as type, count(*) as total "
            + "from viagens vi join veiculos ve on ve.id = vi.veiculo_id "
            + "group by ve.tipo order by ve.tipo", nativeQuery = true)
    List<VolumeByCategory> volumeByCategory();

    @Query(value = "select ve.id as vehicle_id, ve.placa as plate, ve.modelo as model, ve.tipo as type, "
            + "coalesce(sum(vi.km_percorrida), 0) as total_km "
            + "from viagens vi join veiculos ve on ve.id = vi.veiculo_id "
            + "group by ve.id, ve.placa, ve.modelo, ve.tipo "
            + "order by total_km desc limit 1", nativeQuery = true)
    List<UtilizationRanking> topUtilization();
}
