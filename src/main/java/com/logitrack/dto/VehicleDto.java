package com.logitrack.dto;

public class VehicleDto {

    private Integer id;
    private String plate;
    private String model;
    private String type;
    private Integer year;

    public VehicleDto() {
    }

    public VehicleDto(Integer id, String plate, String model, String type, Integer year) {
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.type = type;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
