package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Sach;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    Context context;
    ArrayList<Sach> list_sach;
    TextView tvmasach, tvtensach;

    public SachSpinnerAdapter(@NonNull Context context, ArrayList<Sach> list_sach) {
        super(context, 0, (List<Sach>) list_sach);
        this.context = context;
        this.list_sach = list_sach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.sach_item_spinner, parent, false);
        }
        Sach item = list_sach.get(position);
        if (item != null) {
            tvmasach = v.findViewById(R.id.tv_masach_sach);
            tvmasach.setText(item.getMaSach() + ":");
            tvtensach = v.findViewById(R.id.tv_tensach_sach);
            tvtensach.setText(item.getTenSach());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.sach_item_spinner, parent, false);
        }
        Sach item = list_sach.get(position);
        if (item != null) {
            tvmasach = v.findViewById(R.id.tv_masach_sach);
            tvmasach.setText(item.getMaSach() + ":");
            tvtensach = v.findViewById(R.id.tv_tensach_sach);
            tvtensach.setText(item.getTenSach());
        }
        return v;
    }
}
