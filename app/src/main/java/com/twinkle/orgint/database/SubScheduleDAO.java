package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SubScheduleDAO implements DatabaseTableCreator
{
    private DatabaseHelper dbHelper;

    public SubScheduleDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context);
        dbHelper.subScheduleCreator = this;
    }

    @Override
    public void create(SQLiteDatabase db)
    {
        //table create
        String DATABASE_CREATE_SCRIPT = "create table "
                + Sub_schedule.TABLE + " (" + Sub_schedule.ID + " integer primary key autoincrement, "
                + Sub_schedule.SCHEDULE_ID  + " integer not null, "
                + Sub_schedule.TIME  + " text not null, "
                + Sub_schedule.TYPE  + " text not null, "
                + Sub_schedule.COMMENT  + " text not null, "
                + Sub_schedule.TASK  + " text not null, "
                + Sub_schedule.INTEREST  + " text not null)";

        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    //Adding data to Sub Shedule Table
    public int insert(Sub_schedule sub_schedule)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(Sub_schedule.SCHEDULE_ID, sub_schedule.getSchedule_ID());
        values.put(Sub_schedule.TIME, sub_schedule.getTime());
        values.put(Sub_schedule.TYPE, sub_schedule.getType());
        values.put(Sub_schedule.TASK, sub_schedule.getTask());
        values.put(Sub_schedule.COMMENT, sub_schedule.getComment());
        values.put(Sub_schedule.INTEREST, sub_schedule.getInterest());

        // Inserting Row
        long sub_schedule_Id = db.insert(Sub_schedule.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) sub_schedule_Id;
    }

    //Updating data in Sub Shedule Table
    public void update(Sub_schedule sub_schedule)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(Sub_schedule.SCHEDULE_ID, sub_schedule.getSchedule_ID());
        values.put(Sub_schedule.TIME, sub_schedule.getTime());
        values.put(Sub_schedule.TYPE, sub_schedule.getType());
        values.put(Sub_schedule.TASK, sub_schedule.getTask());
        values.put(Sub_schedule.COMMENT, sub_schedule.getComment());
        values.put(Sub_schedule.INTEREST, sub_schedule.getInterest());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Sub_schedule.TABLE, values, Sub_schedule.ID + "= ?", new String[] { String.valueOf(sub_schedule.getSub_schedule_ID()) });
        db.close(); // Closing database connection
    }

    //Deleting data from Sub Shedule Table
    public void delete(int sub_schedule_Id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Sub_schedule.TABLE, Sub_schedule.ID + "= ?", new String[] { String.valueOf(sub_schedule_Id) });
        db.close(); // Closing database connection
    }

    //Get list of all Sub Shedules
    public List<Sub_schedule> getSubScheduleList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Sub_schedule.ID + "," +
                Sub_schedule.SCHEDULE_ID + "," +
                Sub_schedule.TIME + "," +
                Sub_schedule.TYPE + "," +
                Sub_schedule.TASK + "," +
                Sub_schedule.COMMENT + "," +
                Sub_schedule.INTEREST +
                " FROM " + Sub_schedule.TABLE;

        //Schedule schedule = new Schedule();
        List<Sub_schedule> sub_scheduleList = new ArrayList<>();

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Move from first schedule_tab to last
        if (cursor.moveToFirst())
        {
            do {
                Sub_schedule sub_schedule = new Sub_schedule();

                sub_schedule.setSub_schedule_ID(cursor.getInt(cursor.getColumnIndex(Sub_schedule.ID)));
                sub_schedule.setSchedule_ID(cursor.getInt(cursor.getColumnIndex(Sub_schedule.SCHEDULE_ID)));
                sub_schedule.setTime(cursor.getString(cursor.getColumnIndex(Sub_schedule.TIME)));
                sub_schedule.setType(cursor.getString(cursor.getColumnIndex(Sub_schedule.TYPE)));
                sub_schedule.setTask(cursor.getString(cursor.getColumnIndex(Sub_schedule.TASK)));
                sub_schedule.setComment(cursor.getString(cursor.getColumnIndex(Sub_schedule.COMMENT)));
                sub_schedule.setInterest(cursor.getString(cursor.getColumnIndex(Sub_schedule.INTEREST)));

                sub_scheduleList.add(sub_schedule);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sub_scheduleList;
    }

    //Get Sub Shedule by id
    public Sub_schedule getSubScheduleById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Sub_schedule.ID + "," +
                Sub_schedule.SCHEDULE_ID + "," +
                Sub_schedule.TIME + "," +
                Sub_schedule.TYPE + "," +
                Sub_schedule.TASK + "," +
                Sub_schedule.COMMENT + "," +
                Sub_schedule.INTEREST +
                " FROM " + Sub_schedule.TABLE
                + " WHERE " +
                Sub_schedule.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Sub_schedule sub_schedule = new Sub_schedule();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                sub_schedule.setSub_schedule_ID(cursor.getInt(cursor.getColumnIndex(Sub_schedule.ID)));
                sub_schedule.setSchedule_ID(cursor.getInt(cursor.getColumnIndex(Sub_schedule.SCHEDULE_ID)));
                sub_schedule.setTime(cursor.getString(cursor.getColumnIndex(Sub_schedule.TIME)));
                sub_schedule.setType(cursor.getString(cursor.getColumnIndex(Sub_schedule.TYPE)));
                sub_schedule.setTask(cursor.getString(cursor.getColumnIndex(Sub_schedule.TASK)));
                sub_schedule.setComment(cursor.getString(cursor.getColumnIndex(Sub_schedule.COMMENT)));
                sub_schedule.setInterest(cursor.getString(cursor.getColumnIndex(Sub_schedule.INTEREST)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return sub_schedule;
    }
}
