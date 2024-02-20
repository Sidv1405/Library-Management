package com.sidv.quanlythuvien.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sidv.quanlythuvien.DAO.ThuThuDAO;
import com.sidv.quanlythuvien.DTO.ThuThuDTO;
import com.sidv.quanlythuvien.R;

public class FragmentTND extends Fragment {
    private ThuThuDAO thuThuDAO;
    private EditText edtTenDangNhap, edtHoTen, edtMatkhau1, edtMatkhau2;
    private Button btnXacNhan, btnHuy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_thu_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        xacNhanThem();

        huyThem();
    }

    private void huyThem() {
        btnHuy.setOnClickListener(view1 -> {
            edtHoTen.setText("");
            edtTenDangNhap.setText("");
            edtMatkhau1.setText("");
            edtMatkhau2.setText("");
        });
    }

    private void xacNhanThem() {
        btnXacNhan.setOnClickListener(view1 -> {
            if (edtTenDangNhap.getText().toString().isEmpty()
                    || edtHoTen.getText().toString().isEmpty()
                    || edtMatkhau1.getText().toString().isEmpty()
                    || edtMatkhau2.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Thất bại! Bạn hãy điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
            } else if (!edtMatkhau1.getText().toString().equals(edtMatkhau2.getText().toString())) {
                Toast.makeText(getContext(), "Thất bại! Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            } else if (!thuThuDAO.checkAccount(edtTenDangNhap.getText().toString())) {
                Toast.makeText(getContext(), "Thất bại! Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                ThuThuDTO thuThuDTO = new ThuThuDTO(edtTenDangNhap.getText().toString(), edtHoTen.getText().toString(), edtMatkhau1.getText().toString());
                boolean check = thuThuDAO.createThuThu(thuThuDTO);
                if (check) {
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    edtHoTen.setText("");
                    edtTenDangNhap.setText("");
                    edtMatkhau1.setText("");
                    edtMatkhau2.setText("");
                } else {
                    Toast.makeText(getContext(), "Thất bại! Thủ thư đã toàn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void maps(@NonNull View view) {
        thuThuDAO = new ThuThuDAO(getContext());
        edtTenDangNhap = view.findViewById(R.id.tenDangNhap);
        edtHoTen = view.findViewById(R.id.hoTen);
        edtMatkhau1 = view.findViewById(R.id.password);
        edtMatkhau2 = view.findViewById(R.id.rePassword);
        btnXacNhan = view.findViewById(R.id.btnXacNhanThemTT);
        btnHuy = view.findViewById(R.id.btnHuyThemTT);
    }
}
