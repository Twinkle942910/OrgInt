package com.twinkle.orgint.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.ToDoRecycleAdapter;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends EventFragment
{
    public static final int LAYOUT = R.layout.fragment_todo;

    private ToDoRecycleAdapter adapter;
    private List<ToDo> todoList;

    public static ToDoFragment newInstance(String category, String title, String date, String time, String[] sub_tasks, String comment, String interest)
    {
        final Bundle args = new Bundle();

        args.putString(ARGUMENT_CATEGORY, category);
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_DATE, date);
        args.putString(ARGUMENT_TIME, time);
        args.putStringArray(ARGUMENT_SUB_TASKS, sub_tasks);
        args.putString(ARGUMENT_COMMENT, comment);
        args.putString(ARGUMENT_INTEREST, interest);

        final ToDoFragment fragment = new ToDoFragment();
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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.todo_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        if (recyclerView != null)
        {
            recyclerView.setLayoutManager(llm);
        }

        initializeData();

        adapter = new ToDoRecycleAdapter(todoList, getActivity());

        if (recyclerView != null)
        {
            recyclerView.setAdapter(adapter);
        }
    }

    //init mock data
    private void initializeMockData()
    {
        todoList = new ArrayList<>();

        Sub_task st1 = new Sub_task();

        st1.setContent("1. Open box");
        st1.setDone(false);

        Sub_task st2 = new Sub_task();

        st2.setContent("2. Check mistakes");
        st2.setDone(false);

        Sub_task st3 = new Sub_task();

        st3.setContent("3. Repair");
        st3.setDone(false);

        List<Sub_task> lst1  = new ArrayList<>();

        lst1.add(st1);
        lst1.add(st2);
        lst1.add(st3);

        ToDo td1 = new ToDo(lst1);

        td1.setTask("Погуляти з друзями");
        td1.setType("ToDo");
        td1.setDate("June 9,");
        td1.setTime("19:00 pm");

        td1.setComment("Lol");
        td1.setInterest("Study");

        todoList.add(td1);
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
        todoList = new ArrayList<>();

        List<Sub_task> lst1 = new ArrayList<>();

        ToDo td1 = new ToDo(lst1);

        td1.setTask(title);
        td1.setType("ToDo");
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

        td1.setInterest(interest);

        todoList.add(td1);
    }
}
