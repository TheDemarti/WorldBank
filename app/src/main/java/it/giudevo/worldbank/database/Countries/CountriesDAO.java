package it.giudevo.worldbank.database.Countries;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountriesDAO {
    @Query("SELECT * FROM Countries")
    List<Countries> getAll();

    @Query("SELECT * FROM Countries WHERE id IN (:ids)")
    List<Countries> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Countries WHERE countryiso3code LIKE :name ORDER BY countryiso3code")
    List<Countries> findByCountryName(String name);

    @Query("SELECT count(*) FROM Countries")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Countries... countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Countries>countries);

    @Delete
    void delete(Countries countries);
}