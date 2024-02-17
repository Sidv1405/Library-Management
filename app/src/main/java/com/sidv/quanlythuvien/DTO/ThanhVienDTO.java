package com.sidv.quanlythuvien.DTO;

public class ThanhVienDTO {
    public int maTV;
    public String hoTen;
    public String namSinh;

    @Override
    public String toString() {
        return "ThanhVienDTO{" +
                "maTV=" + maTV +
                ", hoTen='" + hoTen + '\'' +
                ", namSinh='" + namSinh + '\'' +
                '}';
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public ThanhVienDTO(int maTV, String hoTen, String namSinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVienDTO(String hoTen, String namSinh) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVienDTO() {
    }
}


