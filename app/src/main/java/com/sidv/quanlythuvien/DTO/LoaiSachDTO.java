package com.sidv.quanlythuvien.DTO;

public class LoaiSachDTO {
    private int MALOAI;
    private String TENLOAI;


    public LoaiSachDTO() {
        this.MALOAI = MALOAI;
        this.TENLOAI = TENLOAI;
    }

    public LoaiSachDTO(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    @Override
    public String toString() {
        return MALOAI + "|" + TENLOAI;
    }
}
