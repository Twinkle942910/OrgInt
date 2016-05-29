package com.twinkle.orgint.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.twinkle.orgint.R;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns
{
    // database name
    private static final String DATABASE_NAME = "organizer.db";
    // database version
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //All necessary tables you like to create will create here

        //table create
        String DATABASE_CREATE_SCRIPT = "create table "
                + Schedule_Tab.TABLE + " (" + Schedule_Tab.ID + " integer primary key autoincrement, "
                + Schedule_Tab.TITLE  + " text not null, "
                + Schedule_Tab.IMAGE  + " integer not null, "
                + Schedule_Tab.URGENT_IMPORTANT_COUNT  + " integer not null, "
                + Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT  + " integer not null, "
                + Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT  + " integer not null, "
                + Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT  + " integer not null);";

        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    //Called when version of app updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       // Write to log
        Log.w("SQLite", "Updating from version " + oldVersion + " to version " + newVersion);

        // Deleting old table and creating new
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF IT EXISTS " + Schedule_Tab.TABLE);

        // Creating new (again)
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
