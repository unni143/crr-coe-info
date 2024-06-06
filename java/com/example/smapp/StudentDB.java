package com.example.smapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StudentDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "absentManager";
    private static final int DATABASE_VERSION = 1;

    private static final String ROLL = "roll";
    private static final String SEC = "sec";
    private static final String SUB = "sub";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String TOTAL = "total";
    private static final String TABLE = "student";

    public StudentDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE + "("
                + "id" + " INTEGER PRIMARY KEY," + ROLL + " TEXT,"
                + SEC + " TEXT," + SUB + " TEXT," + DATE + " TEXT," + STATUS + " TEXT," + TOTAL + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    void addData(ToDb toDb) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROLL, toDb.getRoll());
        values.put(SEC, toDb.getSec());
        values.put(SUB, toDb.getSub());
        values.put(DATE, toDb.getDate());
        values.put(STATUS, toDb.getSec());
        values.put(TOTAL, toDb.getTotal());

        // Inserting Row
        db.insert(TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
}
