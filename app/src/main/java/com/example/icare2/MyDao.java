package com.example.icare2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao { //metodi per manipolare il database

    //inserire report nel database
    @Insert
    public void addReport(Report report);

    //query per leggere il contenuto del database
    @Query("select * from Reports order by data DESC")
    public List<Report> getReports();

    //eliminare report dal database
    @Delete
    public void deleteReport(Report report);

    @Update
    public void updateReport(Report report);
}
