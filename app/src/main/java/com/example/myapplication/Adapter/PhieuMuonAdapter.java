package com.example.myapplication.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.Fragment.PhieuMuonFragment;
import com.example.myapplication.Model.PhieuMuon;
import com.example.myapplication.Model.Sach;
import com.example.myapplication.Model.ThanhVien;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    private PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> list_phieumuon;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SachDAO sachDAO;
    private ThanhVienDAO thanhVienDAO;

    public PhieuMuonAdapter(@NonNull Context context, ArrayList<PhieuMuon> list_phieumuon) {
        super(context, 0, list_phieumuon);
        this.context = context;
        this.list_phieumuon = list_phieumuon;
        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.phieumuon_item, parent, false);
        }

        PhieuMuon item = list_phieumuon.get(position);
        if (item != null) {
            TextView txtMaPM = v.findViewById(R.id.tv_maphieumuon);
            txtMaPM.setText("Mã phiếu mượn: " + item.getMaPM());

            TextView txtTensach = v.findViewById(R.id.tv_tensach);
            Sach sach = sachDAO.getid(String.valueOf(item.getMaSach()));
            if (sach != null) {
                txtTensach.setText("Tên sách: " + sach.getTenSach());
            } else {
                txtTensach.setText("Tên sách: (không có)");
            }

            TextView txtTenTV = v.findViewById(R.id.tv_thanhvien);
            ThanhVien thanhVien = thanhVienDAO.getById(String.valueOf(item.getMaTV()));
            txtTenTV.setText("Tên thành viên: " + thanhVien.getHoTen());

            TextView txtNgay = v.findViewById(R.id.tv_ngay);
            txtNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));

            TextView txtTienThue = v.findViewById(R.id.tv_tienthue);
            txtTienThue.setText("Tiền thuê sách: " + item.getTienThue());

            TextView txtTrasach = v.findViewById(R.id.tv_trasach);
            if (item.getTraSach() == 1) {
                txtTrasach.setTextColor(Color.BLUE);
                txtTrasach.setText("Đã trả sách");
            } else {
                txtTrasach.setTextColor(Color.RED);
                txtTrasach.setText("Chưa trả sách");
            }

            Button btnDelete = v.findViewById(R.id.btnXoa);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.xoa(String.valueOf(item.getMaPM()));
                }
            });
        }
        return v;
    }
}