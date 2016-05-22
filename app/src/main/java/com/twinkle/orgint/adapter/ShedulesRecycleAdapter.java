package com.twinkle.orgint.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.TestCardData;

import java.util.List;

public class ShedulesRecycleAdapter extends RecyclerView.Adapter<ShedulesRecycleAdapter.SchedulesViewHolder>
{
    List<TestCardData> schedules;

    public ShedulesRecycleAdapter(List<TestCardData> schedules)
    {
        this.schedules = schedules;
    }

    @Override
    public SchedulesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shedules_card, parent, false);
        SchedulesViewHolder svh = new SchedulesViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(SchedulesViewHolder holder, int position)
    {
        holder.cardTitle.setText(schedules.get(position).getTitle());
       holder.cardImage.setImageResource(schedules.get(position).getImage());
    }

    @Override
    public int getItemCount()
    {
        return schedules.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class SchedulesViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView cardTitle;
        ImageView cardImage;

        public SchedulesViewHolder(View itemView)
        {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            cardTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            cardImage = (ImageView)itemView.findViewById(R.id.cardImage);
        }
    }
}
