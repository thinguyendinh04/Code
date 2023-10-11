package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, "PNLibary", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_Thuthu = "" +
                "CREATE TABLE ThuThu(\n" +
                "maTT text PRIMARY KEY,\n" +
                "hoTen text NOT NULL,\n" +
                "matKhau text NOT NULL\n" +
                ")";
        db.execSQL(tb_Thuthu);
        String tb_Thanhvien = "" +
                "CREATE TABLE ThanhVien(\n" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "hoTen text NOT NULL,\n" +
                "namSinh text NOT NULL\n" +
                ")";
        db.execSQL(tb_Thanhvien);
        String tb_loaisach = "" +
                "CREATE TABLE LoaiSach(\n" +
                "maLoai Integer PRIMARY KEY AUTOINCREMENT ,\n" +
                "tenLoai text NOT NULL\n" +
                ")";
        db.execSQL(tb_loaisach);
        String tb_sach = "" +
                "CREATE TABLE Sach(\n" +
                "maSach Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "tenSach text NOT NULL,\n" +
                "giaThue Integer NOT NULL,\n" +
                "maLoai Integer REFERENCES LoaiSach(maloai)\n" +
                ")";
        db.execSQL(tb_sach);

        String tb_phieumuon = "" +
                "CREATE TABLE PhieuMuon(\n" +
                "maPM Integer PRIMARY KEY AUTOINCREMENT,\n" +
                "maTT text REFERENCES ThuThu(maTT),\n" +
                "maTV INTEGER REFERENCES ThanhVien(maTV),\n" +
                "maSach Integer REFERENCES Sach(maSach),\n" +
                "tienThue Integer NOT NULL,\n" +
                "ngay date NOT NULL,\n" +
                "traSach Integer NOT NULL)";
        db.execSQL(tb_phieumuon);

        //Account Thu Thu
        db.execSQL("Insert Into ThuThu Values (1, 'thinguyen04', '123456')");
        //Loai sach
        db.execSQL("Insert Into LoaiSach Values (1,'Cong nghe thong tin')");
        //Thanh vien
        db.execSQL("Insert Into Thanhvien Values (1,'Dinh Thi', '2004')");
        //Sach
        db.execSQL("Insert Into Sach Values(1,'Java 1', 10000, 1)");
        //PhieuMuon
        db.execSQL("INSERT INTO PhieuMuon (maTT, maTV, maSach, tienThue, ngay, traSach) VALUES ('1', 1, 1, 5000, '2023-10-01', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
