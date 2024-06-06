package com.example.smapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context ) {
        super(context,"UserData",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table IF NOT EXISTS UserDetails(userID TEXT primary key,id TEXT," +
                "password PASSWORD, date text)");

        DB.execSQL("create Table IF NOT EXISTS consolidate(userID TEXT primary key,id TEXT, date text," +
                " sec text, sub text, subtype text, status text)");
    }

    public Boolean insetUserData(String id,String email,String password, String date){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("userID",email);
        contentValues.put("password",password);
        contentValues.put("date", date);
        long result= DB.insert("UserDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean consolidate(String id, String date, String sec, String sub, String subType, String status){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("date", date);
        contentValues.put("sec",sec);
        contentValues.put("sub",sub);
        contentValues.put("sub",subType);
        contentValues.put("date", status);
        long result= DB.insert("consolidate",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails ",null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
