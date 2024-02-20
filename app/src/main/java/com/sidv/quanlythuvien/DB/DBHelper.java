package com.sidv.quanlythuvien.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static final String name = "PLIB";
    static final int version = 11;

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tao bang Thu Thu
        String createTbThuThu = "CREATE TABLE THUTHU (" +
                "matt TEXT PRIMARY KEY," +
                "hoten TEXT NOT NULL," +
                "matkhau TEXT NOT NULL)";
        db.execSQL(createTbThuThu);

        //Tao bang Thanh Vien
        String createTbThanhVien = "CREATE TABLE THANHVIEN (" +
                "matv INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoten TEXT NOT NULL," +
                "namsinh TEXT NOT NULL)";
        db.execSQL(createTbThanhVien);

        //Tao bang Loai Sach
        String createTbLoaiSach = " CREATE TABLE LOAISACH (" +
                "maloai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenloai TEXT NOT NULL)";
        db.execSQL(createTbLoaiSach);

        //Tao bang Sach
        String createTbSach = "CREATE TABLE SACH (" +
                "masach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tensach TEXT NOT NULL," +
                "giathue INTEGER NOT NULL," +
                "maloai INTEGER REFERENCES LOAISACH(MALOAI))";
        db.execSQL(createTbSach);

        // Tao bang Loai Phieu Muon
        String creaTbPhieuMuon = "CREATE TABLE PHIEUMUON (" +
                "mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matt TEXT REFERENCES THUTHU(MATT)," +
                "matv INTEGER REFERENCES THANHVIEN(MATV)," +
                "masach INTEGER REFERENCES SACH(MASACH)," +
                "tienthue INTEGER NOT NULL," +
                "ngay DATE NOT NULL," +
                "trasach INTEGER NOT NULL )";
        db.execSQL(creaTbPhieuMuon);

        //inert du lieu mau
        String insThuThu = "INSERT INTO THUTHU VALUES('tt1','Thủ Thư 1', 'tt1'),('tt2','Thủ Thư 2', 'tt2'),('tt3','Thủ Thư 3', 'tt3'),('admin','admin', 'admin')";
        db.execSQL(insThuThu);
        String insThanhVien = "INSERT INTO THANHVIEN VALUES (1, 'Đặng Văn Sĩ','2000'),(2, 'Lê Thu Phương','2002'),(3, 'Nguyễn Ngọc Linh','1999'),(4, 'Nguyễn Đức Anh ','2000')";
        db.execSQL(insThanhVien);
        String insLoaiSach = "INSERT INTO LOAISACH VALUES (1,'Công Nghệ Thông Tin'),(2,'Kinh Tế-chính Trị'),(3, 'Thiết kế đồ hoạ'), (4, 'Truyện trinh thám')";
        db.execSQL(insLoaiSach);
        String insSach = "INSERT INTO SACH VALUES (1, 'Lập trình java 1', 1500, 1),(2, 'Lập trình java 2', 5500, 1),(3, 'Android cơ bản', 2000, 1),(4, 'Android nâng cao', 3500, 2),(5, 'Kotlin', 2000, 3)";
        db.execSQL(insSach);
        String insPhieuMuon = "INSERT INTO PHIEUMUON VALUES (1, 'tt1', 1, 2, 5500, '19/02/2024',1 ),(2, 'tt1', 1, 3, 4500, '19/02/2024',0 ),(3, 'tt3', 3, 4, 3500, '19/02/2024',1 )";
        db.execSQL(insPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
