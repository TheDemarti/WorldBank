package it.giudevo.worldbank.database.Arguments.Indicators;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Indicators.class, version = 1)
public abstract class AppIndicatorsDatabase extends RoomDatabase {
    public abstract IndicatorsDAO indicatorsDAO();
}