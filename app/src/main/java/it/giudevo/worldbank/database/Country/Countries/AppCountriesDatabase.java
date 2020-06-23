package it.giudevo.worldbank.database.Country.Countries;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Country.class, version = 1)
public abstract class AppCountriesDatabase extends RoomDatabase {
    public abstract CountriesDAO countriesDAO();
}
