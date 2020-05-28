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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.StudentService;
import com.unesc.artesmarciaisapp.ui.student.UpdateStudentActivity;

import java.util.List;
import java.util.stream.Collectors;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.AlunosHolder>{
    public List<StudentModel> mDataset;
    private final List<StudentModel> mDatasetAux;

    public static class AlunosHolder extends RecyclerView.ViewHolder {
        public ImageButton btnStudentUpdate;
        public ImageButton btnStudentDelete;
        public TextView txvAlunosRowNome;
        public TextView txvAlunosRowCelular;
        public TextView txvAlunosRowBairro;

        public AlunosHolder(View v) {
            super(v);

            btnStudentUpdate = v.findViewById(R.id.btnStudentUpdate);
            btnStudentDelete = v.findViewById(R.id.btnStudentDelete);
            txvAlunosRowNome = v.findViewById(R.id.txvAlunosRowNome);
            txvAlunosRowCelular = v.findViewById(R.id.txvAlunosRowCelular);
            txvAlunosRowBairro = v.findViewById(R.id.txvAlunosRowBairro);
        }
    }

    public StudentAdapter(List<StudentModel> revenuesList) {
        mDatasetAux = revenuesList;
        mDataset = revenuesList;
    }

    public void filterDataset(final String filterExpression, Context context){
        StudentService studentService = new StudentService(context);

        mDataset = studentService.getAll().stream().filter(it ->
                it.getAluno().toLowerCase().contains(filterExpression) ||
                        it.getData_nascimento().toLowerCase().contains(filterExpression) ||
                        it.getCidade().toLowerCase().contains(filterExpression) ||
                        it.getSexo().toLowerCase().contains(filterExpression) ||
                        it.getBairro().toLowerCase().contains(filterExpression)
        ).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public void resetDataset(){
        mDataset = mDatasetAux;
        notifyDataSetChanged();
    }

    @Override
    public StudentAdapter.AlunosHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.students_row, parent, false);

        AlunosHolder vh = new AlunosHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AlunosHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.btnStudentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateStudentActivity.class);
                intent.putExtra("codigo_aluno", mDataset.get(position).getCodigo_aluno());
                ((Activity) v.getContext()).startActivityForResult(intent, 0);
            }
        });

        holder.btnStudentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar aluno")
                        .setMessage("Tem certeza que deseja deletar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StudentService studentService = new StudentService(v.getContext());
                                try {
                                    studentService.delete(mDataset.get(position),v.getContext());
                                    mDataset.remove(mDataset.get(position));
                                    notifyDataSetChanged();
                                    dialog.dismiss();
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
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        holder.txvAlunosRowNome.setText(mDataset.get(position).getAluno());
        holder.txvAlunosRowCelular.setText(mDataset.get(position).getCelular());
        holder.txvAlunosRowBairro.setText(mDataset.get(position).getBairro());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
