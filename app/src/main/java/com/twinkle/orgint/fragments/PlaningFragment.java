package com.twinkle.orgint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;

public class PlaningFragment extends AbstractTabFragment
{

    public static final String PLANING_PAGE = "PLANING_PAGE";
    public static final int LAYOUT = R.layout.fragment_planing;

    private int page;

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
        return view;
    }

    public void addPlan()
    {
        Snackbar.make(view, "Plan", Snackbar.LENGTH_LONG)
                .setAction("Action 3", null).show();
    }
}
