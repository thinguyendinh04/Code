package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.LoaiSach;
import com.example.myapplication.Adapter.LoaiSachAdapter;
import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiSachFragment extends Fragment {
    ListView lvLoaiSach;
    ArrayList<LoaiSach> list;
    static LoaiSachDAO dao;
    LoaiSachAdapter adapter;
    LoaiSach item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaLS, edTenLS;
    Button btnSave, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        lvLoaiSach = v.findViewById(R.id.lvLoaiSach);
        fab = v.findViewById(R.id.fab);
        dao = new LoaiSachDAO(getActivity());
        capNhatLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog(getActivity(), 0);
            }
        });

        lvLoaiSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDiaLog(getActivity(), 1); //update
                return false;
            }
        });

        return v;
    }

    void capNhatLv() {
        list = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(), list);
        lvLoaiSach.setAdapter(adapter);
    }

    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    void openDiaLog(final Context context, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.loai_sach_dialog, null);
        builder.setView(dialogView);

        edTenLS = dialogView.findViewById(R.id.edTenLS);
        btnSave = dialogView.findViewById(R.id.btnSaveLS);
        btnCancel = dialogView.findViewById(R.id.btnCancelLS);

        if (type == 0) {
            builder.setTitle("Thêm loại sách");
            btnSave.setText("Thêm");
        } else {
            builder.setTitle("Cập nhật loại sách");
            btnSave.setText("Cập nhật");
            edTenLS.setText(item.getTenLoai());
        }

        dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edTenLS.getText().toString().trim();

                if (tenLoai.isEmpty()) {
                    Toast.makeText(context, "Bạn phải nhập đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    if (type == 0) {
                        // Thêm loại sách
                        LoaiSach newLoaiSach = new LoaiSach();
                        newLoaiSach.setTenLoai(tenLoai);
                        dao.insert(newLoaiSach);
                    } else {
                        // Cập nhật loại sách
                        item.setTenLoai(tenLoai);
                        dao.update(item);
                    }

                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    int validate() {
        int check = 1;
        if (edTenLS.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_LONG).show();
            check = -1;
        }
        return check;
    }
}
