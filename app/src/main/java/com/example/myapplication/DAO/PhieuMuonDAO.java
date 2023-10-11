package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.PhieuMuon;
import com.example.myapplication.Model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private Context context;
    private SimpleDateFormat dateFormat;

    public PhieuMuonDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public long insert(PhieuMuon obj) {
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());

        Date ngay = obj.getNgay();
        if (ngay != null) {
            values.put("ngay", dateFormat.format(ngay));
        } else {
            // Nếu ngay là null, đặt giá trị mặc định là ngày hiện tại
            values.put("ngay", dateFormat.format(new Date()));
        }

        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());

        return db.insert("PhieuMuon", null, values);
    }

    public int update(PhieuMuon obj) {
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", dateFormat.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        return db.update("PhieuMuon", values, "maPM=?", new String[]{String.valueOf(obj.getMaPM())});
    }

    public int delete(String id) {
        return db.delete("PhieuMuon", "maPM=?", new String[]{id});
    }

    public List<PhieuMuon> getData(String sql, String... selectionArgs) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(c.getInt(c.getColumnIndex("maPM")));
            obj.setMaTV(c.getInt(c.getColumnIndex("maTV")));
            obj.setMaSach(c.getInt(c.getColumnIndex("maSach")));
            obj.setTienThue(c.getInt(c.getColumnIndex("tienThue")));
            obj.setTraSach(c.getInt(c.getColumnIndex("traSach")));
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));

            String ngay = c.getString(c.getColumnIndex("ngay"));
            try {
                Date date = dateFormat.parse(ngay);
                obj.setNgay(date);
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(obj);
        }
        c.close();
        return list;
    }

    public List<PhieuMuon> getAll() {
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    public PhieuMuon getById(String id) {
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

//    public List<Top> getTop() {
//        String sqlTop = "SELECT maSach, COUNT(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
//        SachDAO sachDAO = new SachDAO(context);
//        List<Top> list = new ArrayList<>();
//        Cursor c = db.rawQuery(sqlTop, null);
//        while (c.moveToNext()) {
//            Top topmodel = new Top();
//            int maSach = c.getInt(c.getColumnIndex("maSach"));
//            int soLuong = c.getInt(c.getColumnIndex("soLuong"));
//            topmodel.setTenSach(sachDAO.getTenSachById(maSach));
//            topmodel.setSoLuong(soLuong);
//            list.add(topmodel);
//        }
//        c.close();
//        return list;
//    }
//
//    public int getDoanhThu(String tuNgay, String denNgay) {
//        String sqlDoanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
//        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
//        int doanhThu = 0;
//        if (c.moveToFirst()) {
//            doanhThu = c.getInt(c.getColumnIndex("doanhThu"));
//        }
//        c.close();
//        return doanhThu;
//    }
}