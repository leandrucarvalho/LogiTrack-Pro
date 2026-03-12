package com.logitrack.dto;

public class VolumeByCategoryDto {

    private String type;
    private long total;

    public VolumeByCategoryDto() {
    }

    public VolumeByCategoryDto(String type, long total) {
        this.type = type;
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
