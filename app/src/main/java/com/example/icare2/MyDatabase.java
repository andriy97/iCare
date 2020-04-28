package com.example.icare2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Report.class}, version = 1) //ci passo la classe report come entità
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
