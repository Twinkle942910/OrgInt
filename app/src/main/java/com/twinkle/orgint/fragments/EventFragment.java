package com.twinkle.orgint.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public class EventFragment extends Fragment
{
    //Arguments for save state
    protected static final String ARGUMENT_CATEGORY = "category";
    protected static final String ARGUMENT_TITLE = "title";
    protected static final String ARGUMENT_DATE = "date";
    protected static final String ARGUMENT_TIME = "time";
    protected static final String ARGUMENT_SUB_TASKS = "sub_tasks";
    protected static final String ARGUMENT_COMMENT = "comment";
    protected static final String ARGUMENT_INTEREST = "interest";

    //Event data
    protected String category;
    protected String title;
    protected String date;
    protected String time;
    protected String[] sub_tasks;
    protected String comment;
    protected String interest;

    //Resources
    protected Context context;
    protected View view;


}
