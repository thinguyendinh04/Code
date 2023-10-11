package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Adapter.LoaiSachSpinnerAdapter;
import com.example.myapplication.Adapter.SachAdapter;
import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.Model.LoaiSach;
import com.example.myapplication.Model.Sach;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SachFragment extends Fragment {
    ListView lv;
    ArrayList<Sach> list_sach_fr;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMasach, edTensach, edGiathue;
    Spinner spinner;
    Button btnSave, btnDeleteDialog;
    SachDAO sachDAO;
    SachAdapter sachAdapter;
    Sach item;
    LoaiSachSpinnerAdapter loaiSachSpinnerAdapter;
    ArrayList<LoaiSach> list_Loaisach;
    LoaiSachDAO dao;
    LoaiSach loaiSach;
    int maLoaisach, position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        lv = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fab);
        sachDAO = new SachDAO(getActivity());
        dao = new LoaiSachDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog(getActivity(), 0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list_sach_fr.get(position);
                openDiaLog(getActivity(), 1); // update
                return true; // Return true to indicate that the long-click event is consumed
            }
        });
        return v;
    }

    private void capNhatLv() {
        Log.d("SachFragment", "capNhatLv() called");
        list_sach_fr = ((ArrayList<Sach>) sachDAO.getAll());
        sachAdapter = new SachAdapter(getActivity(), list_sach_fr, this);
        lv.setAdapter(sachAdapter);
    }

    private void openDiaLog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sach);
        edMasach = dialog.findViewById(R.id.edMaSach);
        edTensach = dialog.findViewById(R.id.edTenS);
        edGiathue = dialog.findViewById(R.id.edGiathue_Sach);
        spinner = dialog.findViewById(R.id.spinner);
        btnSave = dialog.findViewById(R.id.btnSaveSach);
        btnDeleteDialog = dialog.findViewById(R.id.btnCancelSach);

        // Lấy danh sách LoaiSach từ database
        list_Loaisach = (ArrayList<LoaiSach>) dao.getAll();

        // Khởi tạo adapter cho spinner
        loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(context, list_Loaisach);
        spinner.setAdapter(loaiSachSpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaisach = list_Loaisach.get(position).getMaLoai();
                Toast.makeText(context, "Chọn: " + list_Loaisach.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Xử lý sự kiện khi bấm nút "Lưu"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new Sach();
                item.setTenSach(edTensach.getText().toString());
                item.setGiaThue(Integer.parseInt(edGiathue.getText().toString()));
                item.setMaLoai(maLoaisach);

                if (validate()) {
                    if (type == 0) {
                        //Thêm sách
                        long result = sachDAO.insert(item);
                        if (result > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            capNhatLv();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Cập nhật sách
                        item.setMaSach(list_sach_fr.get(position).getMaSach());
                        long result = sachDAO.update(item);
                        if (result > 0) {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            capNhatLv();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // Xử lý sự kiện khi bấm nút "Hủy"
        btnDeleteDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validate() {
        if (edTensach.getText().toString().isEmpty() || edGiathue.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void xoa(String maSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa sách");
        builder.setMessage("Bạn có chắc chắn muốn xóa sách này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long result = sachDAO.delete(maSach);
                if (result > 0) {
                    Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    capNhatLv();
                } else {
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}