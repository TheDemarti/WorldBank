package it.giudevo.worldbank.database.Final;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Final.class, version = 1, exportSchema = false)
public abstract class AppFinalDatabase extends RoomDatabase{
    public abstract FinalDAO FinalDAO();
}