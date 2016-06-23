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
import com.twinkle.orgint.database.ScheduleDAO;
import com.twinkle.orgint.database.SubScheduleDAO;
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

    private ScheduleDAO schedulesDB;
    private SubScheduleDAO sub_schedulesDB;

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

        //sub_schedules = new ArrayList<>();

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

    private void initWeek()
    {
        schedulesDB = new ScheduleDAO(context);

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

            scheduleDay.setSchedule_ID(i + 1);
            scheduleDay.setDay(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
            scheduleDay.setType("Schedule");
            scheduleDay.setDate(sdf.format(c.getTime()));

            schedulesDB.update(scheduleDay);
        }
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

    private void initializeData()
    {
        sub_schedulesDB = new SubScheduleDAO(context);

       // ((MainActivity)getActivity()).setUpdatePermission(true);
        //((MainActivity)getActivity()).adapterCommunicator.isAddingSubSchedule(true);

        schedules = schedulesDB.getScheduleList();
        sub_schedules = sub_schedulesDB.getSubScheduleList();

        for (Sub_schedule sub_schedule : sub_schedules)
        {
            int schedule_id =  sub_schedule.getSchedule_ID();

            switch (schedule_id)
            {
                case 1:
                    schedules.get(0).setSub_schedule(sub_schedule);
                    break;

                case 2:
                    schedules.get(1).setSub_schedule(sub_schedule);
                    break;

                case 3:
                    schedules.get(2).setSub_schedule(sub_schedule);
                    break;

                case 4:
                    schedules.get(3).setSub_schedule(sub_schedule);
                    break;

                case 5:
                    schedules.get(4).setSub_schedule(sub_schedule);
                    break;

                case 6:
                    schedules.get(5).setSub_schedule(sub_schedule);
                    break;

                case 7:
                    schedules.get(6).setSub_schedule(sub_schedule);
                    break;

            }
        }
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
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(1);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setInterest(interests[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(0).setSub_schedule(sub_schedule);
                }

                break;

            case "Tuesday":
                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(2);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setInterest(interests[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(1).setSub_schedule(sub_schedule);
                }

                break;

            case "Wednesday":
                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(3);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setInterest(interests[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(2).setSub_schedule(sub_schedule);
                }

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

