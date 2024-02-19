package com.sidv.quanlythuvien.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sidv.quanlythuvien.DAO.PhieuMuonDAO;
import com.sidv.quanlythuvien.DAO.SachDAO;
import com.sidv.quanlythuvien.DTO.PhieuMuonDTO;
import com.sidv.quanlythuvien.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private ArrayList<PhieuMuonDTO> listPM;
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(ArrayList<PhieuMuonDTO> listPM, Context context, PhieuMuonDAO phieuMuonDAO) {
        this.listPM = listPM;
        this.context = context;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_phieumuon, parent, false);
        PhieuMuonAdapter.PhieuMuonViewHolder phieuMuonViewHolder = new PhieuMuonViewHolder(view);

        return phieuMuonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuonDTO phieuMuonDTO = listPM.get(position);
        phieuMuonDAO = new PhieuMuonDAO(context);

        holder.txtMaPM.setText("Mã phiếu: " + phieuMuonDTO.getMaPhieuMuon());
        holder.txtTenTV.setText("Tên thành viên: " + phieuMuonDAO.getTenTVById(phieuMuonDTO.getMaThanhVien()));
        holder.txtTenS.setText("Tên sách: " + phieuMuonDAO.getTenSById(phieuMuonDTO.getMaSach()));
        holder.txtTienThue.setText("Tiền thuê: " + phieuMuonDTO.getTienThue());
        holder.txtNgay.setText("Ngày thuê: " + sdf.format(phieuMuonDTO.getNgay()));
        if (phieuMuonDTO.getTraSach() == 1) {
            holder.txtTrangThai.setText("Đã trả sách");
            holder.txtTrangThai.setTextColor(Color.BLUE);
        } else {
            holder.txtTrangThai.setText("Chưa trả sách");
            holder.txtTrangThai.setTextColor(Color.RED);
        }

        deletePM(holder);


        updatePM(holder, phieuMuonDTO);
    }

    private void updatePM(@NonNull PhieuMuonViewHolder holder, PhieuMuonDTO phieuMuonDTO) {
        holder.cardViewPM.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cập nhật phiếu mượn");
                builder.setIcon(R.drawable.ic_edit);

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                String tenThanhVienHienTai = phieuMuonDAO.getTenTVById(phieuMuonDTO.getMaThanhVien());
                TextView txtSpinnerTV = new TextView(context);
                txtSpinnerTV.setText(" Thành viên:");
                txtSpinnerTV.setTextSize(13);
                layout.addView(txtSpinnerTV);
                Spinner spinnerTenTV = new Spinner(context);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phieuMuonDAO.getListTenTV());
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTenTV.setAdapter(spinnerAdapter);
                int defaultPositionTV = spinnerAdapter.getPosition(tenThanhVienHienTai);
                spinnerTenTV.setSelection(defaultPositionTV);
                layout.addView(spinnerTenTV);

                String tenSachHienTai = phieuMuonDAO.getTenSById(phieuMuonDTO.getMaSach());
                TextView txtSpinnerS = new TextView(context);
                txtSpinnerS.setText(" Tên sách:");
                txtSpinnerS.setTextSize(13);
                layout.addView(txtSpinnerS);
                Spinner spinnerTenS = new Spinner(context);
                ArrayAdapter<String> spinnerAdapterS = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phieuMuonDAO.getListTenS());
                spinnerAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTenS.setAdapter(spinnerAdapterS);
                int defaultPositionS = spinnerAdapterS.getPosition(tenSachHienTai);
                spinnerTenS.setSelection(defaultPositionS);
                layout.addView(spinnerTenS);

                TextView txtNgayThue = new TextView(context);
                txtNgayThue.setText(" Giá thuê:");
                txtNgayThue.setTextSize(13);
                layout.addView(txtNgayThue);
                TextView txtGetNgay = new TextView(context);
                txtGetNgay.setText("  " + sdf.format(phieuMuonDTO.getNgay()));
                txtGetNgay.setTextSize(17);
                txtGetNgay.setTextColor(Color.BLACK);
                layout.addView(txtGetNgay);

                TextView txtGiaThue = new TextView(context);
                txtGiaThue.setText(" Giá thuê:");
                txtGiaThue.setTextSize(13);
                layout.addView(txtGiaThue);
                TextView txtMenhGia = new TextView(context);
                spinnerTenS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tenSach = parent.getItemAtPosition(position).toString();
                        String giaSach = String.valueOf(phieuMuonDAO.getGiaSachByTen(tenSach));
                        txtMenhGia.setText("  " + giaSach);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                txtMenhGia.setTextColor(Color.BLACK);
                txtMenhGia.setTextSize(17);
                layout.addView(txtMenhGia);

                CheckBox checkBoxTrasach = new CheckBox(context);
                checkBoxTrasach.setText("Đã trả sách");
                if (phieuMuonDTO.getTraSach() == 1) {
                    checkBoxTrasach.setChecked(true);
                } else {
                    checkBoxTrasach.setChecked(false);
                }
                layout.addView(checkBoxTrasach);

                builder.setView(layout);

                builder.setPositiveButton("Cập nhật", (dialog, which) -> {
                    String nguoiMuonMoi = spinnerTenTV.getSelectedItem().toString().trim();
                    String tenSachMoi = spinnerTenS.getSelectedItem().toString().trim();
                    int giaThueMoi = Integer.parseInt(txtMenhGia.getText().toString().trim());
                    String checkPM;
                    if (checkBoxTrasach.isChecked()) {
                        checkPM = String.valueOf(1);
                    } else {
                        checkPM = String.valueOf(0);
                    }
                    phieuMuonDTO.setMaThanhVien(phieuMuonDAO.getMaTVById(nguoiMuonMoi));
                    phieuMuonDTO.setMaSach(phieuMuonDAO.getMaSById(tenSachMoi));
                    phieuMuonDTO.setTienThue(giaThueMoi);
                    phieuMuonDTO.setTraSach(Integer.parseInt(checkPM));
                    PhieuMuonDAO phieuMuonDAO1 = new PhieuMuonDAO(context);
                    boolean checkUpdate = phieuMuonDAO1.updatePhieumuon(phieuMuonDTO);
                    if (checkUpdate) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

                AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.rgb(254, 230, 179)));

                return true;
            }
        });
    }

    private void deletePM(@NonNull PhieuMuonViewHolder holder) {
        holder.imgDeletePM.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa phiếu mượn");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Bạn có muốn xóa phiếu mượn?");
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                boolean check = phieuMuonDAO.deletePhieuMuon(listPM.get(holder.getAdapterPosition()).getMaPhieuMuon());
                if (check) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    listPM.clear();
                    try {
                        listPM.addAll(phieuMuonDAO.getList());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.rgb(254, 230, 179)));
        });
    }

    @Override
    public int getItemCount() {
        return listPM.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPM, txtTenTV, txtTenS, txtTienThue, txtNgay, txtTrangThai;
        CardView cardViewPM;
        ImageView imgDeletePM;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMapm);
            txtTenTV = itemView.findViewById(R.id.txtTenTV_PM);
            txtTenS = itemView.findViewById(R.id.txtTenS_PM);
            txtTienThue = itemView.findViewById(R.id.txtTienThuePM);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThaiPM);
            txtNgay = itemView.findViewById(R.id.txtNgayPM);
            cardViewPM = itemView.findViewById(R.id.cardViewPM);
            imgDeletePM = itemView.findViewById(R.id.imgdeletePM);
        }
    }
}
