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
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {                                                                           //crea il database e le sue  due tabelle
        String CREATE_TABLE = "create table TABLE_ID " + "( id string primary key, name text )";
        String CREATE_TABLE_DEATAILS = "create table TABLE_VALUE" + "( id string primary key, string text, date text, value text )";
        db.execSQL(CREATE_TABLE_DEATAILS);
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {                                        //aggiorna i valori del db
        db.execSQL(" DROP TABLE IF EXISTS  TABLE_ID" );
        db.execSQL(" DROP TABLE IF EXISTS TABLE_VALUE");
        onCreate(db);

    }

    public boolean addData(String string) {                                                         //aggiunge il dato (id) alla prima tabella
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", string);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("TABLE_ID", null, contentValues);
        db.close();
        return true;
    }

    public void addDataDetails(List<Final> name, String string) {                                   // dato un id , aggiunge tutti i singoli dati (data e valore) alla seconda tabella
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i < name.size(); i++) {
            ContentValues content = new ContentValues();
            content.put("string", string);
            content.put("date", String.valueOf(name.get(i).getDate()));
            content.put("value", String.valueOf(name.get(i).getValue()));
            db.insert("TABLE_VALUE", null, content);
        }
        db.close();
    }


    public Cursor getListContents(){                                                                // fa una query al database e ritorna l'intero contenuto della prima tabella,
        SQLiteDatabase db  = this.getWritableDatabase();                                            //ovvero tutti gli id dei dati salvati nel database
        return db.rawQuery("SELECT * FROM TABLE_ID ", null);
    }

    public Cursor getOneData(String id){                                                            //fa una query al database e ritorna i valori (data e valore) delle righe della seconda tabella
        SQLiteDatabase db  = this.getWritableDatabase();                                            //che hanno un determinato id
        String query = "SELECT * FROM TABLE_VALUE where string =" + "'" + id + "'";
        return db.rawQuery( query , null);
    }

    public Cursor getData(String id){                                                               //fa una query al database e ritorna il valore (id) delle righe della prima tabella
        SQLiteDatabase db  = this.getWritableDatabase();                                            //che hanno un determinato id
        String query = "SELECT * FROM TABLE_ID where name =" + "'" + id + "'";
        return db.rawQuery( query , null);
    }

    public void deletedata (String id){                                                            //data una stringa id , la funzione  elimina da entrambe le tabelle del db tutti i dati releativi ad essa
        Log.w("string",id);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM TABLE_ID WHERE name =" + "'"+ id +"'";
        String query1 = " DELETE FROM TABLE_VALUE WHERE string =" + "'"+ id +"'";
        db.execSQL(query);
        db.execSQL(query1);
        Log.w("delete","deleted");
        db.close();


    }
}
