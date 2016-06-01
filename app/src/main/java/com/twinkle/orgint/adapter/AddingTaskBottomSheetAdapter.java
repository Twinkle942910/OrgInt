package com.twinkle.orgint.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.twinkle.orgint.R;

public class AddingTaskBottomSheetAdapter extends ArrayAdapter<Integer>
{
    private Activity activity;

    public AddingTaskBottomSheetAdapter(Activity context, int resource, Integer[] objects)
    {
        super(context, resource, objects);
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
      ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(R.layout.adding_item, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.image.setImageResource(getItem(position));

        return row;
    }

    private static class ViewHolder
    {
        ImageView image;
    }

}
