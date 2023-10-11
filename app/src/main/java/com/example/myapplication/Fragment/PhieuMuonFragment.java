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

import com.example.myapplication.Adapter.LoaiSachAdapter;
import com.example.myapplication.Adapter.PhieuMuonAdapter;
import com.example.myapplication.Adapter.SachSpinnerAdapter;
import com.example.myapplication.Adapter.ThanhvienSpinnerAdapter;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.Model.LoaiSach;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog(getActivity(), 0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list_pm.get(position);
                openDiaLog(getActivity(), 1); //update
                return false;
            }
        });

        return view;
    }

    void capNhatLv() {
        list_pm = (ArrayList<PhieuMuon>) dao.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getActivity(), list_pm);
        lv.setAdapter(phieuMuonAdapter);
    }

    public void xoa(final String Id) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //gọi delete
                        dao.delete(Id);
                        //cập nhật
                        capNhatLv();
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




    private void openDiaLog(FragmentActivity activity, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.phieumuon_dialog, null);
        builder.setView(dialogView);

        edtMaPM = dialogView.findViewById(R.id.edt_mapm);
        spinnertv = dialogView.findViewById(R.id.spinnerTv);
        spinnersach = dialogView.findViewById(R.id.spinnerSach);
        tvNgay = dialogView.findViewById(R.id.tv_Ngaythue);
        tvTienthue = dialogView.findViewById(R.id.tv_tienthue);
        btnSave = dialogView.findViewById(R.id.btnSave_itemAddPM);
        btnCancel = dialogView.findViewById(R.id.btnHuy_itemAddPM);

        // Tạo adapter cho spinner ThanhVien
        thanhvienSpinnerAdapter = new ThanhvienSpinnerAdapter(getActivity(), list_thanhvien);
        spinnertv.setAdapter(thanhvienSpinnerAdapter);

        // Tạo adapter cho spinner Sach
        sachSpinnerAdapter = new SachSpinnerAdapter(getActivity(), list_sach);
        spinnersach.setAdapter(sachSpinnerAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mapm = Integer.parseInt(edtMaPM.getText().toString());
                // Lấy thông tin thành viên được chọn
                ThanhVien thanhVien = (ThanhVien) spinnertv.getSelectedItem();
                int mathanhvien = thanhVien.getMaTV();
                // Lấy thông tin sách được chọn
                Sach sach = (Sach) spinnersach.getSelectedItem();
                int masach = sach.getMaSach();

                // Lấy ngày hiện tại
                String currentDate = sdf.format(new Date(System.currentTimeMillis()));
                tvNgay.setText(currentDate);
                // Lấy tiền thuê sách
                int tienthue = Integer.parseInt(tvTienthue.getText().toString());

                // Thực hiện lưu thông tin vào cơ sở dữ liệu
                PhieuMuon phieuMuon = new PhieuMuon(mapm, mathanhvien, masach, currentDate, tienthue);
                dao.insert(phieuMuon);

                // Cập nhật ListView
                capNhatLv();

                Toast.makeText(getActivity(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }
}