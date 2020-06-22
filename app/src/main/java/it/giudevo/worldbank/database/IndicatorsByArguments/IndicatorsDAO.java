package it.giudevo.worldbank.database.IndicatorsByArguments;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IndicatorsDAO {
    @Query("SELECT * FROM Indicators")
    List<Indicators> getAll();

    @Query("SELECT * FROM Indicators WHERE id IN (:ids)")
    List<Indicators> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Indicators WHERE name LIKE :value ORDER BY name")
    List<Indicators> findByIndicators(String value);

    @Query("SELECT count(*) FROM Indicators")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Indicators... indicators);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Indicators>indicators);

    @Delete
    void delete(Indicators indicators);
}
