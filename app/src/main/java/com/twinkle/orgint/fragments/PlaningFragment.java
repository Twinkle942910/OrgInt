package com.twinkle.orgint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.PlanningRecycleAdapter;
import com.twinkle.orgint.adapter.ScheduleRecycleAdapter;
import com.twinkle.orgint.database.Planning;

import java.util.ArrayList;
import java.util.List;

public class PlaningFragment extends AbstractTabFragment
{

    public static final String PLANING_PAGE = "PLANING_PAGE";
    public static final int LAYOUT = R.layout.fragment_planing;

    private int page;

    private PlanningRecycleAdapter adapter;

    private List<Planning> plannings;

    public PlaningFragment() {
    }

    public static AbstractTabFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(PLANING_PAGE, page);

        PlaningFragment fragment = new PlaningFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_planing_name));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(PLANING_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        initPlanningList();

        return view;
    }

    private void initPlanningList()
    {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.planning_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
       // LinearLayoutManager gridLayoutManager =  new LinearLayoutManager(context);
        recyclerView.setLayoutManager(gridLayoutManager);

        initListData();

        adapter = new PlanningRecycleAdapter(plannings, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void initListData()
    {
        plannings = new ArrayList<>();

        Planning urgent_important = new Planning();
        urgent_important.setCategory("Urgent \nImportant");
        urgent_important.setImage(R.drawable.ic_ui);

        urgent_important.setSchedules_count(1);
        urgent_important.setTodo_count(3);
        urgent_important.setWork_tasks_count(2);
        urgent_important.setBirthdays_count(5);


        Planning not_urgent_important = new Planning();
        not_urgent_important.setCategory("Not Urgent \nImportant");
        not_urgent_important.setImage(R.drawable.ic_nui);

        not_urgent_important.setSchedules_count(2);
        not_urgent_important.setTodo_count(4);
        not_urgent_important.setWork_tasks_count(1);
        not_urgent_important.setBirthdays_count(3);


        Planning urgent_not_important = new Planning();
        urgent_not_important.setCategory("Urgent \nNot Important");
        urgent_not_important.setImage(R.drawable.ic_uni);

        urgent_not_important.setSchedules_count(4);
        urgent_not_important.setTodo_count(3);
        urgent_not_important.setWork_tasks_count(2);
        urgent_not_important.setBirthdays_count(1);


        Planning not_urgent_not_important = new Planning();
        not_urgent_not_important.setCategory("Not Urgent \nNot Important");
        not_urgent_not_important.setImage(R.drawable.ic_nuni);

        not_urgent_not_important.setSchedules_count(1);
        not_urgent_not_important.setTodo_count(2);
        not_urgent_not_important.setWork_tasks_count(3);
        not_urgent_not_important.setBirthdays_count(4);

        plannings.add(urgent_important);
        plannings.add(not_urgent_important);
        plannings.add(urgent_not_important);
        plannings.add(not_urgent_not_important);
    }

    public void addPlan()
    {
        Snackbar.make(view, "Plan", Snackbar.LENGTH_LONG)
                .setAction("Action 3", null).show();
    }
}
