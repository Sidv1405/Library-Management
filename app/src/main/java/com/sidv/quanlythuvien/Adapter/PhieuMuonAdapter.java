package com.sidv.quanlythuvien.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sidv.quanlythuvien.R;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView txtMapm, txtMaS, txtMatv, txtTienThue, txtNgay, txtTrangthai;
        CardView CardViewpm;
        ImageView ivdelete;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMapm = itemView.findViewById(R.id.txtMapm);
            txtMaS = itemView.findViewById(R.id.txtMaS);
            txtMatv = itemView.findViewById(R.id.txtMaTV);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangthai = itemView.findViewById(R.id.txtTrangThai);
            CardViewpm = itemView.findViewById(R.id.CardViewpm);
            ivdelete = itemView.findViewById(R.id.ivdelete);
        }
    }
}
