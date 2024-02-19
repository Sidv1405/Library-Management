package com.sidv.quanlythuvien.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sidv.quanlythuvien.Adapter.TopAdapter;
import com.sidv.quanlythuvien.DAO.TopDAO;
import com.sidv.quanlythuvien.DTO.TopDTO;
import com.sidv.quanlythuvien.R;

import java.util.ArrayList;

public class FragmentTop extends Fragment {
    RecyclerView rcTop;

    TopDAO topDAO;
    TopAdapter topAdapter;
    ArrayList<TopDTO> listTop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcTop = view.findViewById(R.id.rcTop);
        topDAO = new TopDAO(getContext());
        listTop = topDAO.getList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcTop.setLayoutManager(linearLayoutManager);
        topAdapter = new TopAdapter(listTop, getContext());
        rcTop.setAdapter(topAdapter);
    }
}
