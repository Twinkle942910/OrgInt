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
import com.twinkle.orgint.database.Birthday;
import com.twinkle.orgint.database.BirthdayDAO;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.database.ScheduleDAO;
import com.twinkle.orgint.database.SubScheduleDAO;
import com.twinkle.orgint.database.SubTaskDAO;
import com.twinkle.orgint.database.Sub_schedule;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.ToDo;
import com.twinkle.orgint.database.ToDoDAO;
import com.twinkle.orgint.database.WorkTask;
import com.twinkle.orgint.database.WorkTaskDAO;
import com.twinkle.orgint.helpers.ActivityDataCommunicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SchedulesFragment extends AbstractTabFragment implements ActivityDataCommunicator
{
    public static final String SHEDULES_PAGE = "SHEDULES_PAGE";
    public static final int LAYOUT = R.layout.fragment_shedules;

    private int page;

    private ScheduleRecycleAdapter adapter;
    private List<Schedule> schedules;

    private ScheduleDAO schedulesDB;
    private SubScheduleDAO sub_schedulesDB;

    private ToDoDAO todoListDB;
    private SubTaskDAO subTaskListDB;

    private WorkTaskDAO workTaskListDB;

    private BirthdayDAO birthdayListDB;

    private String day;

    private String[] titles;
    private String[] times;
    private String[] comments;
    private String[] importances;
    private int[] importance_values;

    private Intent scheduleAddData;
    private List<Sub_schedule> sub_schedules;

    public static SchedulesFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(SHEDULES_PAGE, page);

        SchedulesFragment fragment = new SchedulesFragment();
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
        initializeData();

                                                        //context, maybe?
        adapter = new ScheduleRecycleAdapter(schedules, getActivity());
        recyclerView.setAdapter(adapter);

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


        setToDos();
        setWorkTasks();
        setBirthdays();
    }

    public void setData()
    {
        day = scheduleAddData.getStringExtra("day");

        titles = scheduleAddData.getStringArrayExtra("title");
        times = scheduleAddData.getStringArrayExtra("time");
        comments = scheduleAddData.getStringArrayExtra("comment");
        importances = scheduleAddData.getStringArrayExtra("importance");
        importance_values = scheduleAddData.getIntArrayExtra("importance_value");

        //ToDo: Get importance values from adding Activity (ScheduleEventAddingFragment)

        setSubSchedules();
    }

    private void setToDos()
    {
        todoListDB = new ToDoDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        List<ToDo> todoList = new ArrayList<>();
        List<Sub_task> subTaskList = new ArrayList<>();

        todoList.addAll(todoListDB.getToDoList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasks(todoList, subTaskList);

        for (Schedule schedule_day: schedules)
        {
            for (ToDo todo : todoList)
            {
                if(todo.getDate().equals(schedule_day.getDate()))
                {
                    Sub_schedule sub_schedule = new Sub_schedule();

                    sub_schedule.setSchedule_ID(schedule_day.getSchedule_ID());
                    sub_schedule.setType("ToDo");
                    sub_schedule.setTask(todo.getTask());
                    sub_schedule.setTime(todo.getTime());
                    sub_schedule.setComment(todo.getComment());
                    sub_schedule.setImportance(todo.getImportance());
                    sub_schedule.setImportance_value(todo.getImportance_value());

                    schedule_day.setSub_schedule(sub_schedule);
                }
            }
        }
    }

    private void setWorkTasks()
    {
        workTaskListDB = new WorkTaskDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        List<WorkTask> workTaskList = new ArrayList<>();
        List<Sub_task> subTaskList = new ArrayList<>();

        workTaskList.addAll(workTaskListDB.getWorkTaskList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasksWorkT(workTaskList, subTaskList);

        for (Schedule schedule_day: schedules)
        {
            for (WorkTask workTask : workTaskList)
            {
                if(workTask.getDate().equals(schedule_day.getDate()))
                {
                    Sub_schedule sub_schedule = new Sub_schedule();

                    sub_schedule.setSchedule_ID(schedule_day.getSchedule_ID());
                    sub_schedule.setType("Work Task");
                    sub_schedule.setTask(workTask.getTask());
                    sub_schedule.setTime(workTask.getTime());
                    sub_schedule.setComment(workTask.getComment());
                    sub_schedule.setImportance(workTask.getImportance());
                    sub_schedule.setImportance_value(workTask.getImportance_value());

                    schedule_day.setSub_schedule(sub_schedule);
                }
            }
        }
    }

    private void setBirthdays()
    {
        birthdayListDB = new BirthdayDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        List<Birthday> birthdayList = new ArrayList<>();
        List<Sub_task> subTaskList = new ArrayList<>();

        birthdayList.addAll(birthdayListDB.getBirthdayList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasksBD(birthdayList, subTaskList);

        for (Schedule schedule_day: schedules)
        {
            for (Birthday birthday : birthdayList)
            {
                if(birthday.getDate().equals(schedule_day.getDate()))
                {
                    Sub_schedule sub_schedule = new Sub_schedule();

                    sub_schedule.setSchedule_ID(schedule_day.getSchedule_ID());
                    sub_schedule.setType("Birthday");
                    sub_schedule.setTask(birthday.getTask());
                    sub_schedule.setTime(birthday.getTime());
                    sub_schedule.setComment(birthday.getComment());
                    sub_schedule.setImportance(birthday.getImportance());
                    sub_schedule.setImportance(birthday.getImportance());
                    sub_schedule.setImportance_value(birthday.getImportance_value());

                    schedule_day.setSub_schedule(sub_schedule);
                }
            }
        }
    }

    //ToDo: one method for all of them(Casting, abstract, pattern?)
    private void setSubTasks(List<ToDo> todoList, List<Sub_task> subTaskList)
    {
        for (ToDo todo: todoList)
        {
            for (Sub_task sub_task: subTaskList)
            {
                if (sub_task.getTodo_ID() == todo.getID())
                {
                    todo.setSub_task(sub_task);
                }
            }
        }
    }

    private void setSubTasksWorkT(List<WorkTask> workTaskList, List<Sub_task> subTaskList)
    {
        for (WorkTask workTask: workTaskList)
        {
            for (Sub_task sub_task: subTaskList)
            {
                if (sub_task.getWork_task_id() == workTask.getID())
                {
                    workTask.setSub_task(sub_task);
                }
            }
        }
    }

    private void setSubTasksBD(List<Birthday> birthdayList, List<Sub_task> subTaskList)
    {
        for (Birthday birthday: birthdayList)
        {
            for (Sub_task sub_task: subTaskList)
            {
                if (sub_task.getBirthday_id() == birthday.getID())
                {
                    birthday.setSub_task(sub_task);
                }
            }
        }
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
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

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
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

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
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(2).setSub_schedule(sub_schedule);
                }

                break;

            case "Thursday":

                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(4);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(3).setSub_schedule(sub_schedule);
                }

                break;

            case "Friday":

                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(5);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(4).setSub_schedule(sub_schedule);
                }

                break;

            case "Saturday":

                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(6);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(5).setSub_schedule(sub_schedule);
                }

                break;

            case "Sunday":

                for (int i = 0; i < titles.length; i++)
                {
                    Sub_schedule sub_schedule = new Sub_schedule();
                    sub_schedule.setSchedule_ID(7);
                    sub_schedule.setType("Schedule");
                    sub_schedule.setTask(titles[i]);
                    sub_schedule.setTime(times[i]);
                    sub_schedule.setComment(comments[i]);
                    sub_schedule.setImportance(importances[i]);
                    sub_schedule.setImportance_value(importance_values[i]);

                    sub_schedulesDB.insert(sub_schedule);
                    sub_schedules.add(sub_schedule);

                    schedules.get(6).setSub_schedule(sub_schedule);
                }

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

