package com.example.physivoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFunctions extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diary.db";
    public static final String TABLE_NAME = "diary_entries";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "TRANSCRIPT";

    public DatabaseFunctions(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        //table created whenever this method is called
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, TIME TEXT, TRANSCRIPT TEXT)");   //executes the query passed as an argument in the method
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String time, String entry) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, date);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, entry);

        long result = db.insert(TABLE_NAME, null, contentValues); //returns -1 if not inserted

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getMyItems(String date) {

        List<String> stringList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT TRANSCRIPT FROM " + TABLE_NAME + " WHERE DATE='" + date + "'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            while (c.isAfterLast() == false) {

                String name = (c.getString(c.getColumnIndex("TRANSCRIPT")));
                stringList.add(name);
                c.moveToNext();

            }
        }

        return stringList;
    }

}









