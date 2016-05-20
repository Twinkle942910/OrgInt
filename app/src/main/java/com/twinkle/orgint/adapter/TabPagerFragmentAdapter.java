package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twinkle.orgint.fragments.AbstractTabFragment;
import com.twinkle.orgint.fragments.LinksFragment;
import com.twinkle.orgint.fragments.PlaningFragment;
import com.twinkle.orgint.fragments.ShedulesFragment;
import com.twinkle.orgint.helpers.Constants;

import java.util.ArrayList;
import java.util.List;



public class TabPagerFragmentAdapter extends FragmentPagerAdapter
{
    private List<AbstractTabFragment> tabs;
    private Context context;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;

        initTansMap(context);
    }

    private void initTansMap(Context context)
    {
        tabs = new ArrayList<>();

        tabs.add(ShedulesFragment.newInstance(Constants.TAB_ONE, context));
        tabs.add(LinksFragment.newInstance(Constants.TAB_TWO, context));
        tabs.add(PlaningFragment.newInstance(Constants.TAB_THREE, context));

    }

    @Override
    public Fragment getItem(int position)
    {
        return tabs.get(position);
    }

    @Override
    public int getCount()
    {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabs.get(position).getTitle();
    }
}
