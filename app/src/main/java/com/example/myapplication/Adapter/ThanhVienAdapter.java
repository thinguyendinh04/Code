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

import com.example.myapplication.Fragment.ThanhVienFragment;
import com.example.myapplication.Model.ThanhVien;
import com.example.myapplication.R;
import com.example.myapplication.Model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    ThanhVienFragment fragment;
    private ArrayList<ThanhVien> lists;
    TextView tvMatv,tvTenTV,tvNamSinh;
    ImageView imgDel;

    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.thanh_vien_item,null);
        }

        final ThanhVien item = lists.get(position);
        if(item!=null){
            tvMatv = view.findViewById(R.id.tvMaTV);
            tvMatv.setText("Mã thành viên: "+item.getMaTV());
            tvTenTV = view.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Tên thành viên: "+item.getHoTen());
            tvNamSinh = view.findViewById(R.id.NamSinh);
            tvNamSinh.setText("Năm sinh: "+item.getNamSinh());
            imgDel = view.findViewById(R.id.imgDeleteLS);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gọi phương thức xóa
                fragment.xoa(String.valueOf(item.getMaTV()));
            }
        });
        return view;
    }
}
