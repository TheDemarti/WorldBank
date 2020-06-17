package it.giudevo.worldbank.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArgumentsDAO {
    @Query("SELECT * FROM Arguments")
    List<Arguments> getAll();

    @Query("SELECT * FROM Arguments WHERE id IN (:ids)")
    List<Arguments> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Arguments WHERE value LIKE :value ORDER BY value")
    List<Arguments> findByCountryName(String value);

    @Query("SELECT count(*) FROM Arguments")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Arguments... arguments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Arguments>arguments);

    @Delete
    void delete(Arguments arguments);
}
