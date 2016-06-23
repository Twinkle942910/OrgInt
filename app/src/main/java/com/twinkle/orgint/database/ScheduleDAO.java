package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO implements DatabaseTableCreator
{
    private DatabaseHelper dbHelper;

    public ScheduleDAO(Context context)
    {
       // dbHelper = new DatabaseHelper(context, "organizer.db", null, 1);
        dbHelper = new DatabaseHelper(context);
        dbHelper.scheduleCreator = this;
    }

    @Override
    public void create(SQLiteDatabase db)
    {
        //table create
        String DATABASE_CREATE_SCRIPT = "create table "
                + Schedule.TABLE + " (" + Schedule.ID + " integer primary key autoincrement, "
                + Schedule.DAY  + " text not null, "
                + Schedule.DATE  + " text not null, "
                + Schedule.TYPE  + " text not null)";

        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    //Adding data to Shedule Table
    public int insert(Schedule schedule)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(Schedule.DAY, schedule.getDay());
        values.put(Schedule.DATE, schedule.getDate());
        values.put(Schedule.TYPE, schedule.getType());

        // Inserting Row
        long schedule_Id = db.insert(Schedule.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) schedule_Id;

        //Adding data (sql query) 2 - way
    /*    String insertQuery = "INSERT INTO " +
                Schedule.TABLE + " ("
                + Schedule.DAY + ", "
                + Schedule.DATE + ", "
                + Schedule.TYPE  +  ") VALUES ('Monday', " +  '12/12/2016' + 'Schedule' + ")";

        db.execSQL(insertQuery);*/
    }

    //Updating data in Shedule Table
    public void update(Schedule schedule)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(Schedule.DAY, schedule.getDay());
        values.put(Schedule.DATE, schedule.getDate());
        values.put(Schedule.TYPE, schedule.getType());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Schedule.TABLE, values, Schedule.ID + "= ?", new String[] { String.valueOf(schedule.getSchedule_ID()) });
        db.close(); // Closing database connection
    }

    //Deleting data from Shedule Table
    public void delete(int schedule_Id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Schedule.TABLE, Schedule.ID + "= ?", new String[] { String.valueOf(schedule_Id) });
        db.close(); // Closing database connection
    }

    //Get list of all Schedules
    public List<Schedule> getScheduleList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Schedule.ID + "," +
                Schedule.DAY + "," +
                Schedule.DATE + "," +
                Schedule.TYPE +
                " FROM " + Schedule.TABLE;

        //Schedule schedule = new Schedule();
        List<Schedule> scheduleList = new ArrayList<>();

        //using query
/*        Cursor cursor = db.query(Schedule.TABLE, new String[] {Schedule.ID,
                        Schedule.DAY,
                                Schedule.DATE,
                                Schedule.TYPE  },
                null, null,
                null, null, null) ;*/

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Move from first schedule_tab to last
        if (cursor.moveToFirst())
        {
            do {
                Schedule schedule = new Schedule();

                schedule.setSchedule_ID(cursor.getInt(cursor.getColumnIndex(Schedule.ID)));
                schedule.setDay(cursor.getString(cursor.getColumnIndex(Schedule.DAY)));
                schedule.setDate(cursor.getString(cursor.getColumnIndex(Schedule.DATE)));
                schedule.setType(cursor.getString(cursor.getColumnIndex(Schedule.TYPE)));

                scheduleList.add(schedule);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return scheduleList;

    }

    //Get Schedule by id
    public Schedule getScheduleById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Schedule.ID + "," +
                Schedule.DAY + "," +
                Schedule.DATE + "," +
                Schedule.TYPE +
                " FROM " + Schedule.TABLE
                + " WHERE " +
                Schedule.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Schedule schedule = new Schedule();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                schedule.setSchedule_ID(cursor.getInt(cursor.getColumnIndex(Schedule.ID)));
                schedule.setDay(cursor.getString(cursor.getColumnIndex(Schedule.DAY)));
                schedule.setDate(cursor.getString(cursor.getColumnIndex(Schedule.DATE)));
                schedule.setType(cursor.getString(cursor.getColumnIndex(Schedule.TYPE)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return schedule;
    }
}
