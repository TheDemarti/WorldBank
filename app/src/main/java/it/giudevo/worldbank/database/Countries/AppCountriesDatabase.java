package it.giudevo.worldbank.database.Countries;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import it.giudevo.worldbank.database.Countries.Countries;
import it.giudevo.worldbank.database.Countries.CountriesDAO;

@Database(entities = Countries.class, version = 1)
public abstract class AppCountriesDatabase extends RoomDatabase{
    public abstract CountriesDAO countriesDAO();
}