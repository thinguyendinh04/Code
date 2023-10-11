package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.LoaiSach;
import com.example.myapplication.R;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> list;

    public LoaiSachSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.loaisach_item_spinner, parent, false);
        }

        LoaiSach item = list.get(position);
        if (item != null) {
            TextView tvTenLoaisach = v.findViewById(R.id.tv_Tenloaisach);
            tvTenLoaisach.setText(item.getTenLoai());

            TextView tvLoaisach = v.findViewById(R.id.tv_Maloai);
            tvLoaisach.setText(String.valueOf(item.getMaLoai()));
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.loaisach_item_spinner, parent, false);
        }

        LoaiSach item = list.get(position);
        if (item != null) {
            TextView tvTenLoaisach = v.findViewById(R.id.tv_Tenloaisach);
            tvTenLoaisach.setText(item.getTenLoai());

            TextView tvLoaisach = v.findViewById(R.id.tv_Maloai);
            tvLoaisach.setText(String.valueOf(item.getMaLoai()));
        }
        return v;
    }
}