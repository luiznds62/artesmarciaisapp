package com.unesc.artesmarciaisapp.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class ModalityModel implements Searchable {
    public static final String
            TABELA_NOME = "tb_modalidade";

    public static final String
            COLUNA_MODALIDADE = "modalidade";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_MODALIDADE + " text NOT NULL,"
                    + "PRIMARY KEY ('" + COLUNA_MODALIDADE + "')"
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private String modalidade;

    public ModalityModel(String modalidade) {
        this.modalidade = modalidade;
    }

    public ModalityModel() {
    }

    public static String getTabelaNome() {
        return TABELA_NOME;
    }

    public static String getColunaModalidade() {
        return COLUNA_MODALIDADE;
    }

    public static String getCreateTable() {
        return CREATE_TABLE;
    }

    public static String getDropTable() {
        return DROP_TABLE;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    @Override
    public String getTitle() {
        return modalidade;
    }
}
