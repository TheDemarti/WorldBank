package it.giudevo.worldbank.database.Countries;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Countries.class, version = 1, exportSchema = false)
public abstract class AppCountriesDatabase extends RoomDatabase {
    public abstract CountriesDAO countriesDAO();
}
