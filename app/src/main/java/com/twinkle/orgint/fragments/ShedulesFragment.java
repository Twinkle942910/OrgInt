package com.twinkle.orgint.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.MainActivity;
import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.ScheduleRecycleAdapter;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.database.Sub_schedule;
import com.twinkle.orgint.helpers.ActivityDataCommunicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShedulesFragment extends AbstractTabFragment implements ActivityDataCommunicator
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

    private Intent scheduleAddData;
    private boolean isDataBaseData = true;
    private List<Sub_schedule> sub_schedules;

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
    public void onAttach(Context context)
    {
        super.onAttach(context);
        context = getActivity();
        ((MainActivity)context).fragmentCommunicator = this;
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

        RecyclerView  recyclerView = (RecyclerView) view.findViewById(R.id.schedule_list);
        LinearLayoutManager llm = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(llm);

        initWeek();

        sub_schedules = new ArrayList<>();

        if (isDataBaseData)
        {
            initializeData();
        }
                                                        //context, maybe?
        adapter = new ScheduleRecycleAdapter(schedules, getActivity());
        recyclerView.setAdapter(adapter);

        isDataForAdapter(isDataBaseData);

        return view;
    }

    private void isDataForAdapter(boolean isData)
    {
        ((MainActivity)getActivity()).adapterCommunicator.isAddingSubSchedule(isData);
    }

    public void addShedule()
    {
        /*Snackbar.make(view, "Shedule", Snackbar.LENGTH_LONG)
                .setAction("Action 1", null).show();*/

    /*    Intent intent = new Intent(getContext(), AddingActivity.class);
        startActivity(intent);*/
    }

    private void initWeek()
    {
        schedules = new ArrayList<>();

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

        setDays(calendar, dateFormat, dayMonday);
    }

    private void setDays(Calendar c, SimpleDateFormat sdf, int dayMonday)
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

            schedules.add(scheduleDay);
        }
    }

    private void initializeData()
    {
        sub_schedules = new ArrayList<>();


            Sub_schedule ss = new Sub_schedule();
            ss.setType("Schedule");
            ss.setTask("Lab ");
            ss.setTime("13:01");

        Sub_schedule ss1 = new Sub_schedule();
        ss1.setType("Schedule");
        ss1.setTask("DB ");
        ss1.setTime("14:15");

        Sub_schedule ss2 = new Sub_schedule();
        ss2.setType("Schedule");
        ss2.setTask("System Analysis ");
        ss2.setTime("15:50");

            sub_schedules.add(ss);
            sub_schedules.add(ss1);
            sub_schedules.add(ss2);

        schedules.get(0).setSub_schedules(sub_schedules);

       // ((MainActivity)getActivity()).setUpdatePermission(true);
        //((MainActivity)getActivity()).adapterCommunicator.isAddingSubSchedule(true);
    }

    public void setData()
    {
        day = scheduleAddData.getStringExtra("day");

        titles = scheduleAddData.getStringArrayExtra("title");
        times = scheduleAddData.getStringArrayExtra("time");
        comments = scheduleAddData.getStringArrayExtra("comment");
        interests = scheduleAddData.getStringArrayExtra("interest");

        setSubSchedules();
    }


    private void setSubSchedules()
    {
        switch (this.day)
        {
            case "Monday":

                for (int i = 0; i < titles.length; i++)
                {
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

    @Override
    public void passDataToFragment(Intent data)
    {
        scheduleAddData = data;
        setData();
    }
}

