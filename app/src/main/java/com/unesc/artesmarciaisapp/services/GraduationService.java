package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.GraduationDAO;
import com.unesc.artesmarciaisapp.models.GraduationModel;

import java.util.List;
import java.util.Optional;

public class GraduationService {
    private GraduationDAO dao;

    public GraduationService(Context context) {
        this.dao = new GraduationDAO(context);
    }

    public List<GraduationModel> getAll() {
        return this.dao.select();
    }

    public void updateModalityName(String modalityBefore,String modalityAfter){
        this.dao.updateModalityName(modalityBefore,modalityAfter);
    }

    public List<GraduationModel> getByModalidade(String modalidade) throws Exception {
        if(modalidade.equals("")){
           throw new Exception("Nome da modalidade não informado");
        }

        return this.dao.getByModalidade(modalidade);
    }

    public void delete(GraduationModel entityDelete) throws Exception {
        //TODO --> VERIFICAR SE EXISTE ALUNOS COM ESSAS GRADUAÇÕES VINCULADAS

        this.dao.delete(entityDelete.getModalidade(),entityDelete.getGraduacao());
    }

    public GraduationModel create(final GraduationModel newEntity) throws Exception {
        if (newEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar a modalidade");
        }

        if (newEntity.getGraduacao().equals("")) {
            throw new Exception("Necessário informar o nome da graduação");
        }

        Optional<GraduationModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(newEntity.getModalidade()) &&
                c.getGraduacao().equals(newEntity.getGraduacao())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Graduação com este nome já cadastrado para esta modalidade");
        }

        this.dao.insert(newEntity);
        return this.dao.getByModalidadeName(newEntity.getModalidade(),newEntity.getGraduacao());
    }

    public GraduationModel getByModalityGraduation(String modality,String graduation){
        return this.dao.getByModalidadeName(modality,graduation);
    }

    public GraduationModel update(GraduationModel updateEntity,String modalidade,String graduacao) throws Exception {
        if (updateEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar o nome da modalidade");
        }

        if (updateEntity.getGraduacao().equals("")) {
            throw new Exception("Necessário informar o nome da graduação");
        }

        Optional<GraduationModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(updateEntity.getModalidade()) &&
                c.getGraduacao().equals(updateEntity.getGraduacao())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Graduação com este nome já cadastrado para esta modalidade");
        }

        this.dao.update(updateEntity,modalidade,graduacao);
        return this.dao.getByModalidadeName(updateEntity.getModalidade(),updateEntity.getGraduacao());
    }
}
