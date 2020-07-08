package it.giudevo.worldbank.database.Final;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FinalDAO {
    @Query("SELECT count(*) FROM Final")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Final... countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Final>countries);

    @Delete
    void delete(Final aFinal);
}