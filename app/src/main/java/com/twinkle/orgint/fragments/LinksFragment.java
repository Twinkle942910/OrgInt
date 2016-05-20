package com.twinkle.orgint.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twinkle.orgint.R;

public class LinksFragment extends AbstractTabFragment
{
    public static final String LINKS_PAGE = "LINKS_PAGE";
    public static final int LAYOUT = R.layout.fragment_links;

    private int page;

    public static AbstractTabFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(LINKS_PAGE, page);

        LinksFragment fragment = new LinksFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_links_name));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(LINKS_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void addLink()
    {
        Snackbar.make(view, "Link", Snackbar.LENGTH_LONG)
                .setAction("Action 2", null).show();
    }
}
