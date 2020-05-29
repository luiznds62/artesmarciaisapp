package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    StudentModel.COLUNA_CODIGO_ALUNO,
                    StudentModel.COLUNA_ALUNO,
                    StudentModel.COLUNA_DATA_NASCIMENTO,
                    StudentModel.COLUNA_SEXO,
                    StudentModel.COLUNA_TELEFONE,
                    StudentModel.COLUNA_CELULAR,
                    StudentModel.COLUNA_EMAIL,
                    StudentModel.COLUNA_OBSERVACAO,
                    StudentModel.COLUNA_ENDERECO,
                    StudentModel.COLUNA_NUMERO,
                    StudentModel.COLUNA_COMPLEMENTO,
                    StudentModel.COLUNA_BAIRRO,
                    StudentModel.COLUNA_CIDADE,
                    StudentModel.COLUNA_ESTADO,
                    StudentModel.COLUNA_PAIS,
                    StudentModel.COLUNA_CEP,
                    StudentModel.COLUNA_CREATED_DATE
            };

    public StudentDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(final StudentModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(StudentModel.COLUNA_ALUNO, model.getAluno());
            values.put(StudentModel.COLUNA_DATA_NASCIMENTO, model.getData_nascimento());
            values.put(StudentModel.COLUNA_SEXO, model.getSexo());
            values.put(StudentModel.COLUNA_TELEFONE, model.getTelefone());
            values.put(StudentModel.COLUNA_CELULAR, model.getCelular());
            values.put(StudentModel.COLUNA_EMAIL, model.getEmail());
            values.put(StudentModel.COLUNA_OBSERVACAO, model.getObservacao());
            values.put(StudentModel.COLUNA_ENDERECO, model.getEndereco());
            values.put(StudentModel.COLUNA_NUMERO, model.getNumero());
            values.put(StudentModel.COLUNA_COMPLEMENTO, model.getComplemento());
            values.put(StudentModel.COLUNA_BAIRRO, model.getBairro());
            values.put(StudentModel.COLUNA_CIDADE, model.getCidade());
            values.put(StudentModel.COLUNA_ESTADO, model.getEstado());
            values.put(StudentModel.COLUNA_PAIS, model.getPais());
            values.put(StudentModel.COLUNA_CEP, model.getCep());
            values.put(StudentModel.COLUNA_CREATED_DATE, model.getCreatedDate());

            rowAffect = db.insert(StudentModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public long update(final StudentModel model, String whereCodigoAluno) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(StudentModel.COLUNA_ALUNO, model.getAluno());
            values.put(StudentModel.COLUNA_DATA_NASCIMENTO, model.getData_nascimento());
            values.put(StudentModel.COLUNA_SEXO, model.getSexo());
            values.put(StudentModel.COLUNA_TELEFONE, model.getTelefone());
            values.put(StudentModel.COLUNA_CELULAR, model.getCelular());
            values.put(StudentModel.COLUNA_EMAIL, model.getEmail());
            values.put(StudentModel.COLUNA_OBSERVACAO, model.getObservacao());
            values.put(StudentModel.COLUNA_ENDERECO, model.getEndereco());
            values.put(StudentModel.COLUNA_NUMERO, model.getNumero());
            values.put(StudentModel.COLUNA_COMPLEMENTO, model.getComplemento());
            values.put(StudentModel.COLUNA_BAIRRO, model.getBairro());
            values.put(StudentModel.COLUNA_CIDADE, model.getCidade());
            values.put(StudentModel.COLUNA_ESTADO, model.getEstado());
            values.put(StudentModel.COLUNA_PAIS, model.getPais());
            values.put(StudentModel.COLUNA_CEP, model.getCep());
            values.put(StudentModel.COLUNA_CREATED_DATE, model.getCreatedDate());

            rowAffect = db.update(StudentModel.TABELA_NOME, values, StudentModel.COLUNA_CODIGO_ALUNO + "= ?", new String[]{whereCodigoAluno});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public List<StudentModel> select() {

        List<StudentModel> lo_arl_dados = new ArrayList<StudentModel>();

        try {
            Open();

            Cursor cursor = db.query(StudentModel.TABELA_NOME, colunas, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                lo_arl_dados.add(CursorToStructure(cursor));
                cursor.moveToNext();
            }

            cursor.close();
        } finally {
            Close();
        }

        return lo_arl_dados;
    }

    public StudentModel getById(String codigo_aluno) {

        StudentModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + StudentModel.TABELA_NOME + " WHERE " + StudentModel.COLUNA_CODIGO_ALUNO + " = '" + codigo_aluno + "';";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                model = CursorToStructure(cursor);
            }

            cursor.close();
        } finally {
            Close();
        }

        return model;
    }

    public StudentModel getByName(String name) {

        StudentModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + StudentModel.TABELA_NOME + " WHERE ALUNO = '" + name + "';";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                model = CursorToStructure(cursor);
            }

            cursor.close();
        } finally {
            Close();
        }

        return model;
    }

    public StudentModel getByEmail(String email) {

        StudentModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + StudentModel.TABELA_NOME + " WHERE " + StudentModel.COLUNA_EMAIL + " = '" + email + "';";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                model = CursorToStructure(cursor);
            }

            cursor.close();
        } finally {
            Close();
        }

        return model;
    }

    public StudentModel getById(int id) {

        StudentModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + StudentModel.TABELA_NOME + " WHERE CODIGO_ALUNO = '" + id + "';";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                model = CursorToStructure(cursor);
            }

            cursor.close();
        } finally {
            Close();
        }

        return model;
    }

    public void delete(final String codigoAluno) {
        Open();
        db.delete(StudentModel.TABELA_NOME,
                StudentModel.COLUNA_CODIGO_ALUNO + "= ?",
                new String[]{codigoAluno});
        Close();
    }

    private StudentModel CursorToStructure(final Cursor cursor) {
        StudentModel model = new StudentModel();
        try {
            model.setCodigo_aluno(cursor.getInt(0));
            model.setAluno(cursor.getString(1));
            model.setData_nascimento(cursor.getString(2));
            model.setSexo(cursor.getString(3));
            model.setTelefone(cursor.getString(4));
            model.setCelular(cursor.getString(5));
            model.setEmail(cursor.getString(6));
            model.setObservacao(cursor.getString(7));
            model.setEndereco(cursor.getString(8));
            model.setNumero(cursor.getString(9));
            model.setComplemento(cursor.getString(10));
            model.setBairro(cursor.getString(11));
            model.setCidade(cursor.getString(12));
            model.setEstado(cursor.getString(13));
            model.setPais(cursor.getString(14));
            model.setCep(cursor.getString(15));
            model.setCreatedDate(cursor.getString(16));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
