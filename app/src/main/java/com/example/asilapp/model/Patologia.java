package com.example.asilapp.model;

import java.io.Serializable;

public class Patologia implements Serializable {

    private int idUtente;
    private String nome;
    private int idMedico;

    public Patologia(int idU, String nome, int idM) {

        this.idUtente = idU;
        this.nome = nome;
        this.idMedico = idM;
    }

    public Patologia() {

        this.idUtente = 0;
        this.nome = "nome";
        this.idMedico = 0;
    }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idU) { this.idUtente = idU; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idM) { this.idMedico = idM; }

    @Override
    public String toString() {
        return "Patologia{" +
                "id Utente='" + idUtente + '\'' +
                ", nome='" + nome + '\'' +
                ", id Medico='" + idMedico +
                '}';
    }
}
