package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twinkle.orgint.MainActivity;
import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Schedule;
import com.twinkle.orgint.helpers.AdapterCommunicator;

import java.util.List;

public class ScheduleRecycleAdapter extends RecyclerView.Adapter<ScheduleRecycleAdapter. ScheduleViewHolder> implements AdapterCommunicator
{
    List<Schedule> schedules;
    Context context;

    public ScheduleRecycleAdapter(List<Schedule> schedules, Context context)
    {
        this.schedules = schedules;
        this.context = context;

        ((MainActivity)context).adapterCommunicator = this;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shedule_card, parent, false);
        ScheduleViewHolder viewHolder = new  ScheduleViewHolder(view);
        viewHolder.setActivity((MainActivity) context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position)
    {
        Log.d("RW", "onBindViewHolder,position=" + position);

        //Set top data of schedule
        initScheduleTop(holder, position);

        //Set sub schedules
        addSubSchedule(holder, position);
    }

    private void addSubSchedule(ScheduleViewHolder holder, int position)
    {
        removeSchedules(holder);

        int sub_schedules_count = 0;
        int sub_todo_count = 0;
        int sub_work_tasks_count = 0;
        int sub_birthdays_count = 0;

        int schedule_sub_schedules_count = schedules.get(position).getSubSchedulesCount();

        for (int i = 0; i < schedule_sub_schedules_count; i++)
        {
            String sub_taskType = schedules.get(position).getSub_schedule(i).getType();

            View thisChild = null;

            TextView child_sub_schedule_time = null;
            TextView child_sub_schedule_task = null;
            ImageView child_sub_schedule_image = null;

            int sub_task_image = R.drawable.ic_shedule;

          /*  if (isAdding)
            {
                holder.addSubSchedule(sub_taskType);
                isAdding = false;

                Log.d("Adding One RW", "onBindViewHolder,position=" + position);
            }*/

            holder.addSubSchedule(sub_taskType);

            switch (sub_taskType)
            {
                case "Schedule":
                    thisChild = holder.scheduleLayout.getChildAt(sub_schedules_count);
                    sub_task_image = R.drawable.ic_shedule;
                    sub_schedules_count++;
                    break;

                case "ToDo":
                    thisChild = holder.todoLayout.getChildAt(sub_todo_count);
                    sub_task_image = R.drawable.ic_todo;
                    sub_todo_count++;
                    break;

                case "Work Task":
                    thisChild = holder.work_tasksLayout.getChildAt(sub_work_tasks_count);
                    sub_task_image = R.drawable.ic_work_task;
                    sub_work_tasks_count++;
                    break;

                case "Birthday":
                    thisChild = holder.bithdaysLayout.getChildAt(sub_birthdays_count);
                    sub_task_image = R.drawable.ic_birthday;
                    sub_birthdays_count++;
                    break;
            }

            if (thisChild != null) {
                child_sub_schedule_time = (TextView) thisChild.findViewById(R.id.schedule_card_sub_task_time);
                child_sub_schedule_task = (TextView) thisChild.findViewById(R.id.schedule_card_sub_task_task);
                child_sub_schedule_image = (ImageView) thisChild.findViewById(R.id.sub_schedule_icon);
            }

            if (child_sub_schedule_time != null) {
                child_sub_schedule_time.setText(schedules.get(position).getSub_schedule(i).getTime());
            }

            if (child_sub_schedule_task != null) {
                child_sub_schedule_task.setText(schedules.get(position).getSub_schedule(i).getTask());
            }

            if (child_sub_schedule_image != null) {
                child_sub_schedule_image.setBackgroundResource(sub_task_image);
            }
        }

        //init counters of sub schedules
        initCounters(holder, sub_schedules_count, sub_todo_count, sub_work_tasks_count, sub_birthdays_count);
    }

    private void initScheduleTop(ScheduleViewHolder holder, int position)
    {
        holder.schedule_day.setText(schedules.get(position).getDay());
        holder.schedule_type.setText(schedules.get(position).getType());
        holder.schedule_date.setText(schedules.get(position).getDate());
    }

