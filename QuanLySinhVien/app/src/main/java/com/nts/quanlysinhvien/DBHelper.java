package com.nts.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TEN_DATABASE = "QuanLySinhVien";
    public static final String TEN_BANG_SINHVIEN = "SinhVien";
    public static final String COT_ID = "_id";
    public static final String COT_TEN = "_ten";
    public static final String COT_LOP = "_lop";

    private static final String TAO_BANG_SINHVIEN = ""
            + "create table " + TEN_BANG_SINHVIEN + " ( "
            + COT_ID + " integer primary key autoincrement, "
            + COT_TEN + " text not null, "
            + COT_LOP + " text not null );";

    public DBHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(TAO_BANG_SINHVIEN);

        // Insert data using ContentValues
        insertStudent(db, "Nguyen Van A", "K12");
        insertStudent(db, "Le Thi B", "K12");
        insertStudent(db, "Tran Van C", "K12");
    }

    private void insertStudent(SQLiteDatabase db, String name, String clazz) {
        ContentValues values = new ContentValues();
        values.put(COT_TEN, name);
        values.put(COT_LOP, clazz);
        db.insert(TEN_BANG_SINHVIEN, null, values);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_SINHVIEN);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEN_BANG_SINHVIEN);
        onCreate(db);
    }
}
