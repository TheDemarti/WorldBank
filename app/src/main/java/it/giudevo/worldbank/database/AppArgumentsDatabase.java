package it.giudevo.worldbank.database;

import androidx.room.Database;

@Database(entities = {Arguments.class}, version = 1)
public abstract class AppArgumentsDatabase {
    public abstract ArgumentsDAO cocktailDAO();
}
