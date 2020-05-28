package com.unesc.artesmarciaisapp.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.PlanModel;
import com.unesc.artesmarciaisapp.services.PlanService;
import com.unesc.artesmarciaisapp.ui.graduation.GraduationActivity;
import com.unesc.artesmarciaisapp.ui.modality.UpdateModalityActivity;

import java.util.List;
import java.util.stream.Collectors;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanHolder> {
    public List<PlanModel> mDataset;

    public static class PlanHolder extends RecyclerView.ViewHolder {
        public ImageButton btnPlanDelete;
        public ImageButton btnPlanUpdate;
        public Button btnPlanAdd;
        public TextView txvPlanModality;
        public TextView txvPlanName;
        public TextView txvPlanValue;

        public PlanHolder(View v) {
            super(v);

            btnPlanDelete = v.findViewById(R.id.btnPlanDelete);
            btnPlanUpdate = v.findViewById(R.id.btnPlanUpdate);
            btnPlanAdd = v.findViewById(R.id.btnPlanAdd);
            txvPlanModality = v.findViewById(R.id.txvPlanModality);
            txvPlanName = v.findViewById(R.id.txvPlanName);
            txvPlanValue = v.findViewById(R.id.txvPlanValue);
        }
    }

    public PlanAdapter(List<PlanModel> matriculationList) {
        mDataset = matriculationList;
    }

    public void filterDataset(final String filterExpression, Context context) {
        PlanService planService = new PlanService(context);

        mDataset = planService.getAll().stream().filter(it ->
                it.getModalidade().toLowerCase().contains(filterExpression) ||
                        it.getTitle().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @Override
    public PlanAdapter.PlanHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_row, parent, false);

        PlanHolder vh = new PlanHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlanHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txvPlanModality.setText(mDataset.get(position).getModalidade());
        holder.txvPlanName.setText( mDataset.get(position).getTitle());
        holder.txvPlanValue.setText("R$" +mDataset.get(position).getValor_mensal().toString());

        holder.btnPlanUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), UpdateModalityActivity.class);
//                intent.putExtra("modalidade", mDataset.get(position).getModalidade());
//                ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });

        holder.btnPlanDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.text_plan_delete_text)
                        .setMessage(R.string.delete_confirmation_text)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PlanService planService = new PlanService(v.getContext());
                                try {
                                    planService.delete(mDataset.get(position));
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
