package com.example.asilapp.Models;

import java.io.Serializable;

public class Strumento implements Serializable {

    private int idStrumento;
    private String nome;
    private String modello;
    private String tipoMisurazione;
    private int idLocker;

    public Strumento(int idS, String nome, String modello, String tipoM, int idL) {

        this.idStrumento = idS;
        this.nome = nome;
        this.modello = modello;
        this.tipoMisurazione = tipoM;
        this.idLocker = idL;
    }

    public Strumento() {

        this.idStrumento = 0;
        this.nome = "nome";
        this.modello = "modello";
        this.tipoMisurazione = "tipoM";
        this.idLocker = 0;
    }

    public int getIdStrumento() { return idStrumento; }
    public void setIdStrumento(int idS) { this.idStrumento = idS; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getModello() { return modello; }
    public void setModello(String modello) { this.modello = modello; }

    public String getTipoMisurazione() { return tipoMisurazione; }
    public void setTipoMisurazione(String tipoMisurazione) { this.tipoMisurazione = tipoMisurazione; }

    public int getIdLocker() { return idLocker; }
    public void setIdLocker(int idL) { this.idLocker = idL; }

    @Override
    public String toString() {
        return "Strumento{" +
                "id Strumento='" + idStrumento + '\'' +
                ", nome strumento='" + nome + '\'' +
                ", modello='" + modello + '\'' +
                ", tipo misurazione='" + tipoMisurazione + '\'' +
                ", id Locker='" + idLocker +
                '}';
    }
}
