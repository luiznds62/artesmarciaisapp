package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.StudentDAO;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private StudentDAO dao;

    public StudentService(Context context) {
        this.dao = new StudentDAO(context);
    }

    public List<StudentModel> getAll() {
        return this.dao.select();
    }

    public StudentModel getByCodigoAluno(int codigo){
        return this.dao.getById(codigo);
    }

    public void delete(StudentModel entityDelete) {
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

        this.dao.update(updateEntity,String.valueOf(this.dao.getByName(updateEntity.getAluno()).getCodigo_aluno()));
        return this.dao.getByName(updateEntity.getAluno());
    }
}
