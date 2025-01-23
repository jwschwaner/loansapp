package com.example.loansapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "loans_db";
    public static final int DATABASE_VERSION = 1;

    // Tabelnavn og kolonner
    public static final String TABLE_LOANS = "loans";
    public static final String COL_ID = "id";
    public static final String COL_BRAND = "brand";
    public static final String COL_CABLE = "cable";
    public static final String COL_NAME = "name";
    public static final String COL_CONTACT = "contact";
    public static final String COL_TIME = "time";
    public static final String COL_RETURNED = "returned"; // 0 = false, 1 = true

    private static final String CREATE_TABLE_LOANS =
            "CREATE TABLE " + TABLE_LOANS + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_BRAND + " TEXT NOT NULL, "
                    + COL_CABLE + " TEXT, "
                    + COL_NAME + " TEXT NOT NULL, "
                    + COL_CONTACT + " TEXT, "
                    + COL_TIME + " TEXT, "
                    + COL_RETURNED + " INTEGER DEFAULT 0"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOANS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simpel strategi: drop tabellen og opret igen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANS);
        onCreate(db);
    }
}
