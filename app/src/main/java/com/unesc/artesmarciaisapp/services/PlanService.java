package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.PlanDAO;
import com.unesc.artesmarciaisapp.models.PlanModel;

import java.util.List;
import java.util.Optional;

public class PlanService {
    private PlanDAO dao;
    private Context context;

    public PlanService(Context context) {
        this.dao = new PlanDAO(context);
        this.context = context;
    }

    public List<PlanModel> getAll() {
        return this.dao.select();
    }

    public PlanModel getByModalidade(String modalidade) throws Exception {
        if(modalidade.equals("")){
           throw new Exception("Nome da modalidade não informado");
        }

        return this.dao.getByModality(modalidade);
    }

    public void delete(PlanModel entityDelete) throws Exception {
        // TODO Verificar se tem fatura vinculada

        this.dao.delete(String.valueOf(entityDelete.getModalidade()));
    }

    public PlanModel create(final PlanModel newEntity) throws Exception {
        if (newEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar o nome da modalidade");
        }

        if(newEntity.getPlano().equals("")){
            throw new Exception("Nome do plano não informado");
        }

        if(newEntity.getValor_mensal() == null){
            throw new Exception("Valor do plano não informado");
        }

        Optional<PlanModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(newEntity.getModalidade())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Plano com essa modalidade já cadastrado");
        }

        this.dao.insert(newEntity);
        return this.dao.getByModality(newEntity.getModalidade());
    }

    public PlanModel update(PlanModel updateEntity,String name) throws Exception {
        if (updateEntity.getModalidade().equals("")) {
            throw new Exception("Necessário informar o nome da modalidade");
        }

        if(updateEntity.getPlano().equals("")){
            throw new Exception("Nome do plano não informado");
        }

        if(updateEntity.getValor_mensal() == null){
            throw new Exception("Valor do plano não informado");
        }

        Optional<PlanModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getModalidade().equals(updateEntity.getModalidade())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Modalidade com este nome já cadastrado");
        }

        this.dao.update(updateEntity,name);
        return this.dao.getByModality(updateEntity.getModalidade());
    }
}
