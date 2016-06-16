package com.twinkle.orgint.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.twinkle.orgint.R;
import com.twinkle.orgint.fragments.BirthdayFragment;
import com.twinkle.orgint.fragments.ToDoFragment;
import com.twinkle.orgint.fragments.WorkTaskFragment;

public class EventActivity extends AppCompatActivity
{
    private static final String TAG_FRAGMENT = "EventActivity";
    private static final int LAYOUT = R.layout.activity_event;

    private Toolbar toolbar;

    String category;
    String title;
    String date;
    String time;
    String[] sub_tasks;
    String comment;
    String interest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();

        getDataFromAddingActivity();

        initFragment(savedInstanceState);
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

    private void initFragment(Bundle savedInstanceState)
    {
        Intent callAct = getIntent();
        String fragment_type = callAct.getStringExtra("type");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null)
        {
            if ("ToDo".equals(fragment_type))
            {
               // ToDoFragment todo_fragment = new ToDoFragment();
                final ToDoFragment todo_fragment = ToDoFragment.newInstance(category,title,date,time, sub_tasks, comment, interest);

                fragmentTransaction.add(R.id.event_container, todo_fragment, TAG_FRAGMENT);
                fragmentTransaction.commit();
            }
            else if ("Work Task".equals(fragment_type))
            {
              //  WorkTaskFragment work_task_fragment = new WorkTaskFragment();
                final WorkTaskFragment work_task_fragment = WorkTaskFragment.newInstance(category,title,date,time, sub_tasks, comment, interest);

                fragmentTransaction.add(R.id.event_container, work_task_fragment, TAG_FRAGMENT);
                fragmentTransaction.commit();
            }
            else if("Birthday".equals(fragment_type))
            {
                final BirthdayFragment birthday_fragment = BirthdayFragment.newInstance(category,title,date,time, sub_tasks, comment, interest);

                fragmentTransaction.add(R.id.event_container, birthday_fragment, TAG_FRAGMENT);
                fragmentTransaction.commit();
            }
        }
    }

    public void getDataFromAddingActivity()
    {
        Intent dataFormAdding = getIntent();

        category = dataFormAdding.getStringExtra("category");
        title = dataFormAdding.getStringExtra("title");
        date = dataFormAdding.getStringExtra("date");
        time = dataFormAdding.getStringExtra("time");

        sub_tasks = dataFormAdding.getStringArrayExtra("sub_tasks");
        comment = dataFormAdding.getStringExtra("comment");
        interest = dataFormAdding.getStringExtra("interest");
    }
}
