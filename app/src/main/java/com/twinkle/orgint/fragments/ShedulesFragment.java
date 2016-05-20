package com.twinkle.orgint.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twinkle.orgint.R;

public class ShedulesFragment extends Fragment
{
    public static final String SHEDULES_PAGE = "SHEDULES_PAGE";
    public static final int LAYOUT = R.layout.fragment_shedules;

    private int page;

    public static ShedulesFragment newInstance(int page)
    {
        Bundle args = new Bundle();
        args.putInt(SHEDULES_PAGE, page);
        ShedulesFragment fragment = new ShedulesFragment();
        fragment.setArguments(args);
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
        View view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

}
