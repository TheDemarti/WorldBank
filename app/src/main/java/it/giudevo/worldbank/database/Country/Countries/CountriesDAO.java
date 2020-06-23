package it.giudevo.worldbank.database.Country.Countries;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountriesDAO {
    @Query("SELECT * FROM Country")
    List<Country> getAll();

    @Query("SELECT * FROM Country WHERE id IN (:ids)")
    List<Country> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Country WHERE iso2Code LIKE :name ORDER BY iso2Code")
    List<Country> findByCountryName(String name);

    @Query("SELECT count(*) FROM Country")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Country... countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Country>countries);

    @Delete
    void delete(Country country);
}
