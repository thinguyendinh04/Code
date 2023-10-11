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

import com.example.myapplication.Fragment.LoaiSachFragment;
import com.example.myapplication.Model.LoaiSach;
import com.example.myapplication.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    LoaiSachFragment fragment;
    private ArrayList<LoaiSach>lists;
    TextView tvMaLoai,tvTenLoai;
    ImageView imgDeletels;
    public LoaiSachAdapter(@NonNull Context context, ArrayList<LoaiSach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item,null);
        }
        final LoaiSach item = lists.get(position);
        if(item != null){
            tvMaLoai = v.findViewById(R.id.tvMaLoai);
            tvMaLoai.setText("Mã loại: "+item.getMaLoai());
            tvTenLoai = v.findViewById(R.id.tvTenLoai);
            tvTenLoai.setText("Tên loại: "+item.getTenLoai());
            imgDeletels = v.findViewById(R.id.imgDel);
        }
        imgDeletels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaLoai()));
            }
        });
        return v;
    }
}
