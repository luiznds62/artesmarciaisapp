package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.MatriculationDAO;
import com.unesc.artesmarciaisapp.database.dao.StudentDAO;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.List;
import java.util.Optional;

public class MatriculationService {
    MatriculationDAO dao;

    public MatriculationService(Context context) {
        this.dao = new MatriculationDAO(context);
    }

    public List<MatriculationModel> getAll() {
        return this.dao.select();
    }

    public void delete(MatriculationModel entityDelete) {
        this.dao.delete(String.valueOf(entityDelete.getCodigo_matricula()));
    }

    public MatriculationModel create(final MatriculationModel newEntity) throws Exception {
        this.dao.insert(newEntity);
        return this.dao.getByCodigoAluno(newEntity.getCodigo_aluno());
    }

    public MatriculationModel update(MatriculationModel updateEntity) throws Exception {
        this.dao.update(updateEntity,String.valueOf(updateEntity.getCodigo_matricula()));
        return this.dao.getByCodigoAluno(updateEntity.getCodigo_aluno());
    }
}
