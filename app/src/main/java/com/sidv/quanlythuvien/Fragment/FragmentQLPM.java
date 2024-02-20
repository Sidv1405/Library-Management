package com.sidv.quanlythuvien.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidv.quanlythuvien.Adapter.PhieuMuonAdapter;
import com.sidv.quanlythuvien.DAO.PhieuMuonDAO;
import com.sidv.quanlythuvien.DAO.SachDAO;
import com.sidv.quanlythuvien.DAO.ThanhVienDAO;
import com.sidv.quanlythuvien.DTO.PhieuMuonDTO;
import com.sidv.quanlythuvien.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentQLPM extends Fragment {
    private RecyclerView rcPhieuMuon;
    private ArrayList<PhieuMuonDTO> listPM;
    private PhieuMuonDAO phieuMuonDAO;
    private PhieuMuonAdapter phieuMuonAdapter;
    private FloatingActionButton fabAddPM;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ql_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        showListPM();

        fabAddPM.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            View view1 = layoutInflater.inflate(R.layout.dialog_them_phieu_muon, null);
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Spinner spinTV = view1.findViewById(R.id.spinTV);
            Spinner spinS = view1.findViewById(R.id.spinS);
            TextView txtNgayThue = view1.findViewById(R.id.txtNgayThue_PM);
            TextView txtTienThue = view1.findViewById(R.id.txtTienthue_PM);
            CheckBox chkTrangThai = view1.findViewById(R.id.chkTrangthai_PM);
            Button btnXacNhanThemPM = view1.findViewById(R.id.btnXacNhanThemPM);
            Button btnHuyThemPM = view1.findViewById(R.id.btnHuyThemPM);

            ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
            ArrayAdapter<String> adapterTV = (ArrayAdapter<String>) spinTV.getAdapter();
            ArrayAdapter<String> newAdapterTV = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, thanhVienDAO.getListTenTV());
            newAdapterTV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinTV.setAdapter(newAdapterTV);

            SachDAO sachDAO = new SachDAO(getContext());
            ArrayAdapter<String> adapterS = (ArrayAdapter<String>) spinS.getAdapter();
            ArrayAdapter<String> newAdapterS = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sachDAO.getListTenS());
            newAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinS.setAdapter(newAdapterS);

            txtNgayThue.setText(sdf.format(new Date()));
            spinS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String tenSach = parent.getItemAtPosition(position).toString();
                    String giaSach = String.valueOf(phieuMuonDAO.getGiaSachByTen(tenSach));
                    txtTienThue.setText("  " + giaSach);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnXacNhanThemPM.setOnClickListener(v1 -> {
                int maTV = phieuMuonDAO.getMaTVById(spinTV.getSelectedItem().toString());
                int maS = phieuMuonDAO.getMaSById(spinS.getSelectedItem().toString());
                Date ngayThue = null;
                try {
                    ngayThue = sdf.parse(txtNgayThue.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int tienThue = Integer.parseInt(txtTienThue.getText().toString().trim());
                int checkPM = chkTrangThai.isChecked() ? 1 : 0;

                PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO();
                phieuMuonDTO.setMaThanhVien(maTV);
                phieuMuonDTO.setMaSach(maS);
                phieuMuonDTO.setNgay(ngayThue);
                phieuMuonDTO.setTienThue(tienThue);
                phieuMuonDTO.setTraSach(checkPM);

                boolean check = phieuMuonDAO.createPhieuMuon(phieuMuonDTO);
                if (check) {
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    showListPM();
                } else {
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            btnHuyThemPM.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

        });
    }

    private void showListPM() {
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        try {
            listPM = phieuMuonDAO.getList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcPhieuMuon.setLayoutManager(linearLayoutManager);
        phieuMuonAdapter = new PhieuMuonAdapter(listPM, getContext(), phieuMuonDAO);
        rcPhieuMuon.setAdapter(phieuMuonAdapter);
    }

    private void maps(View view) {
        rcPhieuMuon = view.findViewById(R.id.rcPhieuMuon);
        fabAddPM = view.findViewById(R.id.fabAddPM);
    }
}
