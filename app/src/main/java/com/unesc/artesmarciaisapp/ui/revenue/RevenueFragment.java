package com.unesc.artesmarciaisapp.ui.revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.FaturaMatriculaModel;
import com.unesc.artesmarciaisapp.ui.adapters.RevenueAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RevenueFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_revenues, container, false);


        recyclerView = root.findViewById(R.id.rcvRevenuesList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<FaturaMatriculaModel> lst = new ArrayList<>();
        lst.add(new FaturaMatriculaModel(98,new Date(),5951.87,new Date(),new Date()));
        lst.add(new FaturaMatriculaModel(98,new Date(),5951.87,new Date(),new Date()));
        lst.add(new FaturaMatriculaModel(98,new Date(),5951.87,new Date(),new Date()));
        lst.add(new FaturaMatriculaModel(98,new Date(),5951.87,new Date(),new Date()));

        mAdapter = new RevenueAdapter(lst);
        recyclerView.setAdapter(mAdapter);

        return root;
    }
}
