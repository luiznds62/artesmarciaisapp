package com.unesc.artesmarciaisapp.models;

import java.util.Date;

public class FaturaMatriculaModel {

    public static final String
            TABELA_NOME = "tb_fatura_matricula";

    public static final String
            COLUNA_CODIGO_MATRICULA = "codigo_matricula",
            COLUNA_DATA_VENCIMENTO = "data_vencimento",
            COLUNA_VALOR = "valor",
            COLUNA_DATA_PAGAMENTO = "data_pagamento",
            COLUNA_DATA_CANCELAMENTO = "data_cancelamento";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_CODIGO_MATRICULA + " integer NOT NULL, "
                    + COLUNA_DATA_VENCIMENTO + " text NOT NULL, "
                    + COLUNA_VALOR + " real NOT NULL,"
                    + COLUNA_DATA_PAGAMENTO + " text DEFAULT NULL,"
                    + COLUNA_DATA_CANCELAMENTO + " text DEFAULT NULL,"
                    + "PRIMARY KEY ('data_vencimento','codigo_matricula')"
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private int codigo_matricula;
    private Date data_vencimento;
    private Double valor;
    private Date data_pagamento;
    private Date data_cancelamento;

    public FaturaMatriculaModel(){

    }

    public FaturaMatriculaModel(int codigo_matricula, Date data_vencimento, Double valor, Date data_pagamento, Date data_cancelamento) {
        this.codigo_matricula = codigo_matricula;
        this.data_vencimento = data_vencimento;
        this.valor = valor;
        this.data_pagamento = data_pagamento;
        this.data_cancelamento = data_cancelamento;
    }

    public int getCodigo_matricula() {
        return codigo_matricula;
    }

    public void setCodigo_matricula(int codigo_matricula) {
        this.codigo_matricula = codigo_matricula;
    }

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Date getData_cancelamento() {
        return data_cancelamento;
    }

    public void setData_cancelamento(Date data_cancelamento) {
        this.data_cancelamento = data_cancelamento;
    }
}

