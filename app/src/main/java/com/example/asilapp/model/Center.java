package com.example.asilapp.model;

import java.io.Serializable;

public class Center implements Serializable {

    private String citta;
    private String nome;
    private String indirizzo;
    private int id;
    private String norma;
    private String regolamento;
    private String immagine;

    public Center(String citta, String nome, String indirizzo, int id, String norma, String regolamento, String immagine) {
        this.citta = citta;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.id = id;
        this.norma = norma;
        this.regolamento = regolamento;
        this.immagine = immagine;
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

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
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

    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }

    @Override
    public String toString() {
        return "Center{" +
                "citta='" + citta + '\'' +
                ", nome='" + nome + '\'' +
                ", locazione='" + indirizzo + '\'' +
                ", id=" + id +
                ", norma='" + norma + '\'' +
                ", regolamento='" + regolamento + '\'' +
                '}';
    }
}

