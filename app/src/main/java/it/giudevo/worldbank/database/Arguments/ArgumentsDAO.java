package it.giudevo.worldbank.database.Arguments;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArgumentsDAO {
    @Query("SELECT count(*) FROM Arguments")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Arguments... arguments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Arguments>arguments);

    @Delete
    void delete(Arguments arguments);
}