    private void initCounters(ScheduleViewHolder holder, int sub_schedules_count, int sub_todo_count, int sub_work_tasks_count, int sub_birthdays_count)
    {
        holder.sub_schedules_count_txt.setText(Integer.toString(sub_schedules_count));
        holder.sub_todo_count_txt.setText(Integer.toString(sub_todo_count));
        holder.sub_work_task_count_txt.setText(Integer.toString(sub_work_tasks_count));
        holder.sub_birthday_count_txt.setText(Integer.toString(sub_birthdays_count));
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

    public void addItem(Schedule dataObj, int index)
    {
        schedules.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index)
    {
        schedules.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void updateItem(int position)
    {
        notifyItemChanged(position);
       // notifyDataSetChanged();
    }

    private void removeSchedules(ScheduleViewHolder holder)
    {
        if(holder.scheduleLayout.getChildCount() > 0)
        {
            holder.scheduleLayout.removeAllViews();
        }
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
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
        RelativeLayout subScheduleCountLayout;

        LinearLayout scheduleLayout;
        LinearLayout todoLayout;
        LinearLayout work_tasksLayout;
        LinearLayout bithdaysLayout;

        //Sub_Tasks counters
        TextView sub_schedules_count_txt;
        TextView sub_todo_count_txt;
        TextView sub_work_task_count_txt;
        TextView sub_birthday_count_txt;

        Context context;

        MainActivity activity;

        public ScheduleViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            //Schedule (top)
            schedule_day = (TextView)itemView.findViewById(R.id.schedule_card_day);
            schedule_type = (TextView)itemView.findViewById(R.id.schedule_card_type);
            schedule_date = (TextView)itemView.findViewById(R.id.schedule_card_date);

            //containers
            subScheduleLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_lay);
            subScheduleCountLayout = (RelativeLayout)itemView.findViewById(R.id.sub_tasks_counter_lay);

            scheduleLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_schedule_layout);
            todoLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_todo_layout);
            work_tasksLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_work_task_layout);
            bithdaysLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_birthday_layout);

            //sub tasks counters
            sub_schedules_count_txt = (TextView)itemView.findViewById(R.id.sub_schedules_count_txt);
            sub_todo_count_txt = (TextView)itemView.findViewById(R.id.sub_todo_count_txt);
            sub_work_task_count_txt = (TextView)itemView.findViewById(R.id.sub_work_task_count_txt);
            sub_birthday_count_txt = (TextView)itemView.findViewById(R.id.sub_birthday_count_txt);
        }

        boolean flag = false;

        @Override
        public void onClick(View v)
        {
            if (!flag) {
                expand(cardView);
                flag = true;
            }
            else{
                collapse(cardView);
                flag = false;
            }
        }

        public void addSubSchedule(String type)
        {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View subScheduleView = layoutInflater.inflate(R.layout.sub_schedule, null);

            sub_schedule_time = (TextView) subScheduleView.findViewById(R.id.schedule_card_sub_task_time);
            sub_schedule_task = (TextView) subScheduleView.findViewById(R.id.schedule_card_sub_task_task);
            sub_schedule_image = (ImageView) subScheduleView.findViewById(R.id.sub_schedule_icon);

            switch (type)
            {
                case "Schedule":
                scheduleLayout.addView(subScheduleView);
                    break;

                case "ToDo":
                todoLayout.addView(subScheduleView);
                    break;

                case "Work Task":
                work_tasksLayout.addView(subScheduleView);
                    break;

                case "Birthday":
                bithdaysLayout.addView(subScheduleView);
                    break;
            }

           //subScheduleLayout.addView(subScheduleView);

            //int pos = getAdapterPosition();
        }

        public void setActivity(MainActivity activity)
        {
            this.activity = activity;
        }

        public void setContext(Context context)
        {
            this.context = context;
        }

        public void expand(final View v)
        {
            v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            subScheduleLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            subScheduleCountLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            final int currentHeight = v.getMeasuredHeight();
            final int targetHeight = subScheduleLayout.getMeasuredHeight() - subScheduleCountLayout.getMeasuredHeight();

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {

                    subScheduleCountLayout.setVisibility(View.GONE);

                    v.getLayoutParams().height = currentHeight + (int) (targetHeight * interpolatedTime);
                    v.requestLayout();

                    subScheduleLayout.setVisibility(View.VISIBLE);

                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
            v.startAnimation(a);
        }

        public void collapse(final View v)
        {
           // v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            subScheduleLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            subScheduleCountLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            final int initialHeight = v.getMeasuredHeight();
            final int targetHeight = subScheduleLayout.getMeasuredHeight() - subScheduleCountLayout.getMeasuredHeight();

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {
                    if(v.getLayoutParams().height == (initialHeight -targetHeight))
                    {
                        subScheduleLayout.setVisibility(View.GONE);

                        subScheduleCountLayout.setVisibility(View.VISIBLE);
                    }

                        v.getLayoutParams().height = initialHeight - (int)(targetHeight * interpolatedTime);
                        v.requestLayout();

                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }
    }
}
