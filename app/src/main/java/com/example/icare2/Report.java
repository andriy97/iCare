package com.example.icare2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//FUNGE DA TABELLA
@Entity(tableName = "Reports")
public class Report {

    //Colonne
    @PrimaryKey
    private int id;
    @ColumnInfo
    private int anno;
    @ColumnInfo
    private int mese;
    @ColumnInfo
    private int giorno;
    @ColumnInfo
    private double temperatura;
    @ColumnInfo
    private double pressione;
    @ColumnInfo
    private double peso;


    //tutti i getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPressione() {
        return pressione;
    }

    public void setPressione(double pressione) {
        this.pressione = pressione;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


}

