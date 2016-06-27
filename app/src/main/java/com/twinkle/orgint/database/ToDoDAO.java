package com.twinkle.orgint.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ToDoDAO implements DatabaseTableCreator
{
    private DatabaseHelper dbHelper;

    public ToDoDAO(Context context)
    {
        dbHelper = new DatabaseHelper(context);
       // dbHelper.subScheduleCreator = this;
    }

    @Override
    public void create(SQLiteDatabase db)
    {
        //table create
        String DATABASE_CREATE_SCRIPT = "create table "
                + ToDo.TABLE + " (" + ToDo.ID + " integer primary key autoincrement, "
                + ToDo.TASK  + " text not null, "
                + ToDo.TYPE  + " text not null, "
                + ToDo.DATE  + " text not null, "
                + ToDo.TIME  + " text not null, "
                + ToDo.COMMENT  + " text not null, "
                + ToDo.INTEREST  + " text not null)";

        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    //Adding data to ToDo Table
    public int insert(ToDo todo)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /*ContentValues is used for adding new rows to table. Every object of that class
        presents (consists of) one table row and looks like associated array with column names
        and values that are appropriate to them.*/
        ContentValues values = new ContentValues();

        //Adding values
        values.put(ToDo.TASK, todo.getTask());
        values.put(ToDo.TYPE, todo.getType());
        values.put(ToDo.DATE, todo.getDate());
        values.put(ToDo.TIME, todo.getTime());
        values.put(ToDo.COMMENT, todo.getComment());
        values.put(ToDo.INTEREST, todo.getInterest());


        // Inserting Row
        long todo_id = db.insert(ToDo.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) todo_id;
    }

    //Updating data in ToDo Table
    public void update(ToDo todo)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Adding values
        values.put(ToDo.TASK, todo.getTask());
        values.put(ToDo.TYPE, todo.getType());
        values.put(ToDo.DATE, todo.getDate());
        values.put(ToDo.TIME, todo.getTime());
        values.put(ToDo.COMMENT, todo.getComment());
        values.put(ToDo.INTEREST, todo.getInterest());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(ToDo.TABLE, values, ToDo.ID + "= ?", new String[] { String.valueOf(todo.getID()) });
        db.close(); // Closing database connection
    }

    //Deleting data from ToDo Table
    public void delete(int todo_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(ToDo.TABLE, ToDo.ID + "= ?", new String[] { String.valueOf(todo_id) });
        db.close(); // Closing database connection
    }

    //Get list of all ToDo
    public List<ToDo> getToDoList()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ToDo.ID + "," +
                ToDo.TASK + "," +
                ToDo.TYPE + "," +
                ToDo.DATE + "," +
                ToDo.TIME + "," +
                ToDo.COMMENT + "," +
                ToDo.INTEREST +
                " FROM " + ToDo.TABLE;

        //Schedule schedule = new Schedule();
        List<ToDo> todoList = new ArrayList<>();

        //using rawQuery
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Move from first ToDo to last
        if (cursor.moveToFirst())
        {
            do {
                ToDo todo = new ToDo();

                todo.setID(cursor.getInt(cursor.getColumnIndex(ToDo.ID)));
                todo.setTask(cursor.getString(cursor.getColumnIndex(ToDo.TASK)));
                todo.setType(cursor.getString(cursor.getColumnIndex(ToDo.TYPE)));
                todo.setDate(cursor.getString(cursor.getColumnIndex(ToDo.DATE)));
                todo.setTime(cursor.getString(cursor.getColumnIndex(ToDo.TIME)));
                todo.setComment(cursor.getString(cursor.getColumnIndex(ToDo.COMMENT)));
                todo.setInterest(cursor.getString(cursor.getColumnIndex(ToDo.INTEREST)));

                todoList.add(todo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoList;
    }

    //Get ToDo by id
    public ToDo getToDoById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                ToDo.ID + "," +
                ToDo.TASK + "," +
                ToDo.TYPE + "," +
                ToDo.DATE + "," +
                ToDo.TIME + "," +
                ToDo.COMMENT + "," +
                ToDo.INTEREST +
                " FROM " + ToDo.TABLE
                + " WHERE " +
                ToDo.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        ToDo todo = new ToDo();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                todo.setID(cursor.getInt(cursor.getColumnIndex(ToDo.ID)));
                todo.setTask(cursor.getString(cursor.getColumnIndex(ToDo.TASK)));
                todo.setType(cursor.getString(cursor.getColumnIndex(ToDo.TYPE)));
                todo.setDate(cursor.getString(cursor.getColumnIndex(ToDo.DATE)));
                todo.setTime(cursor.getString(cursor.getColumnIndex(ToDo.TIME)));
                todo.setComment(cursor.getString(cursor.getColumnIndex(ToDo.COMMENT)));
                todo.setInterest(cursor.getString(cursor.getColumnIndex(ToDo.INTEREST)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return todo;
    }
}
