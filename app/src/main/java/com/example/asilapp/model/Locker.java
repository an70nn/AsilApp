package com.example.asilapp.model;

import java.io.Serializable;

public class Locker implements Serializable {

   private int id;
   private String nome;
   private String locazione;

   public Locker(int id, String nome, String locazione) {

       this.id = id;
       this.nome = nome;
       this.locazione = locazione;
   }

   public Locker() {

       this.id = 0;
       this.nome = "nome";
       this.locazione = "locazione";
   }

   public int getId() { return id; }
   public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLocazione() { return locazione; }
    public void setLocazione(String locazione) { this.locazione = locazione; }

    @Override
    public String toString() {
        return "Locker{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", locazione='" + locazione +
               '}';
    }
}
