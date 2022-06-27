package com.drogoncodes.finalpracticeapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "doctorDB", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table doctor(doctorId Integer primary key autoincrement, doctorName text, doctorEmail text, doctorContact text, doctorPassword text)");
        sqLiteDatabase.execSQL("create table patient(patientId Integer primary key autoincrement, patientName text, patientIllness text, patientContact text, patientGender text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void Register(String name, String email, String contact, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("doctorName", name);
        values.put("doctorEmail", email);
        values.put("doctorContact", contact);
        values.put("doctorPassword", password);
        db.insert("doctor", null, values);
    }

    public boolean Login(String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from doctor where doctorEmail = ? and doctorPassword = ?", new String[]{userName, password});
        if(res.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public void AddPatient(String name, String illness, String contact, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("patientName", name);
        values.put("patientIllness", illness);
        values.put("patientContact", contact);
        values.put("patientGender", gender);
        db.insert("patient", null, values);
    }

    public Cursor ViewPatients(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from patient", null);
        return res;
    }

    public Cursor GetPatient(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from patient where patientName=?",new String[]{name});
        return res;
    }

    public void UpdatePatient(Integer id, String name, String illness, String contact, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("patientName", name);
        values.put("patientIllness", illness);
        values.put("patientContact", contact);
        values.put("patientGender", gender);
        db.update("patient",values,"patientId=?",new String[]{id.toString()});
    }

    public void DeletePatient(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("patient","patientName=?", new String[]{name});
        db.close();
    }
}
