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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sidv.quanlythuvien.DAO.ThanhVienDAO;
import com.sidv.quanlythuvien.DTO.ThanhVienDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;
import java.util.Objects;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private ArrayList<ThanhVienDTO> listTV;
    private Context context;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(ArrayList<ThanhVienDTO> listTV, Context context) {
        this.listTV = listTV;
        this.context = context;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_thanhvien, parent, false);
        ThanhVienViewHolder thanhVienViewHolder = new ThanhVienViewHolder(view);

        return thanhVienViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVienDTO thanhVienDTO = listTV.get(position);
        thanhVienDAO = new ThanhVienDAO(context);
        holder.txtMaTV.setText("Mã thành viên: " + thanhVienDTO.getMaTV());
        holder.txtTenTV.setText(thanhVienDTO.getHoTen());
        holder.txtNamSinhTV.setText(thanhVienDTO.getNamSinh());

        deleteTV(holder);

        updateTV(holder, thanhVienDTO);
    }

    private void updateTV(@NonNull ThanhVienViewHolder holder, ThanhVienDTO thanhVienDTO) {
        holder.cardViewTV.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cập nhật thành viên");
            builder.setIcon(R.drawable.ic_edit);

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText edtTenTV = new EditText(context);
            edtTenTV.setText(thanhVienDTO.getHoTen());
            layout.addView(edtTenTV);

            // EditText 2: Mã loại sách
            final EditText edtNamSinhTV = new EditText(context);
            edtNamSinhTV.setText(String.valueOf(thanhVienDTO.getNamSinh()));
            layout.addView(edtNamSinhTV);

            builder.setView(layout);

            builder.setPositiveButton("Cập nhật", (dialog, which) -> {
                String tenMoi = edtTenTV.getText().toString().trim();
                String namSinhMoi = edtNamSinhTV.getText().toString().trim();
                if (!tenMoi.isEmpty() || !namSinhMoi.isEmpty()) {
                    thanhVienDTO.setHoTen(tenMoi);
                    thanhVienDTO.setNamSinh(namSinhMoi);
                    ThanhVienDAO thanhVienDAO1 = new ThanhVienDAO(context);
                    boolean check = thanhVienDAO1.updateThanhVien(thanhVienDTO);
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
        });
    }

    private void deleteTV(@NonNull ThanhVienViewHolder holder) {
        holder.imgDeleteTV.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa Thành viên");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Bạn có muốn xóa thành viên?");
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                boolean check = thanhVienDAO.deleteThanhVien(listTV.get(holder.getAdapterPosition()).getMaTV());
                if (check) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    listTV.clear();
                    listTV.addAll(thanhVienDAO.getList());
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
        return listTV.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMaTV, txtTenTV, txtNamSinhTV;
        private ImageView imgDeleteTV;
        private CardView cardViewTV;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtNamSinhTV = itemView.findViewById(R.id.txtNamSinhTV);
            imgDeleteTV = itemView.findViewById(R.id.imgDeleteTV);
            cardViewTV = itemView.findViewById(R.id.cardViewTV);
        }
    }
}
