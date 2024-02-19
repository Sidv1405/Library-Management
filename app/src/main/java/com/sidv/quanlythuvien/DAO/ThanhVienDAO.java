package com.sidv.quanlythuvien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.ThanhVienDTO;

import java.util.ArrayList;

public class ThanhVienDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public ThanhVienDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<ThanhVienDTO> getList() {
        ArrayList<ThanhVienDTO> listTV = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listTV.add(new ThanhVienDTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        }
        cursor.close();
        return listTV;
    }

    public boolean createThanhVien(ThanhVienDTO thanhVienDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", thanhVienDTO.getHoTen());
        contentValues.put("namsinh", thanhVienDTO.getNamSinh());
        try {
            long ketQua = sqLiteDatabase.insert("THANHVIEN", null, contentValues);
            if (ketQua != -1) {
                Toast.makeText(context, "Thêm thành công thành viên", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean updateThanhVien(ThanhVienDTO thanhVienDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", thanhVienDTO.getHoTen());
        contentValues.put("namsinh", thanhVienDTO.getNamSinh());
        return sqLiteDatabase.update("THANHVIEN", contentValues, "matv=?", new String[]{String.valueOf(thanhVienDTO.getMaTV())}) > 0;
    }

    public boolean deleteThanhVien(int maThanhVien) {
        return sqLiteDatabase.delete("THANHVIEN", "matv=?", new String[]{String.valueOf(maThanhVien)}) > 0;
    }

    public ArrayList<String> getListTenTV() {
        ArrayList<String> listTenTV = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT hoten FROM THANHVIEN", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listTenTV.add(cursor.getString(0));
            }
        }
        cursor.close();
        return listTenTV;
    }
}
