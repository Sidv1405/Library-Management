package com.sidv.quanlythuvien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.PhieuMuonDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonDAO {

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DBHelper dbHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PhieuMuonDAO(Context context) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public ArrayList<PhieuMuonDTO> getList() {
        ArrayList<PhieuMuonDTO> listPM = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO();
                phieuMuonDTO.setMaPhieuMuon(cursor.getInt(0));
                phieuMuonDTO.setMaThuThu(cursor.getString(1));
                phieuMuonDTO.setMaThanhVien(cursor.getInt(2));
                phieuMuonDTO.setMaSach(cursor.getInt(3));
                phieuMuonDTO.setTienThue(cursor.getInt(4));
                try {
                    Date ngay = sdf.parse(cursor.getString(6));
                    phieuMuonDTO.setNgay(ngay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                phieuMuonDTO.setTraSach(cursor.getInt(6));

                listPM.add(phieuMuonDTO);
            }
        }
        cursor.close();
        return listPM;
    }

    public int createPhieuMuon(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuonDTO.getMaThuThu());
        contentValues.put("matv", phieuMuonDTO.getMaThanhVien());
        contentValues.put("masach", phieuMuonDTO.getMaSach());
        contentValues.put("ngay", sdf.format(phieuMuonDTO.getNgay()));
        contentValues.put("tienthue", phieuMuonDTO.getTienThue());
        contentValues.put("trasach", phieuMuonDTO.getTraSach());
        try {
            long ketQua = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
            Toast.makeText(context, "Create success", Toast.LENGTH_SHORT).show();
            return (int) ketQua;

        } catch (SQLException e) {
            //ap dung khi su dung cau lenh query
            Toast.makeText(context, "Fail to add phieu muon" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public int updatePhieumuon(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuonDTO.getMaThuThu());
        contentValues.put("matv", phieuMuonDTO.getMaThanhVien());
        contentValues.put("masach", phieuMuonDTO.getMaSach());
        contentValues.put("ngay", sdf.format(phieuMuonDTO.getNgay()));
        contentValues.put("tienthue", phieuMuonDTO.getTienThue());
        contentValues.put("trasach", phieuMuonDTO.getTraSach());
        return sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm=?", new String[]{String.valueOf(phieuMuonDTO.getMaPhieuMuon())});
    }

    public boolean deletePhieuMuon(int maPhieuMuon) {
        int check = sqLiteDatabase.delete("PHIEUMUON", "mapm=?", new String[]{String.valueOf(maPhieuMuon)});
        if (check <= 0) {
            return false;
        } else {
            return true;
        }
    }

}
