package com.sidv.quanlythuvien.DTO;

public class TopDTO {
    private String TENSACH;
    private int SOLUONG;

    public TopDTO() {
    }

    public TopDTO(String TENSACH, int SOLUONG) {
        this.TENSACH = TENSACH;
        this.SOLUONG = SOLUONG;
    }

    public String getTENSACH() {
        return TENSACH;
    }

    public void setTENSACH(String TENSACH) {
        this.TENSACH = TENSACH;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }
}
