package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Model.ThanhVien;
import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    public ThanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return db.insert("ThanhVien",null,values);
    }
    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.getHoTen());
        values.put("namSinh",obj.getNamSinh());
        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(obj.getMaTV())});
    }
    public int delete(String id){
        return db.delete("ThanhVien","maTV=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<ThanhVien>getData(String sql, String...selectionArgs){
        List<ThanhVien>list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            list.add(obj);
            Log.i("//========",obj.toString());
        }
        return list;
    }
    public List<ThanhVien>getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    public ThanhVien getById(String valueOf) {
        String sql = "SELECT * FROM Thanhvien WHERE maTV=?";
        List<ThanhVien>list = getData(sql,valueOf);
        return list.get(1);
    }
}
