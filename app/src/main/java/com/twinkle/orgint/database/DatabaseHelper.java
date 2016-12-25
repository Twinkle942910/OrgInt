package com.twinkle.orgint.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns
{
    // database name
    private static final String DATABASE_NAME = "organizer.db";
    // database version
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    public DatabaseTableCreator scheduleCreator;
    public DatabaseTableCreator subScheduleCreator;

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //All necessary tables you like to create will create here

        //Schedule table create
        scheduleCreator.create(db);

        //table sub_schedule create
        String CREATE_SUB_SCHEDULE = "create table "
                + Sub_schedule.TABLE + " (" + Sub_schedule.ID + " integer primary key autoincrement, "
                + Sub_schedule.SCHEDULE_ID  + " integer not null, "
                + Sub_schedule.TIME  + " text not null, "
                + Sub_schedule.TYPE  + " text not null, "
                + Sub_schedule.COMMENT  + " text not null, "
                + Sub_schedule.TASK  + " text not null, "
                + Sub_schedule.IMPORTANCE + " text not null, "
                + Sub_schedule.IMPORTANCE_VALUE + " integer not null)";

        //table todo create
        String CREATE_TODO = "create table "
                + ToDo.TABLE + " (" + ToDo.ID + " integer primary key autoincrement, "
                + ToDo.TASK  + " text not null, "
                + ToDo.TYPE  + " text not null, "
                + ToDo.DATE  + " text not null, "
                + ToDo.TIME  + " text not null, "
                + ToDo.COMMENT  + " text not null, "
                + ToDo.IMPORTANCE + " text not null, "
                + ToDo.IMPORTANCE_VALUE + " integer not null)";

        //table sub task create
        String CREATE_SUB_TASK = "create table "
                + Sub_task.TABLE + " (" + Sub_task.ID + " integer primary key autoincrement, "
                + Sub_task.TODO_ID  + " int null, "
                + Sub_task.WORK_TASK_ID  + " int null, "
                + Sub_task.BIRTHDAY_ID  + " int null, "
                + Sub_task.CONTENT  + " text not null, "
                + Sub_task.ISDONE  + " text not null)";

        //table work task create
        String CREATE_WORK_TASK = "create table "
                + WorkTask.TABLE + " (" + WorkTask.ID + " integer primary key autoincrement, "
                + WorkTask.TASK  + " text not null, "
                + WorkTask.TYPE  + " text not null, "
                + WorkTask.DATE  + " text not null, "
                + WorkTask.TIME  + " text not null, "
                + WorkTask.COMMENT  + " text not null, "
                + WorkTask.IMPORTANCE + " text not null, "
                + WorkTask.IMPORTANCE_VALUE + " integer not null)";

        //table birthday create
        String CREATE_BIRTHDAY = "create table "
                + Birthday.TABLE + " (" + Birthday.ID + " integer primary key autoincrement, "
                + Birthday.TASK  + " text not null, "
                + Birthday.TYPE  + " text not null, "
                + Birthday.DATE  + " text not null, "
                + Birthday.TIME  + " text not null, "
                + Birthday.COMMENT  + " text not null, "
                + Birthday.IMPORTANCE + " text not null, "
                + Birthday.IMPORTANCE_VALUE + " integer not null)";

        db.execSQL(CREATE_SUB_SCHEDULE);
        db.execSQL(CREATE_TODO);
        db.execSQL(CREATE_SUB_TASK);
        db.execSQL(CREATE_WORK_TASK);
        db.execSQL(CREATE_BIRTHDAY);

        initWeek(db);
    }

    //Called when version of app updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       // Write to log
        Log.w("SQLite", "Updating from version " + oldVersion + " to version " + newVersion);

        // Deleting old table and creating new
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF IT EXISTS " + Schedule.TABLE);

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

    private void initWeek(SQLiteDatabase db)
    {
        // Get Current Date
        final Calendar calendar = Calendar.getInstance();

        int mYear = calendar.get(Calendar.YEAR);
        int  mMonth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String myFormatDate = "LLLL d, yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormatDate, Locale.US);

        int dayMonday = 0;

        switch (day)
        {
            // Current day is Monday
            case Calendar.MONDAY:
                dayMonday = 0;
                break;

            // Current day is Tuesday
            case Calendar.TUESDAY:
                dayMonday =  - 1;
                break;

            // Current day is Wednesday
            case Calendar.WEDNESDAY:
                dayMonday =  - 2;
                break;

            // Current day is Thursday
            case Calendar.THURSDAY:
                dayMonday =  - 3;
                break;

            // Current day is Friday
            case Calendar.FRIDAY:
                dayMonday =  - 4;
                break;

            // Current day is Saturday
            case Calendar.SATURDAY:
                dayMonday =  - 5;
                break;

            // Current day is Sunday
            case Calendar.SUNDAY:
                dayMonday =  - 6;
                break;
        }

        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);

        setDays(calendar, dateFormat, dayMonday, db);
    }

    private void setDays(Calendar c, SimpleDateFormat sdf, int dayMonday, SQLiteDatabase db)
    {
        for(int i = 0; i < 7; i++)
        {
            if(i == 0)
            {
                c.add(Calendar.DAY_OF_MONTH, dayMonday);
            }
            else
            {
                c.add(Calendar.DAY_OF_MONTH, 1);
            }

            Schedule scheduleDay = new Schedule();

            scheduleDay.setDay(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
            scheduleDay.setType("Schedule");
            scheduleDay.setDate(sdf.format(c.getTime()));

            //schedulesDB.insert(scheduleDay);

            insertScheduleData(scheduleDay, db);
        }
    }

    private void insertScheduleData(Schedule schedule, SQLiteDatabase db)
     {
           ContentValues values = new ContentValues();

            values.put(Schedule.DAY, schedule.getDay());
            values.put(Schedule.DATE, schedule.getDate());
            values.put(Schedule.TYPE, schedule.getType());

            db.insert(Schedule.TABLE, null, values);
     }
}
