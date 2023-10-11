package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.ThanhVien;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ThanhvienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    Context context;
    ArrayList<ThanhVien> list;
    TextView tv_matv, tv_tentv;

    public ThanhvienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> list) {
        super(context, 0, (List<ThanhVien>) list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.thanh_vien_item_spinner, parent, false);
        }
        ThanhVien item = list.get(position);
        if (item != null) {
            tv_matv = v.findViewById(R.id.tv_matv_thanhvien);
            tv_matv.setText(item.getMaTV() + ":");
            tv_tentv = v.findViewById(R.id.tv_tentv_thanhvien);
            tv_tentv.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.thanh_vien_item_spinner, parent, false);
        }
        ThanhVien item = list.get(position);
        if (item != null) {
            tv_matv = v.findViewById(R.id.tv_matv_thanhvien);
            tv_matv.setText(item.getMaTV() + ":");
            tv_tentv = v.findViewById(R.id.tv_tentv_thanhvien);
            tv_tentv.setText(item.getHoTen());
        }
        return v;
    }
}
