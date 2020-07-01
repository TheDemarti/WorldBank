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
    public static final String ID = "id";
    public static final String NAME = "name";
    //public static final String COL3 = "VALUE";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table TABLE_NAME " + "( id integer primary key, name text )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS  TABLE_NAME" );
        onCreate(db);
    }

    public boolean addData(List<Final> name) {


//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            for(int i = 0; i < name.size()-1; i++) {
//                values.put(String.valueOf(COL2), name.get(i).getDate());
//                values.put(String.valueOf(COL3), name.get(i).getValue());
//                Log.w("CA", String.valueOf(name.get(i).getDate()));
//                Log.w("CA", String.valueOf(name.get(i).getValue()));
//                db.insert(TABLE_NAME, null, values);
//            }
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
//    }


        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL1, name.get(0).countryiso3code);
        //for(int i = 0; i < name.size(); i++) {
            contentValues.put("id", 12);
            contentValues.put("name", String.valueOf(name.get(1).getDate()));
            Log.w("CA", "addData: Adding" + contentValues + "to " + TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();

        //}

        //Log.w("CA", "addData: Adding " + name + "to " + TABLE_NAME);
        //Log.w("CA", "addData: Adding" + contentValues + "to " + TABLE_NAME);

        db.insert("TABLE_NAME", null, contentValues);


        db.close();
        return true;
    }

    public Cursor getListContents(){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM TABLE_NAME ", null);
        return data;
    }

//    public Cursor getOneData(String id){
//        SQLiteDatabase db  = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT " + COL1 + " FROM " + TABLE_NAME, null);
//        return res;
//    }
}
