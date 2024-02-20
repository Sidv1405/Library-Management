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
import com.sidv.quanlythuvien.Adapter.ThanhVienAdapter;
import com.sidv.quanlythuvien.DAO.ThanhVienDAO;
import com.sidv.quanlythuvien.DTO.ThanhVienDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;

public class FragmentQLTV extends Fragment {
    private RecyclerView rcThanhVien;
    private ArrayList<ThanhVienDTO> listTV;
    private ThanhVienDAO thanhVienDAO;
    private ThanhVienAdapter thanhVienAdapter;
    private FloatingActionButton fabAddTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        showListTV();

        addNewTV();
    }

    private void addNewTV() {
        fabAddTV.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            View view1 = layoutInflater.inflate(R.layout.dialog_them_thanh_vien, null);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            EditText edtTenTV = view1.findViewById(R.id.edtTenTV);
            EditText edtNamSinhTV = view1.findViewById(R.id.edtNamSinhTV);
            Button btnXacNhan = view1.findViewById(R.id.btnXacNhanThemTV);
            Button btnHuy = view1.findViewById(R.id.btnHuyThemTV);

            btnXacNhan.setOnClickListener(v1 -> {
                String tenTV = edtTenTV.getText().toString();
                String namSinhTV = edtNamSinhTV.getText().toString();
                if (tenTV.isEmpty() || namSinhTV.isEmpty()) {
                    Toast.makeText(getContext(), "Hãy điền tên thành viên", Toast.LENGTH_SHORT).show();
                } else {
                    ThanhVienDTO thanhVienDTO = new ThanhVienDTO(tenTV,namSinhTV);
                    boolean check = thanhVienDAO.createThanhVien(thanhVienDTO);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        showListTV();
                    } else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
        });
    }

    private void showListTV() {
        thanhVienDAO = new ThanhVienDAO(getContext());
        listTV = thanhVienDAO.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcThanhVien.setLayoutManager(linearLayoutManager);
        thanhVienAdapter = new ThanhVienAdapter(listTV, getContext());
        rcThanhVien.setAdapter(thanhVienAdapter);
    }

    private void maps(@NonNull View view) {
        rcThanhVien = view.findViewById(R.id.rcThanhVien);
        fabAddTV = view.findViewById(R.id.fabAddTV);
    }
}
