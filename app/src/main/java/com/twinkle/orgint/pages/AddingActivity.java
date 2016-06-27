package com.twinkle.orgint.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twinkle.orgint.R;
import com.twinkle.orgint.fragments.FragmentClickListener;
import com.twinkle.orgint.fragments.ScheduleEventAddingFragment;
import com.twinkle.orgint.fragments.SimpleEventAddingFragment;
import com.twinkle.orgint.helpers.Constants;

public class AddingActivity extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_adding;
    private static final String TAG_FRAGMENT = "AddingActivity";

    private Toolbar toolbar;
    private FloatingActionButton fab_interest;

    //Task type
    private String type;

    //ToDo: check that detail!(create SimpleEventAddingFragment or not)
    private FragmentClickListener listener;
    private SimpleEventAddingFragment simpleEvent;
    private ScheduleEventAddingFragment scheduleEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();
        initFAB();

        initTypeAndFragments(savedInstanceState);

        listener = simpleEvent;
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

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adding, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        switch(item.getItemId())
        {

            case android.R.id.home:

                this.finish();
                return true;

            case R.id.save:
                if("Schedule".equals(type))
                {
                    return scheduleEvent.checkValidation();
                }
                return simpleEvent.checkValidation();

        }

        return super.onOptionsItemSelected(item);
    }

    private void initFAB()
    {
        fab_interest = (FloatingActionButton)findViewById(R.id.fab_interest);

        fab_interest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(getApplicationContext(), InterestsActivity.class);

                //Added recently
                intent.putExtra("red_code", "From adding activity");

                startActivityForResult(intent, Constants.REQUEST_CODE_INTEREST);
            }
        });
    }

    private void initTypeAndFragments(Bundle savedInstanceState)
    {
        Intent eventType = getIntent();
        type = eventType.getStringExtra("type");

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null)
        {
            switch (type)
            {
                case "ToDo":
                    this.setTitle("New " + type + " Task");

                    simpleEvent = SimpleEventAddingFragment.newInstance(type);
                    fragmentTransaction.add(R.id.adding_container, simpleEvent, TAG_FRAGMENT);
                    fragmentTransaction.commit();

                    break;

                case "Work Task":
                    this.setTitle("New " + type);

                    simpleEvent = SimpleEventAddingFragment.newInstance(type);
                    fragmentTransaction.add(R.id.adding_container, simpleEvent, TAG_FRAGMENT);
                    fragmentTransaction.commit();

                    break;

                case "Schedule":
                    this.setTitle("New " + type);

                    fab_interest.hide();

                    scheduleEvent = ScheduleEventAddingFragment.newInstance(type);
                    fragmentTransaction.add(R.id.adding_container, scheduleEvent, TAG_FRAGMENT);
                    fragmentTransaction.commit();

                    break;

                case "Birthday":
                    this.setTitle("New " + type);

                    simpleEvent = SimpleEventAddingFragment.newInstance(type);
                    fragmentTransaction.add(R.id.adding_container, simpleEvent, TAG_FRAGMENT);
                    fragmentTransaction.commit();

                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == Constants.REQUEST_CODE_INTEREST)
            {
                switch (type)
                {
                    case "ToDo":
                    case "Work Task":
                    case "Birthday":
                        simpleEvent.setInterest(data.getStringExtra("interest_title"));
                        break;

                    case "Schedule":
                        scheduleEvent.setInterest(data.getStringExtra("interest_title"));
                        scheduleEvent.setInterestText();
                        break;
                }
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error! Different result codes", Toast.LENGTH_SHORT).show();
        }
    }

    public void pick(View v)
    {
        listener.onFragmentClick(v);
    }

    public  void addSubTask(View view)
    {
        //ToDo: work on it! For simple and schedule

        switch (type)
        {
            case "ToDo":
            case "Work Task":
            case "Birthday":
                simpleEvent.onFragmentClick(view);
                break;

            case "Schedule":
                scheduleEvent.onFragmentClick(view);
                break;
        }
    }

}
