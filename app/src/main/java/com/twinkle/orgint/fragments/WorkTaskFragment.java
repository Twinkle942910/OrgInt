package com.twinkle.orgint.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.WorkTaskRecycleAdapter;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.WorkTask;

import java.util.ArrayList;
import java.util.List;

public class WorkTaskFragment extends EventFragment
{
    public static final int LAYOUT = R.layout.fragment_work_tasks;

    private WorkTaskRecycleAdapter adapter;
    private List<WorkTask> workTaskList;

    public static WorkTaskFragment newInstance(String category, String title, String date, String time, String[] sub_tasks, String comment, String interest)
    {
        final Bundle args = new Bundle();

        args.putString(ARGUMENT_CATEGORY, category);
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_DATE, date);
        args.putString(ARGUMENT_TIME, time);
        args.putStringArray(ARGUMENT_SUB_TASKS, sub_tasks);
        args.putString(ARGUMENT_COMMENT, comment);
        args.putString(ARGUMENT_INTEREST, interest);

        final WorkTaskFragment fragment = new WorkTaskFragment();
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

        return view;
    }

    private void initToDoList()
    {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.work_tasks_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        if (recyclerView != null)
        {
            recyclerView.setLayoutManager(llm);
        }

        initializeData();

        adapter = new WorkTaskRecycleAdapter(workTaskList, getActivity());

        if (recyclerView != null)
        {
            recyclerView.setAdapter(adapter);
        }
    }

    //init mock data
    private void initializeMockData()
    {
        workTaskList = new ArrayList<>();

        Sub_task st1 = new Sub_task();

        st1.setContent("1. Create tables");
        st1.setDone(false);

        Sub_task st2 = new Sub_task();

        st2.setContent("2. Make queries");
        st2.setDone(false);

        Sub_task st3 = new Sub_task();

        st3.setContent("3. Do crud methods");
        st3.setDone(false);

        List<Sub_task> lst1  = new ArrayList<>();

        lst1.add(st1);
        lst1.add(st2);
        lst1.add(st3);

        WorkTask workTask = new WorkTask(lst1);

        workTask.setTask("Make database");
        workTask.setType("Work Task");
        workTask.setDate("June 16,");
        workTask.setTime("10:20 am");

        workTask.setComment("check onCreate()");
        workTask.setInterest("Programing");

        workTaskList.add(workTask);
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

    private void addToDoData()
    {
        workTaskList = new ArrayList<>();

        List<Sub_task> lst1 = new ArrayList<>();

        WorkTask td1 = new WorkTask(lst1);

        td1.setTask(title);
        td1.setType("Work Task");
        td1.setDate(date);
        td1.setTime(time);

        td1.setComment(comment);

        for (String sub_task1 : sub_tasks)
        {
            Sub_task sub_task = new Sub_task();

            sub_task.setContent(sub_task1);
            sub_task.setDone(false);

            td1.getSub_tasks().add(sub_task);
        }

        workTaskList.add(td1);
    }
}
