package com.unesc.artesmarciaisapp.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.GraduationModel;
import com.unesc.artesmarciaisapp.services.GraduationService;
import com.unesc.artesmarciaisapp.ui.graduation.UpdateGraduationActivity;

import java.util.List;
import java.util.stream.Collectors;

public class GraduationAdapter extends RecyclerView.Adapter<GraduationAdapter.GraduationHolder> {
    public List<GraduationModel> mDataset;

    public static class GraduationHolder extends RecyclerView.ViewHolder {
        public ImageButton btnGraduationDelete;
        public ImageButton btnGraduationUpdate;
        public TextView txvGraduationName;

        public GraduationHolder(View v) {
            super(v);

            btnGraduationUpdate = v.findViewById(R.id.btnGraduationUpdate);
            btnGraduationDelete = v.findViewById(R.id.btnGraduationDelete);
            txvGraduationName = v.findViewById(R.id.txvGraduationName);
        }
    }

    public GraduationAdapter(List<GraduationModel> matriculationList) {
        mDataset = matriculationList;
    }

    public void filterDataset(final String filterExpression, Context context) {
        GraduationService graduationService = new GraduationService(context);

        mDataset = graduationService.getAll().stream().filter(it ->
                it.getModalidade().toLowerCase().contains(filterExpression) ||
                        it.getGraduacao().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @Override
    public GraduationAdapter.GraduationHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.graduations_row, parent, false);

        GraduationHolder vh = new GraduationHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GraduationHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txvGraduationName.setText(mDataset.get(position).getGraduacao());

        holder.btnGraduationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateGraduationActivity.class);
                intent.putExtra("modalidade_graduacao", mDataset.get(position).getModalidade() + ";" + mDataset.get(position).getGraduacao());
                ((Activity) v.getContext()).startActivityForResult(intent, position);
            }
        });

        holder.btnGraduationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.text_graduation_delete_text)
                        .setMessage(R.string.delete_confirmation_text)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GraduationService GraduationService = new GraduationService(v.getContext());
                                try {
                                    GraduationService.delete(mDataset.get(position));
                                } catch (Exception e) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle(R.string.dialog_error)
                                            .setMessage(e.getMessage())
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                                mDataset.remove(mDataset.get(position));
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
