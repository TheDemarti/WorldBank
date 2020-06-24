package it.giudevo.worldbank.database.FinalSearch;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = FinalSearch.class, version = 1)
public abstract class AppFinalSearchDatabase extends RoomDatabase{
    public abstract FinalSearchDAO countriesDAO();
}