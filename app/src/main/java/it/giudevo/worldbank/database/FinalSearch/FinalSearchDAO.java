package it.giudevo.worldbank.database.Arguments.FinalSearch;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FinalSearchDAO {
    @Query("SELECT * FROM FinalSearch")
    List<FinalSearch> getAll();

    @Query("SELECT * FROM FinalSearch WHERE id IN (:ids)")
    List<FinalSearch> loadAllByIds(int[] ids);

    @Query("SELECT * FROM FinalSearch WHERE countryiso3code LIKE :name ORDER BY countryiso3code")
    List<FinalSearch> findByCountryName(String name);

    @Query("SELECT count(*) FROM FinalSearch")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FinalSearch... countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FinalSearch>countries);

    @Delete
    void delete(FinalSearch finalSearch);
}