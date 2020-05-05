package com.example.icare2;

public class Campo {
    private String titolo;
    private double valore;


    private String valoredata;

    public Campo(String titolo, double valore) {
        this.titolo = titolo;
        this.valore = valore;
    }

    public Campo(String titolo) {
        this.titolo = titolo;
    }

    public String getValoredata() {
        return valoredata;
    }

    public void setValoredata(String valoredata) {
        this.valoredata = valoredata;
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
