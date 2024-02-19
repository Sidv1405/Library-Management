package com.sidv.quanlythuvien.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sidv.quanlythuvien.DTO.TopDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    ArrayList<TopDTO> list;
    Context context;

    public TopAdapter(ArrayList<TopDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        TopDTO top = list.get(position);
        holder.txtTenSach.setText("Sách: " + top.getTenSach());
        holder.txtSoLuong.setText("Số lượng: " + top.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSach, txtSoLuong;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txtTenS_T);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong_T);
        }
    }
}
