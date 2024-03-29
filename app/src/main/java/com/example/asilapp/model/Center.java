package com.example.asilapp.model;

import java.io.Serializable;

public class Center implements Serializable {

    private String citta;
    private String nome;
    private String locazione;
    private int id;
    private String norma;
    private String regolamento;

    public Center(String citta, String nome, String locazione, int id, String norma, String regolamento) {
        this.citta = citta;
        this.nome = nome;
        this.locazione = locazione;
        this.id = id;
        this.norma = norma;
        this.regolamento = regolamento;
    }

    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocazione() {
        return locazione;
    }
    public void setLocazione(String locazione) {
        this.locazione = locazione;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNorma() {
        return norma;
    }
    public void setNorma(String norma) {
        this.norma = norma;
    }

    public String getRegolamento() {
        return regolamento;
    }
    public void setRegolamento(String regolamento) {
        this.regolamento = regolamento;
    }

    @Override
    public String toString() {
        return "Center{" +
                "citta='" + citta + '\'' +
                ", nome='" + nome + '\'' +
                ", locazione='" + locazione + '\'' +
                ", id=" + id +
                ", norma='" + norma + '\'' +
                ", regolamento='" + regolamento + '\'' +
                '}';
    }
}

