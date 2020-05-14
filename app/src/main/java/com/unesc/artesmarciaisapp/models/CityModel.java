package com.unesc.artesmarciaisapp.models;

public class CityModel {
    public static final String
            TABELA_NOME = "tb_cidade";

    public static final String
            COLUNA_CIDADE = "cidade",
            COLUNA_ESTADO = "estado",
            COLUNA_PAIS = "pais";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_CIDADE + " text NOT NULL,"
                    + COLUNA_ESTADO + " text NOT NULL,"
                    + COLUNA_PAIS + " text NOT NULL,"
                    + "PRIMARY KEY ('cidade','estado','pais')"
                    + ");";
    public static final String[] INSERT_CITIES = new String[]{
            "insert into " + TABELA_NOME + "(" + COLUNA_CIDADE + "," + COLUNA_ESTADO + "," + COLUNA_PAIS + ") " + "values ('Criciúma','Santa Catarina','Brasil')",
            "insert into " + TABELA_NOME + "(" + COLUNA_CIDADE + "," + COLUNA_ESTADO + "," + COLUNA_PAIS + ") " + "values ('Içara','Santa Catarina','Brasil')",
            "insert into " + TABELA_NOME + "(" + COLUNA_CIDADE + "," + COLUNA_ESTADO + "," + COLUNA_PAIS + ") " + "values ('Tubarão','Santa Catarina','Brasil')",
            "insert into " + TABELA_NOME + "(" + COLUNA_CIDADE + "," + COLUNA_ESTADO + "," + COLUNA_PAIS + ") " + "values ('Paranavaí','Paraná','Brasil')",
            "insert into " + TABELA_NOME + "(" + COLUNA_CIDADE + "," + COLUNA_ESTADO + "," + COLUNA_PAIS + ") " + "values ('Torres','Rio Grande do Sul','Brasil')"
    };
    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private String cidade;
    private String estado;
    private String pais;

    public CityModel(String cidade, String estado, String pais) {
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public CityModel() {
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
