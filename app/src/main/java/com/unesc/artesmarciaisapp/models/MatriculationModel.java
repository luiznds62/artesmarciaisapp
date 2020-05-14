package com.unesc.artesmarciaisapp.models;

public class MatriculationModel {
    public static final String
            TABELA_NOME = "tb_matricula";

    public static final String
            COLUNA_CODIGO_MATRICULA = "codigo_matricula",
            COLUNA_ALUNO = "codigo_aluno",
            COLUNA_DATA_MATRICULA = "data_matricula",
            COLUNA_DIA_VENCIMENTO = "dia_vencimento",
            COLUNA_DATA_ENCERRAMENTO = "data_encerramento";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_CODIGO_MATRICULA + " integer primary key autoincrement, "
                    + COLUNA_ALUNO + " integer not null, "
                    + COLUNA_DATA_MATRICULA + " text not null, "
                    + COLUNA_DIA_VENCIMENTO + " text not null, "
                    + COLUNA_DATA_ENCERRAMENTO + " text DEFAULT NULL"
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private int codigo_matricula;
    private int codigo_aluno;
    private String data_matricula;
    private String dia_vencimento;
    private String data_encerramento;

    public MatriculationModel() {
    }

    public MatriculationModel(int codigo_matricula, int codigo_aluno, String data_matricula, String data_vencimento) {
        this.codigo_matricula = codigo_matricula;
        this.codigo_aluno = codigo_aluno;
        this.data_matricula = data_matricula;
        this.dia_vencimento = data_vencimento;
    }

    public int getCodigo_matricula() {
        return codigo_matricula;
    }

    public void setCodigo_matricula(int codigo_matricula) {
        this.codigo_matricula = codigo_matricula;
    }

    public int getCodigo_aluno() {
        return codigo_aluno;
    }

    public void setCodigo_aluno(int codigo_aluno) {
        this.codigo_aluno = codigo_aluno;
    }

    public String getData_matricula() {
        return data_matricula;
    }

    public void setData_matricula(String data_matricula) {
        this.data_matricula = data_matricula;
    }

    public String getDia_vencimento() {
        return dia_vencimento;
    }

    public void setDia_vencimento(String dia_vencimento) {
        this.dia_vencimento = dia_vencimento;
    }

    public String getData_encerramento() {
        return data_encerramento;
    }

    public void setData_encerramento(String data_encerramento) {
        this.data_encerramento = data_encerramento;
    }
}
