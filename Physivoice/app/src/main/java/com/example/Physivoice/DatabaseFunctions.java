package com.example.Physivoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFunctions extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "physivoice.db";

    public DatabaseFunctions(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        //table created whenever this method is called

        makeTable(db, "Pain_Entries", new String[]{"(ID INTEGER PRIMARY KEY AUTOINCREMENT, ","DATE TEXT, ","TIME TEXT, ","TRANSCRIPT TEXT)"});
        makeTable(db, "Medication_Entries", new String[]{"(ID INTEGER PRIMARY KEY AUTOINCREMENT, ","DATE TEXT, ","MEDICATION TEXT)"});
    }

    private void makeTable(SQLiteDatabase db,String table_name, String[] columns){

        String query = "create table if not exists " + table_name +" ";
        for (String i: columns){
            query += i;
        }
        db.execSQL(query);   //executes the query passed as an argument in the method
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Pain_Entries");
        db.execSQL("DROP TABLE IF EXISTS Medication_Entries");
        onCreate(db);
    }

    public boolean insertData(String table,String[] entries, String[] columns) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for ( int i=0; i< entries.length; i++){
            contentValues.put(columns[i], entries[i]);
        }

        long result = db.insert(table, null, contentValues); //returns -1 if not inserted

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getMyItems(String table,String[] columns,String filter,String value) {

        List<String> stringList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ";
        for (int i=0; i<columns.length; i++){
            query+=columns[i];
            if (i!=columns.length-1){
                query+=", ";
            }
        }
        query +=" FROM " + table + " WHERE "+ filter + "='" + value + "'";


        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                for (String column : columns) {
                    String name = (c.getString(c.getColumnIndex(column)));
                    stringList.add(name);


                }
                c.moveToNext();
            }
        }

        return stringList;
    }

}









