package com.sidv.quanlythuvien.DTO;

public class TopDTO {
    private String tenSach;
    private int soLuong;

    public TopDTO() {
    }

    @Override
    public String toString() {
        return "TopDTO{" +
                "tenSach='" + tenSach + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public TopDTO(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }
}
