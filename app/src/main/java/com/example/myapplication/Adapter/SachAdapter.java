package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.Fragment.SachFragment;
import com.example.myapplication.Model.LoaiSach;
import com.example.myapplication.Model.Sach;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {
    Context context;
    SachFragment fragment;
    ArrayList<Sach> list_sach;

    public SachAdapter(@NonNull Context context, ArrayList<Sach> list_sach, SachFragment sachFragment) {
        super(context, R.layout.sach_item, list_sach);
        this.context = context;
        this.fragment = sachFragment;
        this.list_sach = list_sach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.sach_item, parent, false);
        }

        Sach item = list_sach.get(position);
        if (item != null) {
            TextView tvMasach = v.findViewById(R.id.tvMasach_Sach);
            tvMasach.setText("Mã sách: " + item.getMaSach());

            TextView tvTensach = v.findViewById(R.id.tvTensach_Sach);
            tvTensach.setText("Tên sách: " + item.getTenSach());

            TextView tvGiaThue = v.findViewById(R.id.tvGiathue_Sach);
            tvGiaThue.setText("Giá thuê: " + item.getGiaThue());

            TextView tvLoai = v.findViewById(R.id.tvMaloai_Sach);
            tvLoai.setText("Mã loại: " + item.getMaLoai());

            ImageView imgDel = v.findViewById(R.id.imgDel_Sach);
            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.xoa(String.valueOf(item.getMaSach()));
                }
            });
        }

        return v;
    }
}