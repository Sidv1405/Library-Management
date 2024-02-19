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
    private DBHelper dbHelper;
    private Context context;

    public PhieuMuonDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ArrayList<PhieuMuonDTO> getList() throws ParseException {
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
                phieuMuonDTO.setNgay(sdf.parse(cursor.getString(5)));
                phieuMuonDTO.setTraSach(cursor.getInt(6));

                listPM.add(phieuMuonDTO);
            }
        }
        cursor.close();
        return listPM;
    }

    public boolean createPhieuMuon(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuonDTO.getMaThuThu());
        contentValues.put("matv", phieuMuonDTO.getMaThanhVien());
        contentValues.put("masach", phieuMuonDTO.getMaSach());
        contentValues.put("ngay", sdf.format(phieuMuonDTO.getNgay()));
        contentValues.put("tienthue", phieuMuonDTO.getTienThue());
        contentValues.put("trasach", phieuMuonDTO.getTraSach());
        try {
            long ketQua = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
            if (ketQua != -1) {
                Toast.makeText(context, "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Thêm phiếu mượn thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean updatePhieumuon(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", phieuMuonDTO.getMaThuThu());
        contentValues.put("matv", phieuMuonDTO.getMaThanhVien());
        contentValues.put("masach", phieuMuonDTO.getMaSach());
        contentValues.put("ngay", sdf.format(phieuMuonDTO.getNgay()));
        contentValues.put("tienthue", phieuMuonDTO.getTienThue());
        contentValues.put("trasach", phieuMuonDTO.getTraSach());
        int rowsAffected = sqLiteDatabase.update("PHIEUMUON", contentValues, "mapm=?", new String[]{String.valueOf(phieuMuonDTO.getMaPhieuMuon())});
        return rowsAffected > 0;
    }

    public boolean deletePhieuMuon(int maPhieuMuon) {
        int rowsAffected = sqLiteDatabase.delete("PHIEUMUON", "mapm=?", new String[]{String.valueOf(maPhieuMuon)});
        return rowsAffected > 0;
    }

    public String getTenTVById(int maTV) {
        String tenTV = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT THANHVIEN.hoten FROM PHIEUMUON JOIN THANHVIEN ON PHIEUMUON.matv = THANHVIEN.matv WHERE PHIEUMUON.matv = ? ", new String[]{String.valueOf(maTV)});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("hoten");
            if (columnIndex != -1) {
                tenTV = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return tenTV;
    }

    public String getTenSById(int maSach) {
        String tenS = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SACH.tensach FROM PHIEUMUON JOIN SACH ON PHIEUMUON.masach = SACH.masach WHERE PHIEUMUON.masach = ? ", new String[]{String.valueOf(maSach)});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("tensach");
            if (columnIndex != -1) {
                tenS = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return tenS;
    }

    public ArrayList<String> getListTenTV() {
        ArrayList<String> listTV = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT hoten FROM THANHVIEN", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listTV.add(cursor.getString(0));
            }
        }
        cursor.close();
        return listTV;
    }

    public ArrayList<String> getListTenS() {
        ArrayList<String> listS = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tensach FROM SACH", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listS.add(cursor.getString(0));
            }
        }
        cursor.close();
        return listS;
    }

    public int getGiaSachByTen(String tenSach) {
        int giaS = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT giathue FROM SACH WHERE tensach = ? ", new String[]{tenSach});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("giathue");
            if (columnIndex != -1) {
                giaS = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return giaS;
    }

    public int getMaTVById(String tenTV){
        int maTV = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matv FROM THANHVIEN WHERE hoten = ? ", new String[]{tenTV});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("matv");
            if (columnIndex != -1) {
                maTV = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return maTV;
    }

    public int getMaSById(String tenS) {
        int maS = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT masach FROM SACH WHERE tensach = ? ", new String[]{tenS});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("masach");
            if (columnIndex != -1) {
                maS = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return maS;
    }
}
