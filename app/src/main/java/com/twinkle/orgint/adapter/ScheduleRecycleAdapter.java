package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.pages.SheduleActivity;

import java.util.List;

public class ScheduleRecycleAdapter extends RecyclerView.Adapter<ScheduleRecycleAdapter. ScheduleViewHolder>
{
    List<Schedule> schedules;
    Context context;

    public ScheduleRecycleAdapter(List<Schedule> schedules, Context context)
    {
        this.schedules = schedules;
        this.context = context;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shedule_card, parent, false);
        ScheduleViewHolder svh = new  ScheduleViewHolder(view);
        svh.setActivity((SheduleActivity)context);
        return svh;
    }

    @Override
    public void onBindViewHolder( ScheduleViewHolder holder, int position)
    {
        holder.schedule_day.setText(schedules.get(position).getDay());
        holder.schedule_type.setText(schedules.get(position).getType());
        holder.schedule_date.setText(schedules.get(position).getDate());

        for (int i=0; i < schedules.get(position).getSubSchedulesCount(); i++)
        {
            holder.addSubSchedule();

            View thisChild = holder.subScheduleLayout.getChildAt(i);

            TextView child_sub_schedule_time = (TextView) thisChild.findViewById(R.id.schedule_card_sub_task_time);
            TextView child_sub_schedule_task = (TextView) thisChild.findViewById(R.id.schedule_card_sub_task_task);
            ImageView child_sub_schedule_image = (ImageView) thisChild.findViewById(R.id.sub_schedule_icon);


            child_sub_schedule_time.setText(schedules.get(position).getSub_schedule(i).getTime());
            child_sub_schedule_task.setText(schedules.get(position).getSub_schedule(i).getTask());

            if("Schedule".equals(schedules.get(position).getSub_schedule(i).getType()))
            {
                child_sub_schedule_image.setBackgroundResource(R.drawable.ic_shedule);
            }
            else if("ToDo".equals(schedules.get(position).getSub_schedule(i).getType()))
            {
                child_sub_schedule_image.setBackgroundResource(R.drawable.ic_todo);
            }
            else if("Work Task".equals(schedules.get(position).getSub_schedule(i).getType()))
            {
                child_sub_schedule_image.setBackgroundResource(R.drawable.ic_work_task);
            }
            else if("Birthday".equals(schedules.get(position).getSub_schedule(i).getType()))
            {
                child_sub_schedule_image.setBackgroundResource(R.drawable.ic_birthday);
            }
        }
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

    public static class  ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;

        //Top
        TextView schedule_day;
        TextView schedule_type;
        TextView schedule_date;

        //Sub Tasks
        TextView sub_schedule_time;
        TextView sub_schedule_task;
        ImageView sub_schedule_image;

        LinearLayout subScheduleLayout;

        SheduleActivity activity;

        public  ScheduleViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            schedule_day = (TextView)itemView.findViewById(R.id.schedule_card_day);
            schedule_type = (TextView)itemView.findViewById(R.id.schedule_card_type);
            schedule_date = (TextView)itemView.findViewById(R.id.schedule_card_date);

           // subScheduleLayout = (LinearLayout)itemView.findViewById(R.id.subTaskContainer);
        }

        @Override
        public void onClick(View v)
        {

        }

        public  void addSubSchedule()
        {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View subScheduleView = layoutInflater.inflate(R.layout.sub_schedule, null);
            //View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_schedule, parent, false);

            sub_schedule_time = (TextView) subScheduleView.findViewById(R.id.schedule_card_sub_task_time);
            sub_schedule_task = (TextView) subScheduleView.findViewById(R.id.schedule_card_sub_task_task);
            sub_schedule_image = (ImageView) subScheduleView.findViewById(R.id.sub_schedule_icon);

            subScheduleLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_lay);

            subScheduleLayout.addView(subScheduleView);
        }

        public void setActivity(SheduleActivity activity)
        {
            this.activity = activity;
        }
    }
}
