package com.sidv.quanlythuvien.DTO;

public class LoaiSachDTO {
    private int maLoai;
    private String tenLoai;


    @Override
    public String toString() {
        return "LoaiSachDTO{" +
                "maLoai=" + maLoai +
                ", tenLoai='" + tenLoai + '\'' +
                '}';
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public LoaiSachDTO(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public LoaiSachDTO(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public LoaiSachDTO() {
    }
}
