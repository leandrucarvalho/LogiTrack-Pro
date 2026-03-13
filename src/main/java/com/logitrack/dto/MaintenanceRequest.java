package com.logitrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.logitrack.model.MaintenanceStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MaintenanceRequest {

    @NotNull(message = "vehicleId e obrigatorio")
    @Min(value = 1, message = "vehicleId deve ser maior que zero")
    private Integer vehicleId;

    @NotNull(message = "data de inicio e obrigatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expectedEndDate;

    @NotBlank(message = "tipo de servico e obrigatorio")
    @Size(max = 100, message = "tipo de servico deve ter no maximo 100 caracteres")
    private String serviceType;

    @NotNull(message = "custo estimado e obrigatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "custo estimado deve ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2, message = "custo estimado deve ter ate 8 digitos e 2 casas decimais")
    private BigDecimal estimatedCost;

    @NotNull(message = "status e obrigatorio")
    private MaintenanceStatus status;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }
}
