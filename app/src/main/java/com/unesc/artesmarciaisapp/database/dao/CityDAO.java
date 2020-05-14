package com.unesc.artesmarciaisapp.database.dao;

import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.CityModel;

import java.util.ArrayList;
import java.util.List;

public class CityDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    CityModel.COLUNA_CIDADE,
                    CityModel.COLUNA_ESTADO,
                    CityModel.COLUNA_PAIS
            };

    public CityDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public List<CityModel> select() {

        List<CityModel> lo_arl_dados = new ArrayList<CityModel>();

        try {
            Open();

            Cursor cursor = db.query(CityModel.TABELA_NOME, colunas, null, null, null, null, null);
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


    public List<CityModel> getByState(String state) {

        List<CityModel> lo_arl_dados = new ArrayList<CityModel>();

        try {
            Open();

            Cursor cursor = db.query(CityModel.TABELA_NOME, colunas, "estado = " + "'" + state + "'", null, null, null, null);
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


    private CityModel CursorToStructure(final Cursor cursor) {
        CityModel model = new CityModel();
        try {
            model.setCidade(cursor.getString(0));
            model.setEstado(cursor.getString(1));
            model.setPais(cursor.getString(2));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
