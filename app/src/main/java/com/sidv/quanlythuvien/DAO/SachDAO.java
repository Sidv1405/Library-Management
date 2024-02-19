package com.sidv.quanlythuvien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.LoaiSachDTO;
import com.sidv.quanlythuvien.DTO.SachDTO;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public SachDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<SachDTO> getList() {
        ArrayList<SachDTO> listS = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listS.add(new SachDTO(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            }
        }
        cursor.close();
        return listS;
    }

    public boolean createSach(SachDTO sachDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sachDTO.getTenSach());
        contentValues.put("giathue", sachDTO.getGiaThue());
        contentValues.put("maloai", sachDTO.getMaLoai());
        try {
            long ketQua = sqLiteDatabase.insert("SACH", null, contentValues);
            if (ketQua != -1) {
                Toast.makeText(context, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean updateSach(SachDTO sachDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sachDTO.getTenSach());
        contentValues.put("giathue", sachDTO.getGiaThue());
        contentValues.put("maloai", sachDTO.getMaLoai());
        return sqLiteDatabase.update("SACH", contentValues, "masach=?", new String[]{String.valueOf(sachDTO.getMaSach())}) > 0;
    }

    public boolean deleteSach(int maSach) {
        return sqLiteDatabase.delete("SACH", "masach=?", new String[]{String.valueOf(maSach)}) > 0;
    }

    public String getTenLSById(int maLS) {
        String tenLS = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT LOAISACH.tenloai FROM SACH JOIN LOAISACH ON SACH.maloai = LOAISACH.maloai WHERE SACH.maloai = ? ", new String[]{String.valueOf(maLS)});
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("tenloai");
            if (columnIndex != -1) {
                tenLS = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return tenLS;
    }

    public ArrayList<String> getListTenS() {
        ArrayList<String> listTenS = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tensach FROM SACH", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listTenS.add(cursor.getString(0));
            }
        }
        cursor.close();
        return listTenS;
    }
}
