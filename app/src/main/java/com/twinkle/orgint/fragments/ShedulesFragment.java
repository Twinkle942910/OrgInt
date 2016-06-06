package com.twinkle.orgint.fragments;

import android.content.Context;
import android.os.Bundle;
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

        ShedulesRecycleAdapter adapter = new  ShedulesRecycleAdapter(schedules, getContext());
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
        Schedule_TabDAO dao = new Schedule_TabDAO(getContext());

        schedules = new ArrayList<>();

        Schedule_Tab schedule_tab_birthday = new Schedule_Tab();

     /*   schedule_tab_birthday.setSchedule_tab_ID(4);
        schedule_tab_birthday.setTitle("Birthdays");
        schedule_tab_birthday.setImage(R.drawable.birthdays);
        schedule_tab_birthday.setUrgent_important_count(3);
        schedule_tab_birthday.setNot_urgent_important_count(3);
        schedule_tab_birthday.setUrgent_not_important_count(3);
        schedule_tab_birthday.setNot_urgent_not_important_count(3);

        dao.update(schedule_tab_birthday);*/

        schedules = dao.getSchedule_TabList();
    }




}

