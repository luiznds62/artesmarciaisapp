package com.unesc.artesmarciaisapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.FaturaMatriculaModel;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.MovieHolder>{
    public List<FaturaMatriculaModel> mDataset;
    private final List<FaturaMatriculaModel> mDatasetAux;

    public static class MovieHolder extends RecyclerView.ViewHolder {
        public TextView txvRevenuesRowCodigo;
        public TextView txvRevenuesRowDataVencimento;
        public TextView txvRevenuesRowDataPagamento;
        public TextView txvRevenuesRowValor;

        public MovieHolder(View v) {
            super(v);

            txvRevenuesRowCodigo = v.findViewById(R.id.txvRevenuesRowCodigo);
            txvRevenuesRowDataVencimento = v.findViewById(R.id.txvRevenuesRowDataVencimento);
            txvRevenuesRowDataPagamento = v.findViewById(R.id.txvRevenuesRowDataPagamento);
            txvRevenuesRowValor = v.findViewById(R.id.txvRevenuesRowValor);
        }
    }

    public RevenueAdapter(List<FaturaMatriculaModel> revenuesList) {
        mDatasetAux = revenuesList;
        mDataset = revenuesList;
    }

    /*public void filterDataset(final String filterExpression){
        mDataset = mDataset.stream().filter(it ->
                it.getName().toLowerCase().contains(filterExpression) ||
                        it.getAuthor().toLowerCase().contains(filterExpression) ||
                        it.getDescription().toLowerCase().contains(filterExpression) ||
                        it.getTheme().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }*/

    public void resetDataset(){
        mDataset = mDatasetAux;
        notifyDataSetChanged();
    }

    @Override
    public RevenueAdapter.MovieHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.revenues_row, parent, false);

        MovieHolder vh = new MovieHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txvRevenuesRowCodigo.setText(String.valueOf(mDataset.get(position).getCodigo_matricula()));
        holder.txvRevenuesRowDataPagamento.setText(mDataset.get(position).getData_pagamento().toString());
        holder.txvRevenuesRowDataVencimento.setText(mDataset.get(position).getData_vencimento().toString());
        holder.txvRevenuesRowValor.setText(mDataset.get(position).getValor().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
