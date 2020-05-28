package com.unesc.artesmarciaisapp.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class GraduationModel implements Searchable {
    public static final String
            TABELA_NOME = "tb_graduacao";

    public static final String
            COLUNA_MODALIDADE = "modalidade",
            COLUNA_GRADUACAO = "graduacao";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_MODALIDADE + " text NOT NULL,"
                    + COLUNA_GRADUACAO + " text NOT NULL,"
                    + "PRIMARY KEY ('" + COLUNA_MODALIDADE + "','"+ COLUNA_GRADUACAO + "')"
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private String modalidade;
    private String graduacao;

    public GraduationModel(String modalidade, String graduacao) {
        this.modalidade = modalidade;
        this.graduacao = graduacao;
    }

    public GraduationModel() {
    }

    public static String getTabelaNome() {
        return TABELA_NOME;
    }

    public static String getColunaModalidade() {
        return COLUNA_MODALIDADE;
    }

    public static String getColunaGraduacao() {
        return COLUNA_GRADUACAO;
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

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    @Override
    public String getTitle() {
        return graduacao;
    }
}
