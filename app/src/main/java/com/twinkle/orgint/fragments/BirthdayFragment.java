package com.twinkle.orgint.fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.BirthdayRecycleAdapter;
import com.twinkle.orgint.database.Birthday;
import com.twinkle.orgint.database.BirthdayDAO;
import com.twinkle.orgint.database.SubTaskDAO;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.WorkTask;
import com.twinkle.orgint.database.WorkTaskDAO;
import com.twinkle.orgint.pages.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class BirthdayFragment extends EventFragment
{
    public static final int LAYOUT = R.layout.fragment_birthday;

    private BirthdayRecycleAdapter adapter;

    private List<Birthday> birthdayList;
    private List<Sub_task> subTaskList;

    private BirthdayDAO birthdayListDB;
    private SubTaskDAO subTaskListDB;

    public static BirthdayFragment newInstance(String category, String title, String date, String time, String[] sub_tasks, String comment, String interest)
    {
        final Bundle args = new Bundle();

        args.putString(ARGUMENT_CATEGORY, category);
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_DATE, date);
        args.putString(ARGUMENT_TIME, time);
        args.putStringArray(ARGUMENT_SUB_TASKS, sub_tasks);
        args.putString(ARGUMENT_COMMENT, comment);
        args.putString(ARGUMENT_INTEREST, interest);

        final BirthdayFragment fragment = new BirthdayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        final Bundle args = getArguments();

        category = args.getString(ARGUMENT_CATEGORY);
        title = args.getString(ARGUMENT_TITLE);
        date = args.getString(ARGUMENT_DATE);
        time = args.getString(ARGUMENT_TIME);
        sub_tasks = args.getStringArray(ARGUMENT_SUB_TASKS);
        comment = args.getString(ARGUMENT_COMMENT);
        interest = args.getString(ARGUMENT_INTEREST);

        initToDoList();

        getActivity().setTitle(getActivity().getIntent().getStringExtra("type"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorBirthdayTypeDark));


            ((EventActivity)getActivity()).getToolbar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorBirthdayType)));
        }


        return view;
    }

    private void initToDoList()
    {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.birthday_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        if (recyclerView != null)
        {
            recyclerView.setLayoutManager(llm);
        }

        initLists();
        initializeData();

        adapter = new BirthdayRecycleAdapter(birthdayList, getActivity());

        if (recyclerView != null)
        {
            recyclerView.setAdapter(adapter);
        }
    }

    private void initLists()
    {
        birthdayListDB = new BirthdayDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        birthdayList =  new ArrayList<>();
        subTaskList =  new ArrayList<>();
    }

    private void initializeData()
    {
        Intent callAct = getActivity().getIntent();
        String called_from = callAct.getStringExtra("calling");

        if ("From Adding Activity".equals(called_from))
        {
            addToDoData();
        }
        else if ("From Main Activity".equals(called_from))
        {
            initializeMockData();
        }
    }

    //init mock data
    private void initializeMockData()
    {
        birthdayList.addAll(birthdayListDB.getBirthdayList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasks();
    }

    private void setSubTasks()
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

    private void addToDoData()
    {
        Birthday birthday = new Birthday();

        birthday.setTask(title);
        birthday.setType("Birthday");
        birthday.setDate(date);
        birthday.setTime(time);

        birthday.setComment(comment);
        birthday.setInterest(interest);

        birthdayListDB.insert(birthday);
        birthdayList.addAll(birthdayListDB.getBirthdayList());

        for (String sub_task1 : sub_tasks)
        {
            Sub_task sub_task = new Sub_task();

            sub_task.setBirthday_id(birthdayList.get(birthdayList.size() - 1).getID());
            sub_task.setContent(sub_task1);
            sub_task.setDone(false);

           // birthday.getSub_tasks().add(sub_task);

            subTaskListDB.insert(sub_task);
        }

        subTaskList.addAll(subTaskListDB.getSubTaskList());
        setSubTasks();
    }
}
