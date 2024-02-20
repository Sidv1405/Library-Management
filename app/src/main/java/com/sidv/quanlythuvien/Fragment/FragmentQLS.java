package com.sidv.quanlythuvien.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidv.quanlythuvien.Adapter.SachAdapter;
import com.sidv.quanlythuvien.DAO.LoaiSachDAO;
import com.sidv.quanlythuvien.DAO.SachDAO;
import com.sidv.quanlythuvien.DTO.SachDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;

public class FragmentQLS extends Fragment {
    private RecyclerView rcSach;
    private ArrayList<SachDTO> listS;
    private SachDAO sachDAO;
    private SachAdapter sachAdapter;
    private FloatingActionButton fabAddS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        showListS();

        addNewS();
    }

    private void addNewS() {
        fabAddS.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            View view1 = layoutInflater.inflate(R.layout.dialog_them_sach, null);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            EditText edtTenS = view1.findViewById(R.id.edtTenS);
            EditText edtGiaThueS = view1.findViewById(R.id.edtGiaThueS);
            Spinner spinnerS = view1.findViewById(R.id.spnTenLS_S);
            Button btnXacNhanThemS = view1.findViewById(R.id.btnXacNhanThemS);
            Button btnHuyThemS = view1.findViewById(R.id.btnHuyThemS);

            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerS.getAdapter();
            ArrayAdapter<String> newAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, loaiSachDAO.getListTen());
            newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerS.setAdapter(newAdapter);

            btnXacNhanThemS.setOnClickListener(v1 -> {
                String tenS = edtTenS.getText().toString();
                String giaThueSStr = edtGiaThueS.getText().toString();

                if (tenS.isEmpty() || TextUtils.isEmpty(giaThueSStr)) {
                    Toast.makeText(getContext(), "Hãy điền đủ các trường", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isDigitsOnly(giaThueSStr)) {
                    Toast.makeText(getContext(), "Giá thuê chỉ được nhập số", Toast.LENGTH_SHORT).show();
                } else {
                    int giaThueS = Integer.parseInt(giaThueSStr);
                    int maLS = loaiSachDAO.getMaLSByName(spinnerS.getSelectedItem().toString());

                    SachDTO sachDTO = new SachDTO(tenS, giaThueS, maLS);
                    boolean check = sachDAO.createSach(sachDTO);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        showListS();
                    } else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnHuyThemS.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
        });
    }

    private void showListS() {
        sachDAO = new SachDAO(getContext());
        listS = sachDAO.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcSach.setLayoutManager(linearLayoutManager);
        sachAdapter = new SachAdapter(listS, getContext());
        rcSach.setAdapter(sachAdapter);
    }

    private void maps(View view) {
        rcSach = view.findViewById(R.id.rcSach);
        fabAddS = view.findViewById(R.id.fabAddS);
    }
}
