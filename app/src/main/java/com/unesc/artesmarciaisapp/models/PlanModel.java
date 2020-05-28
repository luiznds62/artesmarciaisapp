package com.unesc.artesmarciaisapp.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class PlanModel implements Searchable {
    public static final String
            TABELA_NOME = "tb_plano";

    public static final String
            COLUNA_MODALIDADE = "modalidade",
            COLUNA_PLANO = "plano",
            COLUNA_VALOR_MENSAL = "valor_mensal";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_MODALIDADE + " text NOT NULL,"
                    + COLUNA_PLANO + " text NOT NULL,"
                    + COLUNA_VALOR_MENSAL + " real NOT NULL,"
                    + "PRIMARY KEY ('" + COLUNA_MODALIDADE + "')"
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private String modalidade;
    private String plano;
    private Double valor_mensal;

    public PlanModel(String modalidade, String plano, Double valor_mensal) {
        this.modalidade = modalidade;
        this.plano = plano;
        this.valor_mensal = valor_mensal;
    }

    public PlanModel() {
    }

    public static String getTabelaNome() {
        return TABELA_NOME;
    }

    public static String getColunaModalidade() {
        return COLUNA_MODALIDADE;
    }

    public static String getColunaPlano() {
        return COLUNA_PLANO;
    }

    public static String getColunaValorMensal() {
        return COLUNA_VALOR_MENSAL;
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

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public Double getValor_mensal() {
        return valor_mensal;
    }

    public void setValor_mensal(Double valor_mensal) {
        this.valor_mensal = valor_mensal;
    }

    @Override
    public String getTitle() {
        return plano;
    }
}
