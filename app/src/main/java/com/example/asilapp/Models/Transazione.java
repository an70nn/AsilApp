package com.example.asilapp.Models;

import java.io.Serializable;
import java.util.Date;

public class Transazione implements Serializable {

    private int idUtente;
    private String nomeSpesa;
    private Date data;
    private String categoria;
    private String prezzo;

    public Transazione(int idU, String nomeSpesa, String categoria, String prezzo) {

        this.idUtente = idU;
        this.nomeSpesa = nomeSpesa;
        this.data = new Date();
        this.categoria = categoria;
        this.prezzo = prezzo;
    }

    public Transazione() {

        this.idUtente = 0;
        this.nomeSpesa = "nomeSpesa";
        this.data = null;
        this.categoria = "categoria";
        this.prezzo = "prezzo";
    }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idU) { this.idUtente = idU; }

    public String getNomeSpesa() { return nomeSpesa; }
    public void setNomeSpesa(String nomeSpesa) { this.nomeSpesa = nomeSpesa; }

    public Date getData() { return data; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getPrezzo() { return prezzo; }
    public void setPrezzo(String prezzo) { this.prezzo = prezzo; }

    @Override
    public String toString() {
        return "Transazione{" +
                "id Utente='" + idUtente + '\'' +
                ", nome spesa='" + nomeSpesa + '\'' +
                ", data='" + data + '\'' +
                ", prezzo='" + prezzo +
                '}';
    }
}
