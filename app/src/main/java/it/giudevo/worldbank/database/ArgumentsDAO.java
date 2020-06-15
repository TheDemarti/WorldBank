package it.giudevo.worldbank.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import javax.xml.transform.Templates;

//@Dao
//public interface ArgumentsDAO {
//    @Query("SELECT * FROM arguments")
//    List<Arguments> getAll();
//
//    @Query("SELECT * FROM Arguments WHERE id IN (:ids)")
//    List<Arguments> loadAllByIds(int[] ids);
//
//    @Query("SELECT * FROM arguments WHERE value LIKE :value ORDER BY value")
//    List<Arguments> findByCountryName(String value);
//
//    @Query("SELECT count(*) FROM arguments")
//    int size();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(Arguments... arguments);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(List<Arguments>arguments);
//
//    @Delete
//    void delete(Arguments arguments);
//}

@Dao
public interface ArgumentsDAO {
    @Insert
    void insert (Template template);
    @Transaction
    @Query("SELECT * FROM Templates")
    List<Template> getAll();

//    @Query("SELECT * FROM Arg.Templates " + " WHERE id IN (:ids)")
//    List<Arguments> loadAllByIds(int[] ids);

    @Query("SELECT * FROM " + " Templates WHERE value LIKE :value ORDER BY value")
    List<Template> findByCountryName(String value);

    @Query("SELECT count(*) FROM Templates")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Template... templates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Template>templates);

    @Delete
    void delete(Template templates);
}
