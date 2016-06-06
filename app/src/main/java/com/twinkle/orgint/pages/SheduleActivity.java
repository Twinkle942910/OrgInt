package com.twinkle.orgint.pages;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.InterestsRecycleAdapter;
import com.twinkle.orgint.adapter.ScheduleRecycleAdapter;
import com.twinkle.orgint.database.Interest;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.database.Sub_schedule;

import java.util.ArrayList;
import java.util.List;

public class SheduleActivity extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_shedule;

    private Toolbar toolbar;

    private ScheduleRecycleAdapter adapter;
    private List<Schedule> schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();
        initScheduleList();
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

    private void initScheduleList()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.schedule_list);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(llm);

        initializeData();

        adapter = new ScheduleRecycleAdapter(schedules, this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeData()
    {
       schedules = new ArrayList<>();

        Sub_schedule ss = new Sub_schedule();

        ss.setTask("Lab work aaaa");
        ss.setTime("19:00 am");
        ss.setType("Schedule1");

        Sub_schedule ss1 = new Sub_schedule();

        ss1.setTask("Buy stuff1");
        ss1.setTime("10:23 am");
        ss1.setType("ToDo");

        Sub_schedule ss3 = new Sub_schedule();

        ss3.setTask("Finish DB1");
        ss3.setTime("13:30 pm");
        ss3.setType("Work Task");

        Sub_schedule ss4 = new Sub_schedule();

        ss4.setTask("Congradulate someone");
        ss4.setTime("20:00 pm");
        ss4.setType("Birthday");

        List<Sub_schedule> ssList = new ArrayList<>();

        ssList.add(ss);
        ssList.add(ss1);
        ssList.add(ss3);
        ssList.add(ss4);

        Schedule s1 = new Schedule(ssList);
        s1.setDay("LolDay");
        s1.setType("Schedule3");
        s1.setDate("June 9, 2016");

        Sub_schedule ssn1 = new Sub_schedule();

        ssn1.setTask("Math");
        ssn1.setTime("8:30 am");
        ssn1.setType("Schedule");

        Sub_schedule ssn2 = new Sub_schedule();

        ssn2.setTask("Check vk");
        ssn2.setTime("10:20 am");
        ssn2.setType("ToDo");

        Sub_schedule ssn3 = new Sub_schedule();

        ssn3.setTask("Edit essay");
        ssn3.setTime("15:00 pm");
        ssn3.setType("Work Task");

        Sub_schedule ssn4 = new Sub_schedule();

        ssn4.setTask("Taras b-day");
        ssn4.setTime("21:00 pm");
        ssn4.setType("Birthday");

        List<Sub_schedule> ssnList = new ArrayList<>();

        ssnList.add(ssn1);
        ssnList.add(ssn2);
        ssnList.add(ssn3);
        ssnList.add(ssn4);

        Schedule s2 = new Schedule(ssnList);
        s2.setDay("SuckDay");
        s2.setType("Schedule");
        s2.setDate("June 07, 2016");

        schedules.add(s1);
        schedules.add(s2);
    }
}
