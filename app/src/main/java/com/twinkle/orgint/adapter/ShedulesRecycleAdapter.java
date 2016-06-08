package com.twinkle.orgint.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Schedule_Tab;
import com.twinkle.orgint.pages.InterestsActivity;
import com.twinkle.orgint.pages.SheduleActivity;
import com.twinkle.orgint.pages.ToDoActivity;

import java.util.List;
import java.util.Locale;

public class ShedulesRecycleAdapter extends RecyclerView.Adapter<ShedulesRecycleAdapter.SchedulesViewHolder>
{
    List<Schedule_Tab> schedules;
    Context context;

    public ShedulesRecycleAdapter(List<Schedule_Tab> schedules, Context context)
    {
        this.schedules = schedules;
        this.context = context;
    }

    @Override
    public SchedulesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shedules_card, parent, false);
        SchedulesViewHolder svh = new SchedulesViewHolder(view);
        svh.setContext(context);
        return svh;
    }

    @Override
    public void onBindViewHolder(SchedulesViewHolder holder, int position)
    {
        holder.cardTitle.setText(schedules.get(position).getTitle());
       holder.cardImage.setBackgroundResource(schedules.get(position).getImage());

       holder.cardUIcount.setText(String.format(Locale.US, "%d", schedules.get(position).getUrgent_important_count()));
       holder.cardNUIcount.setText(String.format(Locale.US, "%d", schedules.get(position).getNot_urgent_important_count()));
       holder.cardUNIcount.setText(String.format(Locale.US, "%d", schedules.get(position).getUrgent_not_important_count()));
       holder.cardNUNIcount.setText(String.format(Locale.US, "%d", schedules.get(position).getNot_urgent_not_important_count()));


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

    public static class SchedulesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;
        TextView cardTitle;
        ImageView cardImage;

        TextView cardUIcount;
        TextView cardNUIcount;
        TextView cardUNIcount;
        TextView cardNUNIcount;

        Context context;

        public SchedulesViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            cardTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            cardImage = (ImageView)itemView.findViewById(R.id.cardImage);

            cardUIcount = (TextView)itemView.findViewById(R.id.warning_count);
            cardNUIcount = (TextView)itemView.findViewById(R.id.schedule_count);
            cardUNIcount = (TextView)itemView.findViewById(R.id.delegate_count);
            cardNUNIcount = (TextView)itemView.findViewById(R.id.delete_count);
        }

        public void setContext(Context context)
        {
            this.context = context;
        }

        @Override
        public void onClick(View v)
        {
            Intent intent;

            if("Schedule".equals(cardTitle.getText().toString()))
            {
                intent =  new Intent(context, SheduleActivity.class);
                context.startActivity(intent);
            }
            else if("ToDo".equals(cardTitle.getText().toString()))
            {
                intent = new Intent(context, ToDoActivity.class);
                intent.putExtra("calling", "From Main Activity");
                context.startActivity(intent);
            }
            else if("Work Tasks".equals(cardTitle.getText().toString()))
            {
               //ToDo: Add listener
            } else if("Birthdays".equals(cardTitle.getText().toString()))
            {
                //ToDo: Add listener
            }
        }
    }
}
