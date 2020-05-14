package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class MatriculationDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    MatriculationModel.COLUNA_CODIGO_MATRICULA,
                    MatriculationModel.COLUNA_ALUNO,
                    MatriculationModel.COLUNA_DATA_MATRICULA,
                    MatriculationModel.COLUNA_DIA_VENCIMENTO,
                    MatriculationModel.COLUNA_DATA_ENCERRAMENTO
            };

    public MatriculationDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public MatriculationModel getByCodigoAluno(int codigo_aluno) {

        MatriculationModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + MatriculationModel.TABELA_NOME + " WHERE CODIGO_ALUNO = '" + codigo_aluno + "';";
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

    public long insert(final MatriculationModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(MatriculationModel.COLUNA_ALUNO, model.getCodigo_aluno());
            values.put(MatriculationModel.COLUNA_DATA_MATRICULA, model.getData_matricula());
            values.put(MatriculationModel.COLUNA_DIA_VENCIMENTO, model.getDia_vencimento());
            values.put(MatriculationModel.COLUNA_DATA_ENCERRAMENTO, model.getData_encerramento());

            rowAffect = db.insert(MatriculationModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public long update(final MatriculationModel model, String whereCodigoMatricula) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(MatriculationModel.COLUNA_ALUNO, model.getCodigo_aluno());
            values.put(MatriculationModel.COLUNA_DATA_MATRICULA, model.getData_matricula());
            values.put(MatriculationModel.COLUNA_DIA_VENCIMENTO, model.getDia_vencimento());
            values.put(MatriculationModel.COLUNA_DATA_ENCERRAMENTO, model.getData_encerramento());

            rowAffect = db.update(MatriculationModel.TABELA_NOME, values, MatriculationModel.COLUNA_CODIGO_MATRICULA + "= ?", new String[]{whereCodigoMatricula});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public List<MatriculationModel> select() {

        List<MatriculationModel> lo_arl_dados = new ArrayList<MatriculationModel>();

        try {
            Open();

            Cursor cursor = db.query(MatriculationModel.TABELA_NOME, colunas, null, null, null, null, null);
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

    public void delete(final String codigoMatricula) {
        Open();
        db.delete(MatriculationModel.TABELA_NOME,
                MatriculationModel.COLUNA_CODIGO_MATRICULA + "= ?",
                new String[]{codigoMatricula});
        Close();
    }

    private MatriculationModel CursorToStructure(final Cursor cursor) {
        MatriculationModel model = new MatriculationModel();
        try {
            model.setCodigo_matricula(cursor.getInt(0));
            model.setCodigo_aluno(cursor.getInt(1));
            model.setData_matricula(cursor.getString(2));
            model.setDia_vencimento(cursor.getString(3));
            model.setData_encerramento(cursor.getString(4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
