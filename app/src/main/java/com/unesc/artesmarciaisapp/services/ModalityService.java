package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.ModalityDAO;
import com.unesc.artesmarciaisapp.models.ModalityModel;

import java.util.List;
import java.util.Optional;

public class ModalityService {
    private ModalityDAO dao;
    private Context context;

    public ModalityService(Context context) {
        this.dao = new ModalityDAO(context);
        this.context = context;
    }

    public List<ModalityModel> getAll() {
        return this.dao.select();
    }

    public ModalityModel getByModalidade(String modalidade) throws Exception {
        if(modalidade.equals("")){
           throw new Exception("Nome da modalidade não informado");
        }

        return this.dao.getByName(modalidade);
    }

    public void delete(ModalityModel entityDelete) throws Exception {
        GraduationService graduationService = new GraduationService(context);
        if(!graduationService.getByModalidade(entityDelete.getModalidade()).isEmpty()){
            throw new Exception("Existe graduação vinculada a essa modalidade");
        }

        // TODO Verificar se tem plano

        this.dao.delete(String.valueOf(entityDelete.getModalidade()));
    }

    public ModalityModel create(final ModalityModel newEntity) throws Exception {
        if (newEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar o nome da modalidade");
        }

        Optional<ModalityModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(newEntity.getModalidade())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Modalidade com este nome já cadastrado");
        }

        this.dao.insert(newEntity);
        return this.dao.getByName(newEntity.getModalidade());
    }

    public ModalityModel update(ModalityModel updateEntity,String name) throws Exception {
        if (updateEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar o nome da modalidade");
        }

        Optional<ModalityModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(updateEntity.getModalidade())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Modalidade com este nome já cadastrado");
        }

        GraduationService graduationService = new GraduationService(this.context);
        graduationService.updateModalityName(name,updateEntity.getModalidade());
        this.dao.update(updateEntity,name);
        return this.dao.getByName(updateEntity.getModalidade());
    }
}
