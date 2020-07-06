package it.giudevo.worldbank.database.Indicators;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Indicators.class, version = 1, exportSchema = false)
public abstract class AppIndicatorsDatabase extends RoomDatabase {
    public abstract IndicatorsDAO indicatorsDAO();
}
