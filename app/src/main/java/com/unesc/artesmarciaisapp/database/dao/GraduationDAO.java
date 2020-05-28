package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.GraduationModel;

import java.util.ArrayList;
import java.util.List;

public class GraduationDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    GraduationModel.COLUNA_MODALIDADE,
                    GraduationModel.COLUNA_GRADUACAO
            };

    public GraduationDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(final GraduationModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(GraduationModel.COLUNA_MODALIDADE, model.getModalidade());
            values.put(GraduationModel.COLUNA_GRADUACAO, model.getGraduacao());

            rowAffect = db.insert(GraduationModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public void updateModalityName(String modalityBefore, String modalityAfter){
        try {
            Open();

            String updateQuery = "UPDATE " + GraduationModel.TABELA_NOME + " SET " + GraduationModel.COLUNA_MODALIDADE
                    + " = '" + modalityAfter + "'"
                    + " WHERE " + GraduationModel.COLUNA_MODALIDADE
                    + " = '" + modalityBefore + "';";
            Cursor cursor = db.rawQuery(updateQuery, null);
            cursor.moveToFirst();

            cursor.close();
        } finally {
            Close();
        }
    }

    public long update(final GraduationModel model, String whereModalidade, String whereGraduacao) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(GraduationModel.COLUNA_GRADUACAO, model.getModalidade());
            values.put(GraduationModel.COLUNA_GRADUACAO, model.getGraduacao());

            rowAffect = db.update(GraduationModel.TABELA_NOME, values,
                    GraduationModel.COLUNA_MODALIDADE + "= ? AND "
                            + GraduationModel.COLUNA_GRADUACAO + "= ?",
                    new String[]{whereModalidade,whereGraduacao});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public GraduationModel getByModalidadeName(String modalidade, String graduacao) {

        GraduationModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + GraduationModel.TABELA_NOME +
                    " WHERE " + GraduationModel.COLUNA_MODALIDADE + "= '" + modalidade + "'" +
                    " AND " + GraduationModel.COLUNA_GRADUACAO + "= '" + graduacao + "';";
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

    public List<GraduationModel> select() {

        List<GraduationModel> lo_arl_dados = new ArrayList<GraduationModel>();

        try {
            Open();

            Cursor cursor = db.query(GraduationModel.TABELA_NOME, colunas, null, null, null, null, null);
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

    public List<GraduationModel> getByModalidade(String modalidade) {

        List<GraduationModel> lo_arl_dados = new ArrayList<GraduationModel>();

        try {
            Open();

            String selectQuery = "SELECT * FROM " + GraduationModel.TABELA_NOME + " WHERE MODALIDADE = \'" + modalidade + "\';";
            Cursor cursor = db.rawQuery(selectQuery, null);
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

    public void delete(final String modalidade, String graduacao) {
        Open();
        db.delete(GraduationModel.TABELA_NOME,
                GraduationModel.COLUNA_MODALIDADE + "= ? AND " + GraduationModel.COLUNA_GRADUACAO + "= ?",
                new String[]{modalidade, graduacao});
        Close();
    }

    private GraduationModel CursorToStructure(final Cursor cursor) {
        GraduationModel model = new GraduationModel();
        try {
            model.setModalidade(cursor.getString(0));
            model.setGraduacao(cursor.getString(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
