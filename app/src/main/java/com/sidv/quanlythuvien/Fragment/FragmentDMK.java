package com.sidv.quanlythuvien.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.sidv.quanlythuvien.R;

public class FragmentDMK extends Fragment {
    EditText edtMKCu, edtMKMoi1, edtMKMoi2;
    Button btnXacnhan, btnHuy;
    ThuThuDAO thuThuDAO;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        maps(view);

        thuThuDAO = new ThuThuDAO(getContext());
        sharedPreferences = getActivity().getSharedPreferences("INFO", Context.MODE_PRIVATE);

        capNhatMK();

        huyCapNhatMK();
    }

    private void huyCapNhatMK() {
        btnHuy.setOnClickListener(view1 -> {
            edtMKCu.setText("");
            edtMKMoi1.setText("");
            edtMKMoi2.setText("");
        });
    }

    private void capNhatMK() {
        btnXacnhan.setOnClickListener(view1 -> {
            String maTT = sharedPreferences.getString("username", "");
            String mkCu = edtMKCu.getText().toString();
            String mkMoi1 = edtMKMoi1.getText().toString();
            String mkMoi2 = edtMKMoi2.getText().toString();

            if (thuThuDAO.kiemTraMatKhauThuThu(maTT, mkCu)) {
                if (mkCu.isEmpty() || mkMoi1.isEmpty() || mkMoi2.isEmpty()) {
                    Toast.makeText(getContext(), "Thất bại! Bạn hãy điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
                } else if (!mkMoi1.equals(mkMoi2)) {
                    Toast.makeText(getContext(), "Thất bại! Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = thuThuDAO.updateMatKhauThuThu(maTT, mkMoi1);
                    if (check) {
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edtMKCu.setText("");
                        edtMKMoi1.setText("");
                        edtMKMoi2.setText("");
                    }
                }
            } else {
                Toast.makeText(getContext(), "Thất bại! Mật khẩu cũ không trùng khớp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void maps(@NonNull View view) {
        edtMKCu = view.findViewById(R.id.oldPass);
        edtMKMoi1 = view.findViewById(R.id.newPass);
        edtMKMoi2 = view.findViewById(R.id.reNewPass);
        btnXacnhan = view.findViewById(R.id.btnXacNhanDMK);
        btnHuy = view.findViewById(R.id.btnHuyDMK);
    }
}
