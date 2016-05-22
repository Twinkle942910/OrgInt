package com.twinkle.orgint.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns
{
    // database name
    private static final String DATABASE_NAME = "organizer.db";
    // database version
    private static final int DATABASE_VERSION = 1;

    // table name
    public static final String DATABASE_TABLE = "shedules";

    // columns name
    public static final String TITLE_COLUMN = "title";
    public static final String IMAGE_COLUMN = "image";

    //table create
    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TITLE_COLUMN
            + " text not null, " + IMAGE_COLUMN  + " integer);";

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    //Called when version of app updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       // Write to log
        Log.w("SQLite", "Updating from version " + oldVersion + " to version " + newVersion);

        // Deleting old table and creating new
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        // Creating new
        onCreate(db);
    }

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }
}
