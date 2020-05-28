package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.ModalityModel;

import java.util.ArrayList;
import java.util.List;

public class ModalityDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    ModalityModel.COLUNA_MODALIDADE,
            };

    public ModalityDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(final ModalityModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(ModalityModel.COLUNA_MODALIDADE, model.getModalidade());

            rowAffect = db.insert(ModalityModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public long update(final ModalityModel model, String whereModalidade) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(ModalityModel.COLUNA_MODALIDADE, model.getModalidade());

            rowAffect = db.update(ModalityModel.TABELA_NOME, values, ModalityModel.COLUNA_MODALIDADE + "= ?", new String[]{whereModalidade});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public List<ModalityModel> select() {

        List<ModalityModel> lo_arl_dados = new ArrayList<ModalityModel>();

        try {
            Open();

            Cursor cursor = db.query(ModalityModel.TABELA_NOME, colunas, null, null, null, null, null);
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

    public ModalityModel getByName(String name) {

        ModalityModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + ModalityModel.TABELA_NOME + " WHERE MODALIDADE = \"" + name + "\"";
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

    public void delete(final String modalidade) {
        Open();
        db.delete(ModalityModel.TABELA_NOME,
                ModalityModel.COLUNA_MODALIDADE + "= ?",
                new String[]{modalidade});
        Close();
    }

    private ModalityModel CursorToStructure(final Cursor cursor) {
        ModalityModel model = new ModalityModel();
        try {
            model.setModalidade(cursor.getString(0));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
