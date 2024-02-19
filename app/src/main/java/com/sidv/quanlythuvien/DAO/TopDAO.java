package com.sidv.quanlythuvien.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidv.quanlythuvien.DB.DBHelper;
import com.sidv.quanlythuvien.DTO.SachDTO;
import com.sidv.quanlythuvien.DTO.TopDTO;

import java.util.ArrayList;

public class TopDAO {

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;

    public TopDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<TopDTO> getList() {
        ArrayList<TopDTO> listTop = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SACH.tensach, COUNT(PHIEUMUON.masach) AS soluong FROM PHIEUMUON JOIN SACH ON PHIEUMUON.masach = SACH.masach GROUP BY PHIEUMUON.masach ORDER BY soluong DESC LIMIT 10", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                listTop.add(new TopDTO(cursor.getString(0), cursor.getInt(1)));
            }
        }
        cursor.close();
        return listTop;
    }
}
