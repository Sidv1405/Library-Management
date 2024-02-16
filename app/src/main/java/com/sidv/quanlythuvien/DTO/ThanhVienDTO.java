package com.sidv.quanlythuvien.DTO;

public class ThanhVienDTO {
    public int MATV;
    public String HOTEN;
    public String NAMSINH;

//    public ThanhVienDTO() {
//    }

    public ThanhVienDTO() {
        this.MATV = MATV;
        this.HOTEN = HOTEN;
        this.NAMSINH = NAMSINH;
    }

    public ThanhVienDTO(String HOTEN, String NAMSINH) {
        this.HOTEN = HOTEN;
        this.NAMSINH = NAMSINH;
    }

    public void setMATV(int MATV) {
        this.MATV = MATV;
    }

    public int getMATV() {
        return MATV;
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getNAMSINH() {
        return NAMSINH;
    }

    public void setNAMSINH(String NAMSINH) {
        this.NAMSINH = NAMSINH;
    }

    @Override
    public String toString() {
        return HOTEN ;
    }

}


