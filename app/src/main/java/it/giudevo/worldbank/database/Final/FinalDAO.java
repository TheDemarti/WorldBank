package it.giudevo.worldbank.database.Final;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FinalDAO {
    @Query("SELECT * FROM Final")
    List<Final> getAll();

    @Query("SELECT * FROM Final WHERE id IN (:ids)")
    List<Final> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Final WHERE countryiso3code LIKE :name ORDER BY countryiso3code")
    List<Final> findByCountryName(String name);

    @Query("SELECT count(*) FROM Final")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Final... countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Final>countries);

    @Delete
    void delete(Final aFinal);
}