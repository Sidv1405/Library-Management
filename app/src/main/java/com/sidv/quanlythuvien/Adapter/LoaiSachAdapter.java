package com.sidv.quanlythuvien.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sidv.quanlythuvien.DAO.LoaiSachDAO;
import com.sidv.quanlythuvien.DTO.LoaiSachDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;
import java.util.Objects;


public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    private ArrayList<LoaiSachDTO> listLS;
    private Context context;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(ArrayList<LoaiSachDTO> listLS, Context context) {
        this.listLS = listLS;
        this.context = context;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_loaisach, parent, false);
        LoaiSachViewHolder loaiSachViewHolder = new LoaiSachViewHolder(view);

        return loaiSachViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSachDTO loaiSachDTO = listLS.get(position);
        loaiSachDAO = new LoaiSachDAO(context);
        holder.txtMaLS.setText("Mã loại sách: " + loaiSachDTO.getMaLoai());
        holder.txtTenLS.setText(loaiSachDTO.getTenLoai());

        deleteLS(holder);

        editLS(holder, loaiSachDTO);
    }

    private void editLS(@NonNull LoaiSachViewHolder holder, LoaiSachDTO loaiSachDTO) {
        holder.cardViewLS.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cập nhật loại sách");
            builder.setIcon(R.drawable.ic_edit);

            // Tạo EditText trong dialog
            final EditText edtTenLoaiSach = new EditText(context);
            edtTenLoaiSach.setText(loaiSachDTO.getTenLoai());

            builder.setView(edtTenLoaiSach);

            // Update
            builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String tenLoaiSachMoi = edtTenLoaiSach.getText().toString().trim();
                    if (!tenLoaiSachMoi.isEmpty()) {
                        // Cập nhật thông tin loại sách
                        loaiSachDTO.setTenLoai(tenLoaiSachMoi);
                        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                        boolean isUpdated = loaiSachDAO.updateLoaiSach(loaiSachDTO);
                        if (isUpdated) {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Thêm nút "Hủy"
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Tạo và hiển thị dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.rgb(254, 230, 179)));
        });
    }

    private void deleteLS(@NonNull LoaiSachViewHolder holder) {
        holder.imgDeleteLS.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa loại sách");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Bạn có muốn xóa loại sách?");
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                boolean check = loaiSachDAO.deleteLoaiSach(listLS.get(holder.getAdapterPosition()).getMaLoai());
                if (check) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    listLS.clear();
                    listLS.addAll(loaiSachDAO.getList());
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
        return listLS.size();
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMaLS, txtTenLS;
        private ImageView imgDeleteLS;
        private CardView cardViewLS;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLS = itemView.findViewById(R.id.txtMaLS);
            txtTenLS = itemView.findViewById(R.id.txtTenLS);
            imgDeleteLS = itemView.findViewById(R.id.imgDeleteLS);
            cardViewLS = itemView.findViewById(R.id.cardViewLS);

        }
    }
}
