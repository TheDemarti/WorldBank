package it.giudevo.worldbank.database.Arguments;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Arguments.class, version = 1)
public abstract class AppArgumentsDatabase extends RoomDatabase{
    public abstract ArgumentsDAO argumentsDAO();
}
