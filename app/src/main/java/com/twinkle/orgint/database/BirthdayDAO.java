package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BirthdayDAO
{
    private DatabaseHelper dbHelper;

    public BirthdayDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context);
    }

    public int insert(Birthday birthday)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(Birthday.TASK, birthday.getTask());
        values.put(Birthday.TYPE, birthday.getType());
        values.put(Birthday.DATE, birthday.getDate());
        values.put(Birthday.TIME, birthday.getTime());
        values.put(Birthday.COMMENT, birthday.getComment());
        values.put(Birthday.IMPORTANCE, birthday.getImportance());
        values.put(Birthday.IMPORTANCE_VALUE, birthday.getImportance_value());


        // Inserting Row
        long birthday_id = db.insert(Birthday.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) birthday_id;
    }

    public void update(Birthday birthday)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(Birthday.TASK, birthday.getTask());
        values.put(Birthday.TYPE, birthday.getType());
        values.put(Birthday.DATE, birthday.getDate());
        values.put(Birthday.TIME, birthday.getTime());
        values.put(Birthday.COMMENT, birthday.getComment());
        values.put(Birthday.IMPORTANCE, birthday.getImportance());
        values.put(Birthday.IMPORTANCE_VALUE, birthday.getImportance_value());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Birthday.TABLE, values, Birthday.ID + "= ?", new String[] { String.valueOf(birthday.getID()) });
        db.close(); // Closing database connection
    }

    public void delete(int birthday_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Birthday.TABLE, Birthday.ID + "= ?", new String[] { String.valueOf(birthday_id) });
        db.close(); // Closing database connection
    }

    public List<Birthday> getBirthdayList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Birthday.ID + "," +
                Birthday.TASK + "," +
                Birthday.TYPE + "," +
                Birthday.DATE + "," +
                Birthday.TIME + "," +
                Birthday.COMMENT + "," +
                Birthday.IMPORTANCE + "," +
                Birthday.IMPORTANCE_VALUE +
                " FROM " + Birthday.TABLE;

        //Schedule schedule = new Schedule();
        List<Birthday> birthdayList = new ArrayList<>();

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst())
        {
            do {
                Birthday birthday = new Birthday();

                birthday.setID(cursor.getInt(cursor.getColumnIndex(Birthday.ID)));
                birthday.setTask(cursor.getString(cursor.getColumnIndex(Birthday.TASK)));
                birthday.setType(cursor.getString(cursor.getColumnIndex(Birthday.TYPE)));
                birthday.setDate(cursor.getString(cursor.getColumnIndex(Birthday.DATE)));
                birthday.setTime(cursor.getString(cursor.getColumnIndex(Birthday.TIME)));
                birthday.setComment(cursor.getString(cursor.getColumnIndex(Birthday.COMMENT)));
                birthday.setImportance(cursor.getString(cursor.getColumnIndex(Birthday.IMPORTANCE)));
                birthday.setImportance_value(cursor.getInt(cursor.getColumnIndex(Birthday.IMPORTANCE_VALUE)));

                birthdayList.add(birthday);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return birthdayList;
    }

    public Birthday getBirthdayById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Birthday.ID + "," +
                Birthday.TASK + "," +
                Birthday.TYPE + "," +
                Birthday.DATE + "," +
                Birthday.TIME + "," +
                Birthday.COMMENT + "," +
                Birthday.IMPORTANCE + "," +
                Birthday.IMPORTANCE_VALUE +
                " FROM " + Birthday.TABLE
                + " WHERE " +
                Birthday.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Birthday birthday = new Birthday();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                birthday.setID(cursor.getInt(cursor.getColumnIndex(Birthday.ID)));
                birthday.setTask(cursor.getString(cursor.getColumnIndex(Birthday.TASK)));
                birthday.setType(cursor.getString(cursor.getColumnIndex(Birthday.TYPE)));
                birthday.setDate(cursor.getString(cursor.getColumnIndex(Birthday.DATE)));
                birthday.setTime(cursor.getString(cursor.getColumnIndex(Birthday.TIME)));
                birthday.setComment(cursor.getString(cursor.getColumnIndex(Birthday.COMMENT)));
                birthday.setImportance(cursor.getString(cursor.getColumnIndex(Birthday.IMPORTANCE)));
                birthday.setImportance_value(cursor.getInt(cursor.getColumnIndex(Birthday.IMPORTANCE_VALUE)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return birthday;
    }
}
