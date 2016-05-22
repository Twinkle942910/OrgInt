package com.twinkle.orgint.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.TestCardData;
import com.twinkle.orgint.adapter.ShedulesRecycleAdapter;
import com.twinkle.orgint.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ShedulesFragment extends AbstractTabFragment
{
    public static final String SHEDULES_PAGE = "SHEDULES_PAGE";
    public static final int LAYOUT = R.layout.fragment_shedules;

    private int page;

    private List<TestCardData> schedules;

    //DB
/*    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSQLiteDatabase;*/
    //

    public static ShedulesFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(SHEDULES_PAGE, page);

        ShedulesFragment fragment = new ShedulesFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_shedules_name));

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(SHEDULES_PAGE);

        //DB work (Adding data)
       /* mDatabaseHelper = new DatabaseHelper(context.getApplicationContext(), "organizer.db", null, 1);
                                      //отримує базу для запису (getReadable - створення та відкриття для читання)
        mSQLiteDatabase = mDatabaseHelper.getReadableDatabase();*/

    /*    Класс ContentValues используется для добавления новых строк в таблицу. Каждый
        объект этого класса представляет собой одну строку таблицы и выглядит как
        ассоциативный массив с именами столбцов и значениями, которые им соответствуют.*/
       /* ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put(DatabaseHelper.TITLE_COLUMN, "ToDo");
        values.put(DatabaseHelper.IMAGE_COLUMN, R.drawable.todo);
        // Вставляем данные в таблицу
        mSQLiteDatabase.insert("shedules", null, values);*/


        //Adding data (sql query) 2 - way
 /*       String insertQuery = "INSERT INTO " +
                DatabaseHelper.DATABASE_TABLE +
                " (" + DatabaseHelper.TITLE_COLUMN + ", " + DatabaseHelper.IMAGE_COLUMN + ") VALUES ('Work_Tasks', " +  R.drawable.work_t + ")";
        mSQLiteDatabase.execSQL(insertQuery);
*/

        //Updating column (changing value)
      /*  ContentValues values = new ContentValues();
        values.put(DatabaseHelper.IMAGE_COLUMN, R.drawable.schedules);
        mSQLiteDatabase.update(mDatabaseHelper.DATABASE_TABLE,
                values,
                mDatabaseHelper.IMAGE_COLUMN + "= ?", new String[]{ Integer.toString(R.drawable.todo) });*/

        //Deleting column
   /*     mSQLiteDatabase.delete(mDatabaseHelper.DATABASE_TABLE,
                mDatabaseHelper._ID + "= ?", new String[]{ Integer.toString(3) });
*/
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        initializeData();

        ShedulesRecycleAdapter adapter = new  ShedulesRecycleAdapter(schedules);
        recyclerView.setAdapter(adapter);

        return view;
    }


    public void addShedule()
    {
        Snackbar.make(view, "Shedule", Snackbar.LENGTH_LONG)
                .setAction("Action 1", null).show();
    }

    private void initializeData()
    {
        schedules = new ArrayList<>();

        //DB Work (getting data)
        //query()
       /* Cursor cursor = mSQLiteDatabase.query("shedules", new String[] {DatabaseHelper.TITLE_COLUMN,
                                                                   DatabaseHelper.IMAGE_COLUMN},
                                                                           null, null,
                                                                            null, null, null) ;

        cursor.moveToFirst();

        String sheduleTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE_COLUMN));
        int sheduleImage = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IMAGE_COLUMN));

        cursor.moveToNext();

        String sheduleTitle2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE_COLUMN));
        int sheduleImage2 = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IMAGE_COLUMN));

        cursor.moveToNext();
        cursor.moveToNext();

        String sheduleTitle3 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE_COLUMN));
        int sheduleImage3 = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IMAGE_COLUMN));*/

      /*  TextView infoTextView = (TextView)view.findViewById(R.id.textView);
        infoTextView.setText("Кот " + catName + " имеет телефон " + phoneNumber + " и ему " +
                age + " лет");*/

        // не забываем закрывать курсор
       /// cursor.close();

        schedules.add(new TestCardData("Schedules",  R.drawable.schedules));
        schedules.add(new TestCardData("ToDo", R.drawable.todo));

        //raw query()
        // Абстрактный пример
        // Метод 2: Сырой SQL-запрос
   /*     String query = "SELECT " + DatabaseHelper.TITLE_COLUMN + ", "
                + DatabaseHelper.IMAGE_COLUMN + " FROM " + DatabaseHelper.DATABASE_TABLE;
        Cursor cursor2 = mSQLiteDatabase.rawQuery(query, null);
        while (cursor2.moveToNext()) {
            String sheduleTitle = cursor2.getString(cursor2
                    .getColumnIndex(DatabaseHelper.TITLE_COLUMN));
            int sheduleImage = cursor2.getInt(cursor2
                    .getColumnIndex(DatabaseHelper.IMAGE_COLUMN));

            schedules.add(new TestCardData(sheduleTitle, sheduleImage));

        }
        cursor2.close();*/

        //DB viewing data
     /*   schedules.add(new TestCardData(sheduleTitle, sheduleImage));
        schedules.add(new TestCardData(sheduleTitle2, sheduleImage2));
        schedules.add(new TestCardData(sheduleTitle3, sheduleImage3));*/
        //

        schedules.add(new TestCardData("Work Tasks", R.drawable.work_t));
    }




}

