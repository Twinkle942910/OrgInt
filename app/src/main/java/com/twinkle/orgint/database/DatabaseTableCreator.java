package com.twinkle.orgint.database;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseTableCreator
{
    void create(SQLiteDatabase db);
}
