package com.logitrack.repository.projection;

import java.math.BigDecimal;

public interface UtilizationRanking {
    Integer getVehicleId();
    String getPlate();
    String getModel();
    String getType();
    BigDecimal getTotalKm();
}
