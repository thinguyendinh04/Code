package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.PhieuMuonAdapter;
import com.example.myapplication.Adapter.SachSpinnerAdapter;
import com.example.myapplication.Adapter.ThanhvienSpinnerAdapter;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.Model.PhieuMuon;
import com.example.myapplication.Model.Sach;
import com.example.myapplication.Model.ThanhVien;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonFragment extends Fragment {
    ListView lv;
    ArrayList<PhieuMuon> list_pm;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edtMaPM;
    Spinner spinnertv, spinnersach;
    TextView tvNgay, tvTienthue;
    CheckBox chkTrasach;
    Button btnSave, btnCancel;
    PhieuMuonDAO dao;
    PhieuMuonAdapter phieuMuonAdapter;
    PhieuMuon item;
    ThanhvienSpinnerAdapter thanhvienSpinnerAdapter;
    ArrayList<ThanhVien> list_thanhvien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int mathanhvien;
    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> list_sach;
    SachDAO sachDAO;
    Sach sach;
    int masach, tienthue;
    int PositionTV, PositionSach;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lv = view.findViewById(R.id.lvphieumuon);
        fab = view.findViewById(R.id.fabButton);
        dao = new PhieuMuonDAO(getActivity());
        CapNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        return view;
    }

    private void openDialog(FragmentActivity activity, int i) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.phieumuon_dialog);
        edtMaPM = dialog.findViewById(R.id.edt_mapm);
        spinnertv = dialog.findViewById(R.id.spinnerTv);
        spinnersach = dialog.findViewById(R.id.spinnerSach);
        tvNgay = dialog.findViewById(R.id.tv_Ngaythue);
        tvTienthue = dialog.findViewById(R.id.tv_tienthue);
        chkTrasach = dialog.findViewById(R.id.chkTrangThai_itemAddPM);
        btnCancel = dialog.findViewById(R.id.btnHuy_itemAddPM);
        btnSave = dialog.findViewById(R.id.btnSave_itemAddPM);
        thanhVienDAO = new ThanhVienDAO(getContext());
        list_thanhvien = new ArrayList<ThanhVien>();
        list_thanhvien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhvienSpinnerAdapter = new ThanhvienSpinnerAdapter(getContext(),list_thanhvien);
        spinnertv.setAdapter(thanhvienSpinnerAdapter);

        spinnertv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mathanhvien = list_thanhvien.get(position).getMaTV();
                Toast.makeText(activity, "Chọn: "+ list_thanhvien.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDAO= new SachDAO(getContext());
        list_sach = (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(), list_sach);
        spinnersach.setAdapter(sachSpinnerAdapter);

        spinnersach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masach = list_sach.get(position).getMaSach();
                tienthue = list_sach.get(position).getGiaThue();
                Toast.makeText(activity, "Chọn" + list_sach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
                tvTienthue.setText(String.valueOf(tienthue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (i == 0) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String masach = edtMaPM.getText().toString();
                    if (masach.isEmpty()) {
                        Toast.makeText(activity, "Vui lòng nhập mã phiếu mượn", Toast.LENGTH_SHORT).show();
                    } else {
                        if (validate() > 0) {
                            item = new PhieuMuon();
                            item.setMaSach(Integer.parseInt(masach));
                            item.setMaTV(mathanhvien);
                            item.setNgay(new Date(System.currentTimeMillis()));
                            item.setTienThue(tienthue);

                            if (chkTrasach.isChecked()) {
                                item.setTraSach(1);
                            } else {
                                item.setTraSach(0);
                            }

                            long result = dao.insert(item);
                            if (result > 0) {
                                Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                CapNhatLv();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, "Kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            // update
            btnSave.setText("Update");
            edtMaPM.setText(list_pm.get(i).getMaPM());
            edtMaPM.setEnabled(false);
            dialog.show();
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void CapNhatLv() {
        list_pm = (ArrayList<PhieuMuon>) dao.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list_pm);
        lv.setAdapter(phieuMuonAdapter);
        phieuMuonAdapter.notifyDataSetChanged(); // Thông báo cập nhật dữ liệu
    }

    public int validate() {
        int check = 1;
        if (edtMaPM.getText().toString().isEmpty()) {
            check = -1;
        }
        return check;
    }

    public void xoa(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dao.delete(String.valueOf(Integer.parseInt(s)));
                        CapNhatLv();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}