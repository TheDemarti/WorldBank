package it.giudevo.worldbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import it.giudevo.worldbank.database.Final.Final;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DatabaseHelper.db";
    public  static final String TABLE_NAME = "people_table";
    public  static final String TABLE_NAME_DETAILS = "people_table_details";
    public static String ID = "id";
    public static final String NAME = "name";
    public static final String COL3 = "VALUE";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table TABLE_NAME " + "( id string primary key, name text )";
        String CREATE_TABLE_DEATAILS = "create table TABLE_NAME_DETAILS" + "( id string primary key, string text, date text, value text )";
        db.execSQL(CREATE_TABLE_DEATAILS);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS  TABLE_NAME" );
        db.execSQL(" DROP TABLE IF EXISTS TABLE_NAME_DETAILS");
        onCreate(db);
    }

    public boolean addData(List<Final> name, String string) {

            Log.w("CA", string);
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", string);
            Log.w("CA", "addData: Adding" + contentValues + "to " + TABLE_NAME);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("TABLE_NAME", null, contentValues);
            db.close();

        return true;
    }

    public boolean addDataDetails(List<Final> name, String string) {
        SQLiteDatabase dbDet = this.getWritableDatabase();
        Log.w("CA", string);

        for(int i = 0; i < name.size(); i++) {
            ContentValues content = new ContentValues();
            content.put("string", string);
            Log.w("string data details", "string ======================" + string);
            content.put("date", String.valueOf(name.get(i).getDate()));
            content.put("value", String.valueOf(name.get(i).getValue()));
            dbDet.insert("TABLE_NAME_DETAILS", null, content);
        }
        dbDet.close();

        return true;
    }


    public int Delete(String string) {
        SQLiteDatabase dbDel = this.getWritableDatabase();
        Log.w("CA", string);

        return dbDel.delete(TABLE_NAME, NAME + "=" + string, null);

        //return true;
    }



    public Cursor getListContents(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM TABLE_NAME ", null);
        return data;
    }

    public Cursor getOneData(String id){
        SQLiteDatabase dbDet  = this.getWritableDatabase();
        String query = "SELECT * FROM TABLE_NAME_DETAILS where string =" + "'" + id + "'";
        Cursor res = dbDet.rawQuery( query , null);
        return res;
    }

    public Cursor getData(String id){
        SQLiteDatabase dbDet  = this.getWritableDatabase();
        String query = "SELECT * FROM TABLE_NAME where name =" + "'" + id + "'";
        Cursor res = dbDet.rawQuery( query , null);
        return res;
    }

    public void deletedata (String id){
        Log.w("string",id);
        SQLiteDatabase dbDet = this.getWritableDatabase();
        String query = " DELETE FROM TABLE_NAME WHERE name =" + "'"+ id +"'";
        String query1 = " DELETE FROM TABLE_NAME_DETAILS WHERE string =" + "'"+ id +"'";
        dbDet.execSQL(query);
        dbDet.execSQL(query1);
        Log.w("delete","deleted");


    }
}
