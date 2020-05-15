package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.MatriculationDAO;
import com.unesc.artesmarciaisapp.database.dao.StudentDAO;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.List;
import java.util.Optional;

public class MatriculationService {
    private MatriculationDAO dao;

    public MatriculationService(Context context) {
        this.dao = new MatriculationDAO(context);
    }

    public List<MatriculationModel> getAll(Context context) {
        StudentService studentService = new StudentService(context);
        List<MatriculationModel> lst =  this.dao.select();
        for(MatriculationModel model : lst){
            model.setStudent(studentService.getByCodigoAluno(model.getCodigo_aluno()));
        }

        return lst;
    }

    public void delete(MatriculationModel entityDelete) {
        this.dao.delete(String.valueOf(entityDelete.getCodigo_matricula()));
    }

    public MatriculationModel create(final MatriculationModel newEntity) throws Exception {
        if(this.dao.getByCodigoAluno(newEntity.getCodigo_aluno()) != null){
            throw new Exception("Aluno já matrículado");
        }

        if(newEntity.getData_matricula().equals("")){
            throw new Exception("É necessário informar a data da matrícula");
        }

        if(newEntity.getDia_vencimento().equals("")){
            throw new Exception("É necessário informar o dia de vencimento");
        }

        if(newEntity.getDia_vencimento().length() > 2){
            throw new Exception("Dia de vencimento inválido");
        }

        try {
            int payDay = Integer.parseInt(newEntity.getDia_vencimento());
            if(payDay < 0 || payDay > 15){
                throw new Exception("Dia de vencimento deve estar entre os dias 1 ~ 15");
            }
        }catch (Exception e){
            throw new Exception("Dia de vencimento inválido");
        }

        this.dao.insert(newEntity);
        return this.dao.getByCodigoAluno(newEntity.getCodigo_aluno());
    }

    public MatriculationModel update(MatriculationModel updateEntity) throws Exception {
        this.dao.update(updateEntity,String.valueOf(updateEntity.getCodigo_matricula()));
        return this.dao.getByCodigoAluno(updateEntity.getCodigo_aluno());
    }
}
