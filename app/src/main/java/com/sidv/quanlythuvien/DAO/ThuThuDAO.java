package com.sidv.quanlythuvien.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.LoaiSachDTO;
import com.sidv.quanlythuvien.DTO.ThuThuDTO;

public class ThuThuDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public ThuThuDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public boolean checkLogIn(String username, String password) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE MATT = ? AND MATKHAU = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkAccount(String username) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE MATT = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean createThuThu(ThuThuDTO thuThuDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", thuThuDTO.getMaTT());
        contentValues.put("hoten", thuThuDTO.getHoTen());
        contentValues.put("matkhau", thuThuDTO.getMatKhau());
        try {
            long ketQua = sqLiteDatabase.insert("THUTHU", null, contentValues);
            if (ketQua != -1) {
                Toast.makeText(context, "Thêm thủ thư thành công", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Thêm thủ thư thất bại" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean updateMatKhauThuThu(String maTT, String matKhauMoi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matkhau", matKhauMoi);
        try {
            int rowsAffected = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{maTT});
            if (rowsAffected > 0) {
                Toast.makeText(context, "Cập nhật mật khẩu thủ thư thành công", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Cập nhật mật khẩu thủ thư thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean kiemTraMatKhauThuThu(String maTT, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "maTT = ? AND matKhau = ?";
        String[] selectionArgs = {maTT, matKhau};
        Cursor cursor = db.query("ThuThu", null, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
}
