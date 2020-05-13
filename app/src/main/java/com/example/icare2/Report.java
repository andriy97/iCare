package com.example.icare2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

//FUNGE DA TABELLA
@Entity(tableName = "Reports")
public class Report {

    //Colonne
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo
    private String data;
    @ColumnInfo
    private Double temperatura;
    @ColumnInfo
    private Double frequenza;
    @ColumnInfo
    private Double peso;
    @ColumnInfo
    private int numero;


    //tutti i getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getFrequenza() {
        return frequenza;
    }

    public void setFrequenza(Double frequenza) {
        this.frequenza = frequenza;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }


}

