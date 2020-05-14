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

    //query ritorna tutti i report (o la media del giorno) dal piu recente
    @Query("select id, data, AVG(temperatura) as temperatura, AVG(frequenza) as frequenza, AVG(peso) as peso, COUNT(*) as numero from Reports group by data order by data DESC")
    public List<Report> getReportsDesc();
    //query ritorna tutti i report (o la media del giorno) dal meno recente
    @Query("select id, data, AVG(temperatura) as temperatura, AVG(frequenza) as frequenza, AVG(peso) as peso, COUNT(*) as numero from Reports group by data order by data ASC")
    public List<Report> getReportsAsc();
    //ritorna i report di un certo giorno
    @Query("select * from Reports WHERE data=:date order by data ASC")
    public List<Report> getDayReports(String date);


    //eliminare report dal database
    @Delete
    public void deleteReport(Report report);

    @Query("DELETE FROM Reports WHERE data= :date")
    public void deleteReportsByDate(String date);

    @Update
    public void updateReport(Report report);
}
