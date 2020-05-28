package com.unesc.artesmarciaisapp.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.services.ModalityService;
import com.unesc.artesmarciaisapp.ui.graduation.GraduationActivity;
import com.unesc.artesmarciaisapp.ui.matriculation.UpdateMatriculationActivity;
import com.unesc.artesmarciaisapp.ui.modality.UpdateModalityActivity;

import java.util.List;
import java.util.stream.Collectors;

public class ModalityAdapter extends RecyclerView.Adapter<ModalityAdapter.ModalityHolder>{
    public List<ModalityModel> mDataset;

    public static class ModalityHolder extends RecyclerView.ViewHolder {
        public ImageButton btnModalityDelete;
        public ImageButton btnModalityUpdate;
        public Button btnModalityAddGratuation;
        public TextView txvModalityName;

        public ModalityHolder(View v) {
            super(v);

            btnModalityAddGratuation = v.findViewById(R.id.btnModalityAddGratuation);
            btnModalityUpdate = v.findViewById(R.id.btnModalityUpdate);
            btnModalityDelete = v.findViewById(R.id.btnModalityDelete);
            txvModalityName = v.findViewById(R.id.txvModalityName);
        }
    }

    public ModalityAdapter(List<ModalityModel> matriculationList) {
        mDataset = matriculationList;
    }

    public void filterDataset(final String filterExpression, Context context) {
        ModalityService modalityService = new ModalityService(context);

        mDataset = modalityService.getAll().stream().filter(it ->
                it.getModalidade().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @Override
    public ModalityAdapter.ModalityHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modalities_row, parent, false);

        ModalityHolder vh = new ModalityHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ModalityHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txvModalityName.setText(mDataset.get(position).getModalidade());

        holder.btnModalityAddGratuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GraduationActivity.class);
                intent.putExtra("modalidade", mDataset.get(position).getModalidade());
                v.getContext().startActivity(intent);
            }
        });

        holder.btnModalityUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateModalityActivity.class);
                intent.putExtra("modalidade", mDataset.get(position).getModalidade());
                ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });

        holder.btnModalityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.text_modality_delete_text)
                        .setMessage(R.string.delete_confirmation_text)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ModalityService ModalityService = new ModalityService(v.getContext());
                                try {
                                    ModalityService.delete(mDataset.get(position));
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
