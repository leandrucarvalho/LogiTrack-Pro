package com.logitrack.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private BigDecimal totalKmAll;
    private BigDecimal totalKmByVehicle;
    private List<VolumeByCategoryDto> volumeByCategory;
    private List<MaintenanceResponse> nextMaintenances;
    private UtilizationRankingDto topUtilization;
    private BigDecimal projectedMaintenanceCost;

    public BigDecimal getTotalKmAll() {
        return totalKmAll;
    }

    public void setTotalKmAll(BigDecimal totalKmAll) {
        this.totalKmAll = totalKmAll;
    }

    public BigDecimal getTotalKmByVehicle() {
        return totalKmByVehicle;
    }

    public void setTotalKmByVehicle(BigDecimal totalKmByVehicle) {
        this.totalKmByVehicle = totalKmByVehicle;
    }

    public List<VolumeByCategoryDto> getVolumeByCategory() {
        return volumeByCategory;
    }

    public void setVolumeByCategory(List<VolumeByCategoryDto> volumeByCategory) {
        this.volumeByCategory = volumeByCategory;
    }

    public List<MaintenanceResponse> getNextMaintenances() {
        return nextMaintenances;
    }

    public void setNextMaintenances(List<MaintenanceResponse> nextMaintenances) {
        this.nextMaintenances = nextMaintenances;
    }

    public UtilizationRankingDto getTopUtilization() {
        return topUtilization;
    }

    public void setTopUtilization(UtilizationRankingDto topUtilization) {
        this.topUtilization = topUtilization;
    }

    public BigDecimal getProjectedMaintenanceCost() {
        return projectedMaintenanceCost;
    }

    public void setProjectedMaintenanceCost(BigDecimal projectedMaintenanceCost) {
        this.projectedMaintenanceCost = projectedMaintenanceCost;
    }
}
