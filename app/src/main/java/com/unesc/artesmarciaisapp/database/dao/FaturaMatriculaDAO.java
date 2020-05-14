package com.unesc.artesmarciaisapp.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;
import com.unesc.artesmarciaisapp.models.FaturaMatriculaModel;
import com.unesc.artesmarciaisapp.services.DateService;

import java.util.ArrayList;
import java.util.List;

public class FaturaMatriculaDAO extends AbstractDAO {

    private final String[]
            colunas =
            {
                    FaturaMatriculaModel.COLUNA_CODIGO_MATRICULA,
                    FaturaMatriculaModel.COLUNA_DATA_CANCELAMENTO,
                    FaturaMatriculaModel.COLUNA_DATA_PAGAMENTO,
                    FaturaMatriculaModel.COLUNA_DATA_VENCIMENTO,
                    FaturaMatriculaModel.COLUNA_VALOR
            };

    public FaturaMatriculaDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(final FaturaMatriculaModel model) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(FaturaMatriculaModel.COLUNA_DATA_CANCELAMENTO, model.getData_cancelamento().toString());
            values.put(FaturaMatriculaModel.COLUNA_DATA_PAGAMENTO, model.getData_pagamento().toString());
            values.put(FaturaMatriculaModel.COLUNA_DATA_VENCIMENTO, model.getData_vencimento().toString());
            values.put(FaturaMatriculaModel.COLUNA_VALOR, model.getValor());

            rowAffect = db.insert(FaturaMatriculaModel.TABELA_NOME, null, values);
            System.out.println("Insert: " + rowAffect);
        } finally {
            Close();
        }

        return rowAffect;
    }

    public long update(final String dataVencimento, String dataPagamento, String dataCancelamento, Double valor, final String whereCodigoFatura) {

        long rowAffect = 0;

        try {

            Open();

            ContentValues values = new ContentValues();
            values.put(FaturaMatriculaModel.COLUNA_DATA_VENCIMENTO, dataVencimento);
            values.put(FaturaMatriculaModel.COLUNA_DATA_PAGAMENTO, dataPagamento);
            values.put(FaturaMatriculaModel.COLUNA_DATA_CANCELAMENTO, dataCancelamento);
            values.put(FaturaMatriculaModel.COLUNA_VALOR, valor);


            rowAffect = db.update(FaturaMatriculaModel.TABELA_NOME, values, FaturaMatriculaModel.COLUNA_CODIGO_MATRICULA + "= ?", new String[]{whereCodigoFatura});
        } finally {
            Close();
        }

        return rowAffect;
    }

    public List<FaturaMatriculaModel> select() {

        List<FaturaMatriculaModel> lo_arl_dados = new ArrayList<FaturaMatriculaModel>();

        try {
            Open();

            Cursor cursor = db.query(FaturaMatriculaModel.TABELA_NOME, colunas, null, null, null, null, null);
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

    public void delete(final String codigoFatura) {
        Open();
        db.delete(FaturaMatriculaModel.TABELA_NOME,
                FaturaMatriculaModel.COLUNA_CODIGO_MATRICULA + "= ?",
                new String[]{codigoFatura});
        Close();
    }

    private FaturaMatriculaModel CursorToStructure(final Cursor cursor) {
        FaturaMatriculaModel model = new FaturaMatriculaModel();
        try {
            model.setCodigo_matricula(cursor.getInt(0));
            model.setData_cancelamento(DateService.stringToDate(cursor.getString(1)));
            model.setData_pagamento(DateService.stringToDate(cursor.getString(2)));
            model.setData_vencimento(DateService.stringToDate(cursor.getString(3)));
            model.setValor(cursor.getDouble(4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }
}
