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
import com.twinkle.orgint.adapter.WorkTaskRecycleAdapter;
import com.twinkle.orgint.database.SubTaskDAO;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.WorkTask;
import com.twinkle.orgint.database.WorkTaskDAO;
import com.twinkle.orgint.pages.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkTaskFragment extends EventFragment
{
    public static final int LAYOUT = R.layout.fragment_work_tasks;

    private WorkTaskRecycleAdapter adapter;

    private List<WorkTask> workTaskList;
    private List<Sub_task> subTaskList;

    private WorkTaskDAO workTaskListDB;
    private SubTaskDAO subTaskListDB;

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

        initWorkTaskList();

        getActivity().setTitle(getActivity().getIntent().getStringExtra("type"));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorWorkTaskTypeDark));


            ((EventActivity)getActivity()).getToolbar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorWorkTaskType)));
        }

        return view;
    }

    private void initWorkTaskList()
    {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.work_tasks_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        if (recyclerView != null)
        {
            recyclerView.setLayoutManager(llm);
        }

        initLists();
        initializeData();

        adapter = new WorkTaskRecycleAdapter(workTaskList, getActivity());

        if (recyclerView != null)
        {
            recyclerView.setAdapter(adapter);
        }
    }

    private void initLists()
    {
        workTaskListDB = new WorkTaskDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        workTaskList =  new ArrayList<>();
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
        workTaskList.addAll(workTaskListDB.getWorkTaskList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasks();
    }

    private void setSubTasks()
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

    private void addToDoData()
    {
        WorkTask workTask = new WorkTask();

        workTask.setTask(title);
        workTask.setType("Work Task");
        workTask.setDate(date);
        workTask.setTime(time);

        workTask.setComment(comment);
        workTask.setInterest(interest);

        workTaskListDB.insert(workTask);
        workTaskList.addAll(workTaskListDB.getWorkTaskList());

        for (String sub_task1 : sub_tasks)
        {
            Sub_task sub_task = new Sub_task();

            sub_task.setWork_task_id(workTaskList.get(workTaskList.size() - 1).getID());
            sub_task.setContent(sub_task1);
            sub_task.setDone(false);

           // workTask.getSub_tasks().add(sub_task);

            subTaskListDB.insert(sub_task);
        }

        subTaskList.addAll(subTaskListDB.getSubTaskList());
        setSubTasks();
    }
}
