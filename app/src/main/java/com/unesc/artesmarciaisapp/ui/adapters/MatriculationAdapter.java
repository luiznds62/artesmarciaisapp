package com.unesc.artesmarciaisapp.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.MatriculationService;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.util.List;
import java.util.stream.Collectors;

public class MatriculationAdapter extends RecyclerView.Adapter<MatriculationAdapter.MatriculationHolder>{
    public List<MatriculationModel> mDataset;

    public static class MatriculationHolder extends RecyclerView.ViewHolder {
        public ImageButton btnMatriculationDelete;
        public TextView txvMatriculationStudent;
        public TextView txvMatriculationDate;
        public TextView txvMatriculationPayDate;

        public MatriculationHolder(View v) {
            super(v);

            btnMatriculationDelete = v.findViewById(R.id.btnMatriculationDelete);
            txvMatriculationStudent = v.findViewById(R.id.txvMatriculationStudent);
            txvMatriculationDate = v.findViewById(R.id.txvMatriculationDate);
            txvMatriculationPayDate = v.findViewById(R.id.txvMatriculationPayDate);
        }
    }

    public MatriculationAdapter(List<MatriculationModel> matriculationList) {
        mDataset = matriculationList;
    }

    public void filterDataset(final String filterExpression, Context context){
        MatriculationService matriculationService = new MatriculationService(context);

        mDataset = matriculationService.getAll(context).stream().filter(it ->
                it.getStudent().getAluno().toLowerCase().contains(filterExpression) ||
                        it.getDia_vencimento().toLowerCase().contains(filterExpression) ||
                        it.getData_encerramento().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @Override
    public MatriculationAdapter.MatriculationHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matriculations_row, parent, false);

        MatriculationHolder vh = new MatriculationHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MatriculationHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.btnMatriculationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar matrícula")
                        .setMessage("Tem certeza que deseja deletar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MatriculationService matriculationService = new MatriculationService(v.getContext());
                                matriculationService.delete(mDataset.get(position));
                                mDataset.remove(mDataset.get(position));
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        holder.txvMatriculationStudent.setText(mDataset.get(position).getStudent().getAluno());
        holder.txvMatriculationDate.setText("Data da matrícula: " + mDataset.get(position).getData_matricula());
        holder.txvMatriculationPayDate.setText("Vencimento: " + mDataset.get(position).getDia_vencimento());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
