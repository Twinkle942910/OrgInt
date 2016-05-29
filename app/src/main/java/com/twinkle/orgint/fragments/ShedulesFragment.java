package com.twinkle.orgint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.ShedulesRecycleAdapter;
import com.twinkle.orgint.database.Schedule_Tab;
import com.twinkle.orgint.database.Schedule_TabDAO;

import java.util.ArrayList;
import java.util.List;

public class ShedulesFragment extends AbstractTabFragment
{
    public static final String SHEDULES_PAGE = "SHEDULES_PAGE";
    public static final int LAYOUT = R.layout.fragment_shedules;

    private int page;

    private List<Schedule_Tab> schedules;

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
        Schedule_TabDAO dao = new Schedule_TabDAO(getContext());

        Schedule_Tab schedules_tab_shedules = new Schedule_Tab();
        Schedule_Tab schedules_tab_todo = new Schedule_Tab();
        Schedule_Tab schedules_tab_work_tasks = new Schedule_Tab();
        Schedule_Tab schedules_tab_birthdays = new Schedule_Tab();

        schedules = new ArrayList<>();

        schedules_tab_shedules.setTitle("Schedule");
        schedules_tab_shedules.setImage(R.drawable.schedules);
        schedules_tab_shedules.setUrgent_important_count(3);
        schedules_tab_shedules.setNot_urgent_important_count(2);
        schedules_tab_shedules.setUrgent_not_important_count(1);
        schedules_tab_shedules.setNot_urgent_not_important_count(1);

        schedules_tab_todo.setTitle("ToDo");
        schedules_tab_todo.setImage(R.drawable.todo);
        schedules_tab_todo.setUrgent_important_count(3);
        schedules_tab_todo.setNot_urgent_important_count(3);
        schedules_tab_todo.setUrgent_not_important_count(2);
        schedules_tab_todo.setNot_urgent_not_important_count(1);

        schedules_tab_work_tasks.setTitle("Work_Tasks");
        schedules_tab_work_tasks.setImage(R.drawable.work_t);
        schedules_tab_work_tasks.setUrgent_important_count(1);
        schedules_tab_work_tasks.setNot_urgent_important_count(3);
        schedules_tab_work_tasks.setUrgent_not_important_count(2);
        schedules_tab_work_tasks.setNot_urgent_not_important_count(1);

        schedules_tab_birthdays.setTitle("Birthdays");
        schedules_tab_birthdays.setImage(R.drawable.birthdays);
        schedules_tab_birthdays.setUrgent_important_count(1);
        schedules_tab_birthdays.setNot_urgent_important_count(1);
        schedules_tab_birthdays.setUrgent_not_important_count(3);
        schedules_tab_birthdays.setNot_urgent_not_important_count(1);

     /*  dao.insert(schedules_tab_shedules);
       dao.insert(schedules_tab_todo);
       dao.insert(schedules_tab_work_tasks);
        dao.insert(schedules_tab_birthdays);*/

        schedules = dao.getSchedule_TabList();
    }




}

