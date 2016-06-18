package com.twinkle.orgint.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.ScheduleRecycleAdapter;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.database.Sub_schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShedulesFragment extends AbstractTabFragment
{
    public static final String SHEDULES_PAGE = "SHEDULES_PAGE";
    public static final int LAYOUT = R.layout.fragment_shedules;

    private int page;

    private ScheduleRecycleAdapter adapter;
    private List<Schedule> schedules;

    private String day;

    private String[] titles;
    private String[] times;
    private String[] comments;
    private String[] interests;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.schedule_list);
        LinearLayoutManager llm = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(llm);

        initializeData();

        adapter = new ScheduleRecycleAdapter(schedules, context);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void addShedule()
    {
        /*Snackbar.make(view, "Shedule", Snackbar.LENGTH_LONG)
                .setAction("Action 1", null).show();*/

    /*    Intent intent = new Intent(getContext(), AddingActivity.class);
        startActivity(intent);*/
    }

    private void initializeData()
    {
        schedules = new ArrayList<>();

        // Get Current Date
        final Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);
        int  mMonth = c.get(Calendar.MONTH);
        int mDayoM = c.get(Calendar.DAY_OF_MONTH);

        int day = c.get(Calendar.DAY_OF_WEEK);

        String myFormatDate = "LLLL d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);

        int dayMonday = 0;

        switch (day)
        {
            // Current day is Monday
            case Calendar.MONDAY:
                dayMonday = mDayoM;
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

        c.set(Calendar.YEAR, mYear);
        c.set(Calendar.MONTH, mMonth);

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

            schedules.add(scheduleDay);
        }

        dataFromAdding();

        Intent addingData = getActivity().getIntent();

        String callingFrom = addingData.getStringExtra("calling");

        if ("From Adding Activity".equals(callingFrom))
        {
            switch (this.day) {
                case "Monday":

                    List<Sub_schedule> sub_schedules = new ArrayList<>();

                    for (int i = 0; i < titles.length; i++) {
                        Sub_schedule ss = new Sub_schedule();
                        ss.setType("Schedule");
                        ss.setTask(titles[i]);
                        ss.setTime(times[i]);

                        //ToDo: Add later!
                        // ss.setComment(comments[i]);
                        //ss.setInteres(interests[i]);

                        sub_schedules.add(ss);
                    }

                    schedules.get(0).setSub_schedules(sub_schedules);
                    break;

                case "Tuesday":

                    break;

                case "Wednesday":

                    break;

                case "Thursday":

                    break;

                case "Friday":

                    break;

                case "Saturday":

                    break;

                case "Sunday":

                    break;
            }
        }
    }

    private void dataFromAdding()
    {
        Intent addingData = getActivity().getIntent();

        day = addingData.getStringExtra("day");

        titles = addingData.getStringArrayExtra("title");
        times = addingData.getStringArrayExtra("time");
        comments = addingData.getStringArrayExtra("comment");
        interests = addingData.getStringArrayExtra("interest");

    }



}

