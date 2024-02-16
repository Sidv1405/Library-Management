package com.sidv.quanlythuvien.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidv.quanlythuvien.Adapter.LoaiSachAdapter;
import com.sidv.quanlythuvien.DAO.LoaiSachDAO;
import com.sidv.quanlythuvien.DTO.LoaiSachDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;

public class FragmentQLLS extends Fragment {
    private RecyclerView rcLoaiSach;
    private ArrayList<LoaiSachDTO> listLS;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter loaiSachAdapter;
    private FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlloaisach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        showListLS();

        addNewLS();

    }

    private void addNewLS() {
        fabAdd.setOnClickListener(v -> {
            // Hiển thị dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.dialog_themloaisach, null);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Anh xa
            EditText edtTenLS = dialogView.findViewById(R.id.edtTenLS);
            Button btnXacNhan = dialogView.findViewById(R.id.btnXacNhanThemLS);
            Button btnHuy = dialogView.findViewById(R.id.btnHuyThemLS);
            //Xu ly nut xac nhan
            btnXacNhan.setOnClickListener(v1 -> {
                String tenLS = edtTenLS.getText().toString();
                if (tenLS.isEmpty()) {
                    Toast.makeText(getContext(), "Hãy điền tên loại sách", Toast.LENGTH_SHORT).show();
                } else {
                    LoaiSachDTO loaiSachDTO = new LoaiSachDTO(tenLS);
                    boolean check = loaiSachDAO.createLoaiSach(loaiSachDTO);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        showListLS();
                    } else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Xu ly nut huy
            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
        });
    }

    private void maps(@NonNull View view) {
        rcLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        fabAdd = view.findViewById(R.id.fabAddLoaiSach);
    }

    private void showListLS() {
        loaiSachDAO = new LoaiSachDAO(getContext());
        listLS = loaiSachDAO.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcLoaiSach.setLayoutManager(linearLayoutManager);
        loaiSachAdapter = new LoaiSachAdapter(listLS, getContext());
        rcLoaiSach.setAdapter(loaiSachAdapter);
    }
}