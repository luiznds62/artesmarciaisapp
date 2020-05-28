package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.PlanModel;

import java.util.ArrayList;
import java.util.List;

public class PlanDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    PlanModel.COLUNA_MODALIDADE,
                    PlanModel.COLUNA_PLANO,
                    PlanModel.COLUNA_VALOR_MENSAL
            };

    public PlanDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(final PlanModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(PlanModel.COLUNA_MODALIDADE, model.getModalidade());
            values.put(PlanModel.COLUNA_PLANO, model.getPlano());
            values.put(PlanModel.COLUNA_VALOR_MENSAL, model.getValor_mensal());

            rowAffect = db.insert(PlanModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public long update(final PlanModel model, String wherePlan) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(PlanModel.COLUNA_MODALIDADE, model.getModalidade());
            values.put(PlanModel.COLUNA_PLANO, model.getPlano());
            values.put(PlanModel.COLUNA_VALOR_MENSAL, model.getValor_mensal());

            rowAffect = db.update(PlanModel.TABELA_NOME, values, PlanModel.COLUNA_MODALIDADE + "= ?", new String[]{wherePlan});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public List<PlanModel> select() {

        List<PlanModel> lo_arl_dados = new ArrayList<PlanModel>();

        try {
            Open();

            Cursor cursor = db.query(PlanModel.TABELA_NOME, colunas, null, null, null, null, null);
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

    public PlanModel getByModality(String modality) {

        PlanModel model = null;

        try {
            Open();

            String selectQuery = "SELECT * FROM " + PlanModel.TABELA_NOME + " WHERE MODALIDADE = \"" + modality + "\"";
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

    public void delete(final String modality) {
        Open();
        db.delete(PlanModel.TABELA_NOME,
                PlanModel.COLUNA_MODALIDADE + "= ?",
                new String[]{modality});
        Close();
    }

    private PlanModel CursorToStructure(final Cursor cursor) {
        PlanModel model = new PlanModel();
        try {
            model.setModalidade(cursor.getString(0));
            model.setPlano(cursor.getString(1));
            model.setValor_mensal(cursor.getDouble(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
