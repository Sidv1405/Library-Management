package com.sidv.quanlythuvien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.LoaiSachDTO;

import java.util.ArrayList;

public class LoaiSachDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public LoaiSachDAO(Context context) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public ArrayList<LoaiSachDTO> getList() {
        ArrayList<LoaiSachDTO> listLS = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToFirst()) {
                listLS.add(new LoaiSachDTO(cursor.getInt(0), cursor.getString(1)));
            }
        }
        cursor.close();
        return listLS;
    }

    public boolean createLoaiSach(LoaiSachDTO loaiSachDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSachDTO.getTenLoai());
        try {
            long ketQua = sqLiteDatabase.insert("LOAISACH", null, contentValues);
            if (ketQua != -1) {
                Toast.makeText(context, "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(context, "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Thêm loại sách thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean updateLoaiSach(LoaiSachDTO loaiSachDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSachDTO.getTenLoai());
        int rowsAffected = sqLiteDatabase.update("LOAISACH", contentValues, "maloai=?", new String[]{String.valueOf(loaiSachDTO.getMaLoai())});
        return rowsAffected > 0;
    }

    public boolean deleteLoaiSach(int maLoaiSach) {
        int rowsAffected = sqLiteDatabase.delete("LOAISACH", "maloai=?", new String[]{String.valueOf(maLoaiSach)});
        return rowsAffected > 0;
    }
}
