package com.sidv.quanlythuvien.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sidv.quanlythuvien.DAO.DoanhThuDAO;
import com.sidv.quanlythuvien.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentDT extends Fragment {

    private Button btnTuNgay, btnDenNgay, btnTinhDoanhThu;
    private TextView txtTuNgay, txtDenNgay, txtDoanhThu;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private DoanhThuDAO doanhThuDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanhthu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        maps(view);

        chonTuNgay();

        chonDenNgay();

        btnTinhDoanhThu.setOnClickListener(view1 -> {
            doanhThuDAO = new DoanhThuDAO(getContext());
            if (txtDenNgay.getText().toString().isEmpty() || txtTuNgay.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn đủ ngày", Toast.LENGTH_SHORT).show();
            } else {
                txtDoanhThu.setText(doanhThuDAO.getDoanhThu(txtTuNgay.getText().toString(), txtDenNgay.getText().toString()) + " USD");
            }
        });
    }

    private void chonDenNgay() {
        btnDenNgay.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view12, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String selectedDate = sdf.format(calendar.getTime());
                txtDenNgay.setText(selectedDate);
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            try {
                Date fromDate = sdf.parse(txtTuNgay.getText().toString());
                long fromDateMillis = fromDate.getTime();
                datePickerDialog.getDatePicker().setMinDate(fromDateMillis);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            datePickerDialog.show();
        });
    }

    private void chonTuNgay() {
        btnTuNgay.setOnClickListener(view1 -> {
            DatePickerDialog.OnDateSetListener dateSetListener = (view2, year, monthOfYear, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String selectedDate = sdf.format(calendar.getTime());
                txtTuNgay.setText(selectedDate);
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
    }

    private void maps(@NonNull View view) {
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        txtTuNgay = view.findViewById(R.id.txtTuNgay);
        txtDenNgay = view.findViewById(R.id.txtDenNgay);
        btnTinhDoanhThu = view.findViewById(R.id.btnTinhDoanhThu);
        txtDoanhThu = view.findViewById(R.id.txtDoanhThu);
    }
}