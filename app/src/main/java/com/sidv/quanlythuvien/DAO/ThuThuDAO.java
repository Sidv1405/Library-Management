package com.sidv.quanlythuvien.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidv.quanlythuvien.DB.DBHelper;

public class ThuThuDAO {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public ThuThuDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        this.context = context;
    }

    public boolean checkLogIn(String username, String password) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE MATT = ? AND MATKHAU = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
}
