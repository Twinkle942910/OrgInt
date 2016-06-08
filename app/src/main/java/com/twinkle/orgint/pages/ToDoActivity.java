package com.twinkle.orgint.pages;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.ScheduleRecycleAdapter;
import com.twinkle.orgint.adapter.ToDoRecycleAdapter;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_to_do;

    private Toolbar toolbar;

    private ToDoRecycleAdapter adapter;
    private List<ToDo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();
        initToDoList();
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:

                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToDoList()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.todo_list);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(llm);

        initializeData();

        adapter = new ToDoRecycleAdapter(todos, this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeData()
    {
        todos = new ArrayList<>();

        Sub_task st1 = new Sub_task();

        st1.setContent("1. Open box");
        st1.setDone(false);

        Sub_task st2 = new Sub_task();

        st2.setContent("2. Check mistakes");
        st2.setDone(false);

        Sub_task st3 = new Sub_task();

        st3.setContent("3. Repair");
        st3.setDone(false);

        List<Sub_task> lst1  = new ArrayList<>();

        lst1.add(st1);
        lst1.add(st2);
        lst1.add(st3);

        ToDo td1 = new ToDo(lst1);

        td1.setTask("Repair PC");
        td1.setType("ToDo");
        td1.setDate("June 9,");
        td1.setTime("19:00 am");

        td1.setComment("Blow the dust");

        todos.add(td1);
    }
}
