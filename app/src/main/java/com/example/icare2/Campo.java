package com.example.icare2;

public class Campo {
    private String titolo;
    private double valore;

    public Campo(String titolo, double valore) {
        this.titolo = titolo;
        this.valore = valore;
    }

    public Campo(String titolo) {
        this.titolo = titolo;
        this.valore = valore;
    }

    public double getValore() {
        return valore;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
