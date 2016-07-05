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
import com.twinkle.orgint.adapter.ToDoRecycleAdapter;
import com.twinkle.orgint.database.SubTaskDAO;
import com.twinkle.orgint.database.Sub_task;
import com.twinkle.orgint.database.ToDo;
import com.twinkle.orgint.database.ToDoDAO;
import com.twinkle.orgint.pages.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends EventFragment
{
    public static final int LAYOUT = R.layout.fragment_todo;

    private ToDoRecycleAdapter adapter;
    private List<ToDo> todoList;
    private List<Sub_task> subTaskList;

    private ToDoDAO todoListDB;
    private SubTaskDAO subTaskListDB;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorToDoTypeDark));


            ((EventActivity)getActivity()).getToolbar().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorToDoType)));
        }

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

        initLists();
        initializeData();

        adapter = new ToDoRecycleAdapter(todoList, getActivity());

        if (recyclerView != null)
        {
            recyclerView.setAdapter(adapter);
        }
    }

    private void initLists()
    {
        todoListDB = new ToDoDAO(getActivity());
        subTaskListDB = new SubTaskDAO(getActivity());

        todoList =  new ArrayList<>();
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

    private void initializeMockData()
    {
        todoList.addAll(todoListDB.getToDoList());
        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasks();
    }

    private void setSubTasks()
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

    private void addToDoData()
    {
       // ToDo toDo = new ToDo(sub_task_list);
        ToDo toDo = new ToDo();

        toDo.setTask(title);
        toDo.setType("ToDo");
        toDo.setDate(date);
        toDo.setTime(time);

        toDo.setComment(comment);
        toDo.setInterest(interest);

        todoListDB.insert(toDo);
        todoList.addAll(todoListDB.getToDoList());

        for (String sub_task1 : this.sub_tasks)
        {
            Sub_task sub_task = new Sub_task();

            sub_task.setTodo_ID(todoList.get(todoList.size() - 1).getID());
            sub_task.setContent(sub_task1);
            sub_task.setDone(false);

            //toDo.setSub_task(sub_task);

            subTaskListDB.insert(sub_task);
        }


        subTaskList.addAll(subTaskListDB.getSubTaskList());

        setSubTasks();
    }
}
