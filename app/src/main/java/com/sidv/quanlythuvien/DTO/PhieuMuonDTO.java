package com.sidv.quanlythuvien.DTO;

import java.util.Date;

public class PhieuMuonDTO {
    private int maPhieuMuon;
    private String maThuThu;
    private int maThanhVien;
    private int maSach;
    private int tienThue;
    private Date ngay;
    private int traSach;


    public PhieuMuonDTO() {
    }

    @Override
    public String toString() {
        return "PhieuMuonDTO{" +
                "maPhieuMuon=" + maPhieuMuon +
                ", maThuThu='" + maThuThu + '\'' +
                ", maThanhVien=" + maThanhVien +
                ", maSach=" + maSach +
                ", tienThue=" + tienThue +
                ", ngay=" + ngay +
                ", traSach=" + traSach +
                '}';
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public PhieuMuonDTO(int maPhieuMuon, String maThuThu, int maThanhVien, int maSach, int tienThue, Date ngay, int traSach) {
        this.maPhieuMuon = maPhieuMuon;
        this.maThuThu = maThuThu;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
        this.traSach = traSach;
    }
}
