package com.ashwin.globalalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GlobalAlarm.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "alarms";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ID = "_id";

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_TIME + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addAlarm (String Date, String Time, String Name) {
        long retVal = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rowData = new ContentValues();
        rowData.put (COLUMN_DATE, Date);
        rowData.put (COLUMN_TIME, Time);
        rowData.put (COLUMN_NAME, Name);
        retVal = db.insert(TABLE_NAME, null, rowData);
        return retVal;
    }

    public Cursor getAlarms () {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public void clearAlarms () {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}