package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkTaskDAO
{
    private DatabaseHelper dbHelper;

    public WorkTaskDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context);
    }

    public int insert(WorkTask workTask)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(WorkTask.TASK, workTask.getTask());
        values.put(WorkTask.TYPE, workTask.getType());
        values.put(WorkTask.DATE, workTask.getDate());
        values.put(WorkTask.TIME, workTask.getTime());
        values.put(WorkTask.COMMENT, workTask.getComment());
        values.put(WorkTask.INTEREST, workTask.getInterest());


        // Inserting Row
        long todo_id = db.insert(WorkTask.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) todo_id;
    }

    public void update(WorkTask workTask)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(WorkTask.TASK, workTask.getTask());
        values.put(WorkTask.TYPE, workTask.getType());
        values.put(WorkTask.DATE, workTask.getDate());
        values.put(WorkTask.TIME, workTask.getTime());
        values.put(WorkTask.COMMENT, workTask.getComment());
        values.put(WorkTask.INTEREST, workTask.getInterest());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(WorkTask.TABLE, values, WorkTask.ID + "= ?", new String[] { String.valueOf(workTask.getID()) });
        db.close(); // Closing database connection
    }

    public void delete(int work_task_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(WorkTask.TABLE, WorkTask.ID + "= ?", new String[] { String.valueOf(work_task_id) });
        db.close(); // Closing database connection
    }

    public List<WorkTask> getWorkTaskList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                WorkTask.ID + "," +
                WorkTask.TASK + "," +
                WorkTask.TYPE + "," +
                WorkTask.DATE + "," +
                WorkTask.TIME + "," +
                WorkTask.COMMENT + "," +
                WorkTask.INTEREST +
                " FROM " + WorkTask.TABLE;

        //Schedule schedule = new Schedule();
        List<WorkTask> workTaskList = new ArrayList<>();

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst())
        {
            do {
                WorkTask workTask = new WorkTask();

                workTask.setID(cursor.getInt(cursor.getColumnIndex(WorkTask.ID)));
                workTask.setTask(cursor.getString(cursor.getColumnIndex(WorkTask.TASK)));
                workTask.setType(cursor.getString(cursor.getColumnIndex(WorkTask.TYPE)));
                workTask.setDate(cursor.getString(cursor.getColumnIndex(WorkTask.DATE)));
                workTask.setTime(cursor.getString(cursor.getColumnIndex(WorkTask.TIME)));
                workTask.setComment(cursor.getString(cursor.getColumnIndex(WorkTask.COMMENT)));
                workTask.setInterest(cursor.getString(cursor.getColumnIndex(WorkTask.INTEREST)));

                workTaskList.add(workTask);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return workTaskList;
    }

    public WorkTask getWorkTaskById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                WorkTask.ID + "," +
                WorkTask.TASK + "," +
                WorkTask.TYPE + "," +
                WorkTask.DATE + "," +
                WorkTask.TIME + "," +
                WorkTask.COMMENT + "," +
                WorkTask.INTEREST +
                " FROM " + WorkTask.TABLE
                + " WHERE " +
                ToDo.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        WorkTask workTask = new WorkTask();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                workTask.setID(cursor.getInt(cursor.getColumnIndex(WorkTask.ID)));
                workTask.setTask(cursor.getString(cursor.getColumnIndex(WorkTask.TASK)));
                workTask.setType(cursor.getString(cursor.getColumnIndex(WorkTask.TYPE)));
                workTask.setDate(cursor.getString(cursor.getColumnIndex(WorkTask.DATE)));
                workTask.setTime(cursor.getString(cursor.getColumnIndex(WorkTask.TIME)));
                workTask.setComment(cursor.getString(cursor.getColumnIndex(WorkTask.COMMENT)));
                workTask.setInterest(cursor.getString(cursor.getColumnIndex(WorkTask.INTEREST)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return workTask;
    }
}
