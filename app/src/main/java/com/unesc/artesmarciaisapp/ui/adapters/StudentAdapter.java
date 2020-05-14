package com.unesc.artesmarciaisapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.util.List;
import java.util.stream.Collectors;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.AlunosHolder>{
    public List<StudentModel> mDataset;
    private final List<StudentModel> mDatasetAux;

    public static class AlunosHolder extends RecyclerView.ViewHolder {
        public TextView txvAlunosRowNome;
        public TextView txvAlunosRowCelular;
        public TextView txvAlunosRowBairro;

        public AlunosHolder(View v) {
            super(v);

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
        holder.txvAlunosRowNome.setText(mDataset.get(position).getAluno());
        holder.txvAlunosRowCelular.setText(mDataset.get(position).getCelular());
        holder.txvAlunosRowBairro.setText(mDataset.get(position).getBairro());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
