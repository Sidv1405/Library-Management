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
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.sidv.quanlythuvien.DAO.LoaiSachDAO;
import com.sidv.quanlythuvien.DAO.SachDAO;
import com.sidv.quanlythuvien.DAO.ThanhVienDAO;
import com.sidv.quanlythuvien.DTO.SachDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;
import java.util.Objects;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private ArrayList<SachDTO> listS;
    private Context context;
    private SachDAO sachDAO;
    private LoaiSachDAO loaiSachDAO;

    public SachAdapter(ArrayList<SachDTO> listS, Context context) {
        this.listS = listS;
        this.context = context;
        this.sachDAO = new SachDAO(context);
        this.loaiSachDAO = new LoaiSachDAO(context);
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_sach, parent, false);
        SachViewHolder sachViewHolder = new SachViewHolder(view);
        return sachViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        SachDTO sachDTO = listS.get(position);
        sachDAO = new SachDAO(context);

        holder.txtMaS.setText("Mã sách: " + sachDTO.getMaSach());
        holder.txtTenS.setText("Tên sách: " + sachDTO.getTenSach());
        holder.txtGiaS.setText("Giá thuê: " + sachDTO.getGiaThue());
        holder.txtLS_S.setText("Loại sách: " + sachDAO.getTenLSById(sachDTO.getMaLoai()));

        deleteS(holder);

        updateS(holder, sachDTO);
    }

    private void updateS(@NonNull SachViewHolder holder, SachDTO sachDTO) {
        holder.cardViewS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cập nhật sách");
                builder.setIcon(R.drawable.ic_edit);

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextInputLayout textInputLayoutTenS = new TextInputLayout(context);
                textInputLayoutTenS.setHint("Tên sách:");
                TextInputEditText edtTenS = new TextInputEditText(context);
                edtTenS.setText(sachDTO.getTenSach());
                textInputLayoutTenS.addView(edtTenS);
                layout.addView(textInputLayoutTenS);

                TextInputLayout textInputLayoutGiaThueS = new TextInputLayout(context);
                textInputLayoutGiaThueS.setHint("Giá thuê:");
                TextInputEditText edtGiaThueS = new TextInputEditText(context);
                edtGiaThueS.setText(String.valueOf(sachDTO.getGiaThue()));
                textInputLayoutGiaThueS.addView(edtGiaThueS);
                layout.addView(textInputLayoutGiaThueS);

                TextView txtSpinner = new TextView(context);
                txtSpinner.setText(" Loại sách:");
                txtSpinner.setTextSize(13);
                layout.addView(txtSpinner);
                Spinner spinnerLoaiSach = new Spinner(context);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, loaiSachDAO.getListTen());
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLoaiSach.setAdapter(spinnerAdapter);
                layout.addView(spinnerLoaiSach);

                builder.setView(layout);

                builder.setPositiveButton("Cập nhật", (dialog, which) -> {
                    String tenMoi = edtTenS.getText().toString().trim();
                    int giaThueMoi = Integer.parseInt(edtGiaThueS.getText().toString());
                    String loaiSachMoi = spinnerLoaiSach.getSelectedItem().toString();
                    if (!tenMoi.isEmpty() || !String.valueOf(giaThueMoi).isEmpty()) {
                        sachDTO.setTenSach(tenMoi);
                        sachDTO.setGiaThue(giaThueMoi);
                        sachDTO.setMaLoai(loaiSachDAO.getMaLSByName(loaiSachMoi));
                        SachDAO sachDAO1 = new SachDAO(context);
                        boolean check = sachDAO1.updateSach(sachDTO);
                        if (check) {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.rgb(254, 230, 179)));

                return true;
            }
        });
    }

    private void deleteS(@NonNull SachViewHolder holder) {
        holder.imgDeleteS.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa sách");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Bạn có muốn xóa sách?");
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                boolean check = sachDAO.deleteSach(listS.get(holder.getAdapterPosition()).getMaSach());
                if (check) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    listS.clear();
                    listS.addAll(sachDAO.getList());
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
        return listS.size();
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMaS, txtGiaS, txtTenS, txtLS_S;
        private CardView cardViewS;
        private ImageView imgDeleteS;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaS = itemView.findViewById(R.id.txtMaSach);
            txtGiaS = itemView.findViewById(R.id.txtGia);
            txtTenS = itemView.findViewById(R.id.txtTenSach);
            txtLS_S = itemView.findViewById(R.id.txtLS_S);
            cardViewS = itemView.findViewById(R.id.cardViewS);
            imgDeleteS = itemView.findViewById(R.id.imgDeleteS);
        }
    }
}
