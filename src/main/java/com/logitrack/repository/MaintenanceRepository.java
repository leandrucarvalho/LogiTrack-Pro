package com.logitrack.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.logitrack.model.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    @Query(value = "select * from manutencoes m "
            + "where m.data_inicio >= current_date "
            + "order by m.data_inicio asc limit 5", nativeQuery = true)
    List<Maintenance> nextMaintenances();

    @Query(value = "select coalesce(sum(m.custo_estimado), 0) from manutencoes m "
            + "where m.data_inicio >= date_trunc('month', current_date) "
            + "and m.data_inicio < (date_trunc('month', current_date) + interval '1 month')", nativeQuery = true)
    BigDecimal projectedMonthlyCost();
}
