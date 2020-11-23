package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import androidx.annotation.Nullable;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="student_db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_STUDENTS="students";
    private static final String KEY_ID="id";
    private static final String KEY_NAMA="nama";
    private static final String KEY_ALAMAT="alamat";
    private static final String CREATE_TABLE_STUDENTS=
            "CREATE TABLE "+TABLE_STUDENTS+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +KEY_NAMA+" TEXT,"
            +KEY_ALAMAT+" TEXT)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '"+TABLE_STUDENTS+"'");
        onCreate(sqLiteDatabase);
    }

    public long addStudent(String nama, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA, nama);
        values.put(KEY_ALAMAT, alamat);
        long insert = db.insert(TABLE_STUDENTS, null, values);
        return insert;
    }

    public ArrayList<Map<String,String>> getAllStudents(){
        ArrayList<Map<String,String>> arrayList = new ArrayList<>();
        String nama = "";
        String alamat = "";
        int id = 0;
        String selectQuery = "SELECT * FROM "+TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                id=c.getInt(c.getColumnIndex(KEY_ID));
                nama=c.getString(c.getColumnIndex(KEY_NAMA));
                alamat=c.getString(c.getColumnIndex(KEY_ALAMAT));
                Map<String ,String > itemMap= new HashMap<>();
                itemMap.put(KEY_ID,id+"");
                itemMap.put(KEY_NAMA, nama);
                itemMap.put(KEY_ALAMAT,alamat);
                arrayList.add(itemMap);
            } while (c.moveToNext());
        }
        return arrayList;
    }

    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteQuery = "DELETE FROM "+TABLE_STUDENTS+" WHERE "+KEY_ID+"='"+id+"'";
        db.execSQL(deleteQuery);
        db.close();
    }
}
