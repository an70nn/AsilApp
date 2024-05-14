package com.example.asilapp.Models;

import java.io.Serializable;

public class Review implements Serializable {

    private int idReview;
    private int idUtente;
    private int idCentro;
    private int stelle;

    public Review(int idR, int idU, int idC, int stelle) {

        this.idReview = idR;
        this.idUtente = idU;
        this.idCentro = idC;
        this.stelle = stelle;
    }

    public Review() {

        this.idReview = 0;
        this.idUtente = 0;
        this.idCentro = 0;
        this.stelle = 0;
    }

    public int getIdReview() { return idReview; }
    public void setIdReview(int idR) { this.idReview = idR; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idU) { this.idUtente = idU; }

    public int getIdCentro() { return idCentro; }
    public void setIdCentro(int idC) { this.idCentro = idC; }

    public int getStelle() { return stelle; }
    public void setStelle(int stelle) { this.stelle = stelle; }
}
