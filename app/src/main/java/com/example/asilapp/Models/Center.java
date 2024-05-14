package com.example.asilapp.Models;

import java.io.Serializable;

public class Center implements Serializable {

    private String citta;
    private String nome;
    private String indirizzo;
    private int id;
    private String norma;
    private String regolamento;
    private String descrizione;
    private String telefono;
    private String immagine;

    public Center(String citta, String nome, String indirizzo, int id, String norma,
                  String regolamento, String descrizione, String telefono, String immagine) {
        this.citta = citta;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.id = id;
        this.norma = norma;
        this.regolamento = regolamento;
        this.descrizione = descrizione;
        this.telefono = telefono;
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

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

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

