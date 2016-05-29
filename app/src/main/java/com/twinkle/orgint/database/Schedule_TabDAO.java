package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Schedule_TabDAO
{
    private DatabaseHelper dbHelper;

    public Schedule_TabDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context, "organizer.db", null, 1);
    }

    //Adding data to Shedule_Tab Table
    public int insert(Schedule_Tab schedule_tab)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(Schedule_Tab.TITLE, schedule_tab.getTitle());
        values.put(Schedule_Tab.IMAGE, schedule_tab.getImage());
        values.put(Schedule_Tab.URGENT_IMPORTANT_COUNT, schedule_tab.getUrgent_important_count());
        values.put(Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_important_count());
        values.put(Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getUrgent_not_important_count());
        values.put(Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_not_important_count());

        // Inserting Row
        long schedule_tab_Id = db.insert(Schedule_Tab.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) schedule_tab_Id;

        //Adding data (sql query) 2 - way
       /* String insertQuery = "INSERT INTO " +
                Schedule_Tab.TABLE + " ("
                + Schedule_Tab.TITLE + ", "
                + Schedule_Tab.IMAGE + ", "
                + Schedule_Tab.URGENT_IMPORTANT_COUNT  +  ") VALUES ('Work_Tasks', " +  R.drawable.work_t + ")";

        db.execSQL(insertQuery);*/
    }

    //Updating data in Shedule_Tab Table
    public void update(Schedule_Tab schedule_tab)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(Schedule_Tab.TITLE, schedule_tab.getTitle());
        values.put(Schedule_Tab.IMAGE, schedule_tab.getImage());
        values.put(Schedule_Tab.URGENT_IMPORTANT_COUNT, schedule_tab.getUrgent_important_count());
        values.put(Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_important_count());
        values.put(Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getUrgent_not_important_count());
        values.put(Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_not_important_count());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Schedule_Tab.TABLE, values, Schedule_Tab.ID + "= ?", new String[] { String.valueOf(schedule_tab.getSchedule_tab_ID()) });
        db.close(); // Closing database connection
    }

    //Deleting data from Shedule_Tab Table
    public void delete(int schedule_tab_Id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Schedule_Tab.TABLE, Schedule_Tab.ID + "= ?", new String[] { String.valueOf(schedule_tab_Id) });
        db.close(); // Closing database connection
    }

    //Get list of all Schedule_Tabs
    public List<Schedule_Tab> getSchedule_TabList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Schedule_Tab.ID + "," +
                Schedule_Tab.TITLE + "," +
                Schedule_Tab.IMAGE + "," +
                Schedule_Tab.URGENT_IMPORTANT_COUNT + "," +
                Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT + "," +
                Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT + "," +
                Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT +
                " FROM " + Schedule_Tab.TABLE;

        //Schedule_Tab schedule_tab = new Schedule_Tab();
        List<Schedule_Tab> schedule_tabList = new ArrayList<>();

        //using query
    /*    Cursor cursor = db.query(Schedule_Tab.TABLE, new String[] {Schedule_Tab.ID,
                        Schedule_Tab.TITLE,
                        Schedule_Tab.IMAGE,
                        Schedule_Tab.URGENT_IMPORTANT_COUNT,
                        Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT,
                        Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT,
                        Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT },
                null, null,
                null, null, null) ;*/

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Move from first schedule_tab to last
        if (cursor.moveToFirst())
        {
            do {
                Schedule_Tab schedule_tab = new Schedule_Tab();

                schedule_tab.setSchedule_tab_ID(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.ID)));
                schedule_tab.setTitle(cursor.getString(cursor.getColumnIndex(Schedule_Tab.TITLE)));
                schedule_tab.setImage(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.IMAGE)));
                schedule_tab.setUrgent_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.URGENT_IMPORTANT_COUNT)));
                schedule_tab.setNot_urgent_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT)));
                schedule_tab.setUrgent_not_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT)));
                schedule_tab.setNot_urgent_not_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT)));

                schedule_tabList.add(schedule_tab);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return schedule_tabList;

    }

    //Get Schedule_Tab by id
    public Schedule_Tab getSchedule_TabById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Schedule_Tab.ID + "," +
                Schedule_Tab.TITLE + "," +
                Schedule_Tab.IMAGE + "," +
                Schedule_Tab.URGENT_IMPORTANT_COUNT + "," +
                Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT + "," +
                Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT + "," +
                Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT +
                " FROM " + Schedule_Tab.TABLE
                + " WHERE " +
                Schedule_Tab.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Schedule_Tab schedule_tab = new Schedule_Tab();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                schedule_tab.setSchedule_tab_ID(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.ID)));
                schedule_tab.setTitle(cursor.getString(cursor.getColumnIndex(Schedule_Tab.TITLE)));
                schedule_tab.setImage(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.IMAGE)));
                schedule_tab.setUrgent_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.URGENT_IMPORTANT_COUNT)));
                schedule_tab.setNot_urgent_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT)));
                schedule_tab.setUrgent_not_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT)));
                schedule_tab.setNot_urgent_not_important_count(cursor.getInt(cursor.getColumnIndex(Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return schedule_tab;
    }


  /*  public void create()
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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
    }*/


}
