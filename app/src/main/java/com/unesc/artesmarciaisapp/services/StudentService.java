package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.StudentDAO;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private StudentDAO dao;
    private MatriculationService matriculationService;

    public StudentService(Context context) {
        this.dao = new StudentDAO(context);
        this.matriculationService = new MatriculationService(context);
    }

    public List<StudentModel> getAll() {
        return this.dao.select();
    }

    public StudentModel getByCodigoAluno(int codigo) throws Exception {
        if(codigo == 0){
           throw new Exception("Código do aluno não informado");
        }

        return this.dao.getById(codigo);
    }

    public void delete(StudentModel entityDelete,Context context) throws Exception {
        if(this.matriculationService.getByCodigoAluno(entityDelete.getCodigo_aluno(),context) != null){
            throw new Exception("É necessário excluir a matrícula antes de excluir o aluno");
        }

        this.dao.delete(String.valueOf(entityDelete.getCodigo_aluno()));
    }

    public StudentModel create(final StudentModel newEntity) throws Exception {
        if (newEntity.getAluno().equals("")) {
            throw new Exception("Necessário informar o nome");
        }

        if (newEntity.getData_nascimento().equals("")) {
            throw new Exception("Necessário informar a data de nascimento");
        }

        if (newEntity.getSexo().equals("")) {
            throw new Exception("Necessário informar o sexo");
        }

        if (newEntity.getBairro().equals("")) {
            throw new Exception("Necessário informar o bairro");
        }

        if (newEntity.getCidade().equals("")) {
            throw new Exception("Necessário informar a cidade");
        }

        if (newEntity.getEstado().equals("")) {
            throw new Exception("Necessário informar o estado");
        }

        if (newEntity.getPais().equals("")) {
            throw new Exception("Necessário informar o país");
        }

        if (newEntity.getCep().equals("")) {
            throw new Exception("Necessário informar o CEP");
        }

        if (newEntity.getCelular().equals("")) {
            throw new Exception("Necessário informar o celular");
        }

        if(!ValidatorService.isValidEmailAddress(newEntity.getEmail())){
            throw new Exception("Email inválido");
        }

        if(this.dao.getByEmail(newEntity.getEmail()) != null){
            throw new Exception("Email já utilizado");
        }

        Optional<StudentModel> existingEntity = this.dao.select().stream().filter(c ->
                c.getAluno().equals(newEntity.getAluno())).findAny();
        if(existingEntity.isPresent()){
            throw new Exception("Aluno com este nome já cadastrado");
        }

        this.dao.insert(newEntity);
        return this.dao.getByName(newEntity.getAluno());
    }

    public StudentModel update(StudentModel updateEntity) throws Exception {
        if (updateEntity.getAluno().equals("")) {
            throw new Exception("Necessário informar o nome");
        }

        if (updateEntity.getData_nascimento().equals("")) {
            throw new Exception("Necessário informar a data de nascimento");
        }

        if (updateEntity.getSexo().equals("")) {
            throw new Exception("Necessário informar o sexo");
        }

        if (updateEntity.getBairro().equals("")) {
            throw new Exception("Necessário informar o bairro");
        }

        if (updateEntity.getCidade().equals("")) {
            throw new Exception("Necessário informar a cidade");
        }

        if (updateEntity.getEstado().equals("")) {
            throw new Exception("Necessário informar o estado");
        }

        if (updateEntity.getPais().equals("")) {
            throw new Exception("Necessário informar o país");
        }

        if (updateEntity.getCep().equals("")) {
            throw new Exception("Necessário informar o CEP");
        }

        if (updateEntity.getCelular().equals("")) {
            throw new Exception("Necessário informar o celular");
        }

        if(this.dao.getByEmail(updateEntity.getEmail()).getCodigo_aluno() != updateEntity.getCodigo_aluno()){
            throw new Exception("Email já utilizado");
        }

        this.dao.update(updateEntity,String.valueOf(updateEntity.getCodigo_aluno()));
        return this.dao.getByName(updateEntity.getAluno());
    }
}
