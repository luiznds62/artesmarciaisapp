package com.unesc.artesmarciaisapp.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class StudentModel implements Searchable {

    public static final String
            TABELA_NOME = "tb_aluno";

    public static final String
            COLUNA_CODIGO_ALUNO = "codigo_aluno",
            COLUNA_ALUNO = "aluno",
            COLUNA_DATA_NASCIMENTO = "data_nascimento",
            COLUNA_SEXO = "sexo",
            COLUNA_TELEFONE = "telefone",
            COLUNA_CELULAR = "celular",
            COLUNA_EMAIL = "email",
            COLUNA_OBSERVACAO = "observacao",
            COLUNA_ENDERECO = "endereco",
            COLUNA_NUMERO = "numero",
            COLUNA_COMPLEMENTO = "complemento",
            COLUNA_BAIRRO = "bairro",
            COLUNA_CIDADE = "cidade",
            COLUNA_ESTADO = "estado",
            COLUNA_PAIS = "pais",
            COLUNA_CEP = "cep",
            COLUNA_CREATED_DATE = "createddate";

    public static final String
            CREATE_TABLE =
            "create table " + TABELA_NOME
                    + "("
                    + COLUNA_CODIGO_ALUNO + " integer primary key autoincrement, "
                    + COLUNA_ALUNO + " text not null, "
                    + COLUNA_DATA_NASCIMENTO + " text not null, "
                    + COLUNA_SEXO + " text DEFAULT NULL, "
                    + COLUNA_TELEFONE + " text DEFAULT NULL, "
                    + COLUNA_CELULAR + " text DEFAULT NULL, "
                    + COLUNA_EMAIL + " text DEFAULT NULL, "
                    + COLUNA_OBSERVACAO + " text DEFAULT NULL, "
                    + COLUNA_ENDERECO + " text DEFAULT NULL, "
                    + COLUNA_NUMERO + " text DEFAULT NULL, "
                    + COLUNA_COMPLEMENTO + " text DEFAULT NULL, "
                    + COLUNA_BAIRRO + " text DEFAULT NULL, "
                    + COLUNA_CIDADE + " text DEFAULT NULL, "
                    + COLUNA_ESTADO + " text DEFAULT NULL, "
                    + COLUNA_PAIS + " text DEFAULT NULL, "
                    + COLUNA_CEP + " text DEFAULT NULL, "
                    + COLUNA_CREATED_DATE + " text DEFAULT NULL "
                    + ");";

    public static final String DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private int codigo_aluno;
    private String aluno;
    private String data_nascimento;
    private String sexo;
    private String telefone;
    private String celular;
    private String email;
    private String observacao;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String createdDate;

    public StudentModel(int codigo_aluno, String aluno, String data_nascimento, String sexo, String telefone, String celular, String email, String observacao, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String cep) {
        this.codigo_aluno = codigo_aluno;
        this.aluno = aluno;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.observacao = observacao;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }

    public StudentModel(String aluno, String data_nascimento, String sexo, String telefone, String celular, String email, String observacao, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String cep) {
        this.aluno = aluno;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.observacao = observacao;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }

    public StudentModel() {
    }

    public int getCodigo_aluno() {
        return codigo_aluno;
    }

    public void setCodigo_aluno(int codigo_aluno) {
        this.codigo_aluno = codigo_aluno;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getTitle() {
        return aluno;
    }
}
