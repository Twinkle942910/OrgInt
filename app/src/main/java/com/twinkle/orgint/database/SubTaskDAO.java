package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SubTaskDAO
{
    private DatabaseHelper dbHelper;

    public SubTaskDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context);
    }

    public int insert(Sub_task sub_task)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(Sub_task.TODO_ID, sub_task.getTodo_ID());
        values.put(Sub_task.WORK_TASK_ID, sub_task.getWork_task_id());
        values.put(Sub_task.BIRTHDAY_ID, sub_task.getBirthday_id());
        values.put(Sub_task.CONTENT, sub_task.getContent());
        values.put(Sub_task.ISDONE, sub_task.isDone());

        // Inserting Row
        long sub_task_id = db.insert(Sub_task.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) sub_task_id;
    }

    public void update(Sub_task sub_task)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(Sub_task.TODO_ID, sub_task.getTodo_ID());
        values.put(Sub_task.WORK_TASK_ID, sub_task.getWork_task_id());
        values.put(Sub_task.BIRTHDAY_ID, sub_task.getBirthday_id());
        values.put(Sub_task.CONTENT, sub_task.getContent());
        values.put(Sub_task.ISDONE, sub_task.isDone());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Sub_task.TABLE, values, Sub_task.ID + "= ?", new String[] { String.valueOf(sub_task.getID()) });
        db.close(); // Closing database connection
    }

    public void delete(int sub_task_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Sub_task.TABLE, Sub_task.ID + "= ?", new String[] { String.valueOf(sub_task_id) });
        db.close(); // Closing database connection
    }

    public List<Sub_task> getSubTaskList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Sub_task.ID + "," +
                Sub_task.TODO_ID + "," +
                Sub_task.WORK_TASK_ID + "," +
                Sub_task.BIRTHDAY_ID + "," +
                Sub_task.CONTENT + "," +
                Sub_task.ISDONE +
                " FROM " + Sub_task.TABLE;

        //Schedule schedule = new Schedule();
        List<Sub_task> subTaskList = new ArrayList<>();

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst())
        {
            do {
                Sub_task sub_task = new Sub_task();

                sub_task.setID(cursor.getInt(cursor.getColumnIndex(Sub_task.ID)));
                sub_task.setTodo_ID(cursor.getInt(cursor.getColumnIndex(Sub_task.TODO_ID)));
                sub_task.setWork_task_id(cursor.getInt(cursor.getColumnIndex(Sub_task.WORK_TASK_ID)));
                sub_task.setBirthday_id(cursor.getInt(cursor.getColumnIndex(Sub_task.BIRTHDAY_ID)));
                sub_task.setContent(cursor.getString(cursor.getColumnIndex(Sub_task.CONTENT)));
                sub_task.setDone(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Sub_task.ISDONE))));

                subTaskList.add(sub_task);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return subTaskList;
    }

    public Sub_task getSubTaskById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Sub_task.ID + "," +
                Sub_task.TODO_ID + "," +
                Sub_task.WORK_TASK_ID + "," +
                Sub_task.BIRTHDAY_ID + "," +
                Sub_task.CONTENT + "," +
                Sub_task.ISDONE +
                " FROM " + Sub_task.TABLE
                + " WHERE " +
                Sub_task.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Sub_task sub_task = new Sub_task();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                sub_task.setID(cursor.getInt(cursor.getColumnIndex(Sub_task.ID)));
                sub_task.setTodo_ID(cursor.getInt(cursor.getColumnIndex(Sub_task.TODO_ID)));
                sub_task.setWork_task_id(cursor.getInt(cursor.getColumnIndex(Sub_task.WORK_TASK_ID)));
                sub_task.setBirthday_id(cursor.getInt(cursor.getColumnIndex(Sub_task.BIRTHDAY_ID)));
                sub_task.setContent(cursor.getString(cursor.getColumnIndex(Sub_task.CONTENT)));
                sub_task.setDone(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Sub_task.ISDONE))));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return sub_task;
    }
}
