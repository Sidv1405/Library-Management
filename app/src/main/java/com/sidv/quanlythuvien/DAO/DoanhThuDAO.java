package com.sidv.quanlythuvien.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidv.quanlythuvien.DB.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuDAO {
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public DoanhThuDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) AS doanhthu FROM PHIEUMUON WHERE NGAY BETWEEN ? AND ?", new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
                doanhThu = Integer.parseInt(String.valueOf(cursor.getInt(0)));

        }
        return doanhThu;
    }
}
