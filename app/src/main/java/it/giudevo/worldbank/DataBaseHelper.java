package it.giudevo.worldbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import it.giudevo.worldbank.searchApi.FinalSearch;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DatabaseHelper";
    private  static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL2 + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.w("CA", "addData: Adding" + item + "to" + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
