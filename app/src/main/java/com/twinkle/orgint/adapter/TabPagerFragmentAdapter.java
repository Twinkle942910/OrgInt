package com.twinkle.orgint.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.twinkle.orgint.R;
import com.twinkle.orgint.fragments.ShedulesFragment;
import com.twinkle.orgint.helpers.Constants;


public class TabPagerFragmentAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 3;

    private String tabTitles[] = new String[] { "Shedules", "Links", "Planing" };
    private Context context;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ShedulesFragment.newInstance(position + 1);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        //Adding text only
        //return tabTitles[position];

        //Adding icons only
        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
        // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        // Drawable image = context.getResources().getDrawable(imageResId[position]);

        //Adding icons only tested (work)
     /*   // Generate title based on item position
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;*/

        //Adding icons and text
      /*  // Generate title based on item position
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString(" " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;*/


        switch (position)
        {
            case Constants.TAB_ONE:
                return tabTitles[0];

            case Constants.TAB_TWO:
                return tabTitles[1];

            case Constants.TAB_THREE:
                return tabTitles[2];
        }

        return null;
    }
}
