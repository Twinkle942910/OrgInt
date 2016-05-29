package com.twinkle.orgint.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.twinkle.orgint.R;

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

        //Inserting data to Schedule_Tab
        initializeScheduleTabsData(db);
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

    private void initializeScheduleTabsData(SQLiteDatabase db)
    {
        Schedule_Tab schedules_tab_shedules = new Schedule_Tab();
        Schedule_Tab schedules_tab_todo = new Schedule_Tab();
        Schedule_Tab schedules_tab_work_tasks = new Schedule_Tab();
        Schedule_Tab schedules_tab_birthdays = new Schedule_Tab();

        schedules_tab_shedules.setTitle("Schedule");
        schedules_tab_shedules.setImage(R.drawable.schedules);
        schedules_tab_shedules.setUrgent_important_count(3);
        schedules_tab_shedules.setNot_urgent_important_count(2);
        schedules_tab_shedules.setUrgent_not_important_count(1);
        schedules_tab_shedules.setNot_urgent_not_important_count(1);

        schedules_tab_todo.setTitle("ToDo");
        schedules_tab_todo.setImage(R.drawable.todo);
        schedules_tab_todo.setUrgent_important_count(3);
        schedules_tab_todo.setNot_urgent_important_count(3);
        schedules_tab_todo.setUrgent_not_important_count(2);
        schedules_tab_todo.setNot_urgent_not_important_count(1);

        schedules_tab_work_tasks.setTitle("Work_Tasks");
        schedules_tab_work_tasks.setImage(R.drawable.work_t);
        schedules_tab_work_tasks.setUrgent_important_count(1);
        schedules_tab_work_tasks.setNot_urgent_important_count(3);
        schedules_tab_work_tasks.setUrgent_not_important_count(2);
        schedules_tab_work_tasks.setNot_urgent_not_important_count(1);

        schedules_tab_birthdays.setTitle("Birthdays");
        schedules_tab_birthdays.setImage(R.drawable.birthdays);
        schedules_tab_birthdays.setUrgent_important_count(1);
        schedules_tab_birthdays.setNot_urgent_important_count(1);
        schedules_tab_birthdays.setUrgent_not_important_count(3);
        schedules_tab_birthdays.setNot_urgent_not_important_count(1);

        insertSchedule_TabData(schedules_tab_shedules, db);
        insertSchedule_TabData(schedules_tab_todo, db);
        insertSchedule_TabData(schedules_tab_work_tasks, db);
        insertSchedule_TabData(schedules_tab_birthdays, db);

        Log.w("SQLite", "Initializing Data");
    }

    private void insertSchedule_TabData(Schedule_Tab schedule_tab, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();

        values.put(Schedule_Tab.TITLE, schedule_tab.getTitle());
        values.put(Schedule_Tab.IMAGE, schedule_tab.getImage());
        values.put(Schedule_Tab.URGENT_IMPORTANT_COUNT, schedule_tab.getUrgent_important_count());
        values.put(Schedule_Tab.NOT_URGENT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_important_count());
        values.put(Schedule_Tab.URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getUrgent_not_important_count());
        values.put(Schedule_Tab.NOT_URGENT_NOT_IMPORTANT_COUNT, schedule_tab.getNot_urgent_not_important_count());

        db.insert(Schedule_Tab.TABLE, null, values);

        Log.w("SQLite", "Inserting Data");
    }

}
