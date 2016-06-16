package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Birthday;
import com.twinkle.orgint.pages.EventActivity;

import java.util.List;

public class BirthdayRecycleAdapter extends RecyclerView.Adapter<BirthdayRecycleAdapter.BirthdayViewHolder> implements View.OnClickListener
{
    BirthdayViewHolder birthdayViewHolder;
    List<Birthday> birthdayList;
    Context context;

    private int todo_item_position;
    private int sub_task_item_position;
    private int checked_sub_tasks_count;

    public BirthdayRecycleAdapter(List<Birthday> birthdayList, Context context)
    {
        this.birthdayList = birthdayList;
        this.context = context;
    }

    @Override
    public BirthdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.birthday_card, parent, false);
        birthdayViewHolder = new BirthdayViewHolder(view);
        birthdayViewHolder.setActivity((EventActivity)context);
        return birthdayViewHolder;
    }

    @Override
    public void onBindViewHolder(BirthdayViewHolder holder, int position)
    {
        //Sub tasks isDone / All counter
        final TextView sub_tasks_count_text = holder.sub_tasks_count;
        //Sub task image;
        final ImageView sub_tasks_image = holder.sub_tasks_icon;

        //Set top Work task text
        setToDoTopText(holder);

        //Set listeners to expand and collapse buttons
        holder.expand_button.setOnClickListener(this);
        holder.collapse_button.setOnClickListener(this);

        //Get position of work task item
        todo_item_position = holder.getAdapterPosition();

        //If there's no sub tasks and comments
        if(isSubTasksAndCommentAndInterest())
        {
            holder.subTasksAndComentLayout.setVisibility(View.GONE);
            holder.subTasksAndComentCountLayout.setVisibility(View.GONE);
        }
        else
        //If there's no sub_tasks
        if(isSubTasks())
        {
            holder.subTasksLayout.setVisibility(View.GONE);
            holder.sub_tasks_title.setVisibility(View.GONE);
        }
        else
        {
            addSubTask(holder, sub_tasks_count_text, sub_tasks_image);
        }

        //If there's no comment
        if(isComment())
        {
            holder.commentLayout.setVisibility(View.GONE);
            holder.comment_count.setText(Integer.toString(0));
            holder.divider.setVisibility(View.GONE);
        }
        else
        {
            if(isSubTasks())
            {
                holder.divider.setVisibility(View.GONE);
            }
            holder.coment.setText(birthdayList.get(position).getComment());
            holder.comment_count.setText(Integer.toString(1));
        }

        //If there's no interest
        if(isInterest())
        {
            holder.interest_icon.setVisibility(View.GONE);
            holder.interestLayout.setVisibility(View.GONE);
            holder.divider_com_int.setVisibility(View.GONE);
        }
        else
        {
            if(isSubTasks() && isComment())
            {
                holder.divider_com_int.setVisibility(View.GONE);
            }
            holder.interest.setText(birthdayList.get(position).getInterest());
        }


        //setting full image if all sub tasks isDone
        setSubTasksCompleteOrNot(sub_tasks_image);

        //Sub tasks counters init(setting)
        sub_tasks_count_text.setText(Integer.toString(checked_sub_tasks_count) + "/" + Integer.toString(birthdayList.get(position).getSubTasksCount()));
    }

    //Checks if there is comment
    private boolean isComment()
    {
        return "".equals(birthdayList.get(todo_item_position).getComment());
    }

    //Checks if there are syb tasks
    private boolean isSubTasks()
    {
        return birthdayList.get(todo_item_position).getSub_tasks().isEmpty();
    }

    //Checks if there is interest
    private boolean isInterest()
    {
        return "".equals(birthdayList.get(todo_item_position).getInterest());
    }

    //Checks if sub tasks and comment are empty
    private boolean isSubTasksAndComment()
    {
        return isSubTasks() && isComment();
    }

    //Checks if sub tasks, comment and interest are empty
    private boolean isSubTasksAndCommentAndInterest()
    {
        return isSubTasksAndComment() && isInterest();
    }

    //ToDo top text setting
    private void setToDoTopText(BirthdayViewHolder holder)
    {
        holder.birthday_task.setText(birthdayList.get(todo_item_position).getTask());
        holder.birthday_type.setText(birthdayList.get(todo_item_position).getType());
        holder.birthday_date.setText(birthdayList.get(todo_item_position).getDate() + " " + birthdayList.get(todo_item_position).getTime());
    }

    //Adding sub tasks and setting values
    private void addSubTask(BirthdayViewHolder holder, final TextView sub_tasks_count_text, final ImageView sub_tasks_image)
    {
        //Adding sub tasks
        for (int i = 0; i < birthdayList.get(todo_item_position).getSubTasksCount(); i++)
        {
            //get position of sub_task
            sub_task_item_position = i;

            //Add view for sub task
            holder.addSubTask();

            //Get view from created sub task
            View thisChild = holder.subTasksLayout.getChildAt(i);

            //Get elements from view
            TextView child_sub_task_task = (TextView) thisChild.findViewById(R.id.sub_task_title);
            CheckBox child_sub_task_isdone = (CheckBox) thisChild.findViewById(R.id.sub_task_checkBox);

            //Setting sub_task views
            child_sub_task_task.setText(birthdayList.get(todo_item_position).getSub_task(i).getContent());

            //in some cases, it will prevent unwanted situations
            child_sub_task_isdone.setOnCheckedChangeListener(null);
            child_sub_task_isdone.setChecked(birthdayList.get(todo_item_position).getSub_task(i).isDone());
            child_sub_task_isdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    birthdayList.get(todo_item_position).getSub_task(sub_task_item_position).setDone(isChecked);

                    if (isChecked) {
                        checked_sub_tasks_count++;
                    } else if (checked_sub_tasks_count > 0) {
                        checked_sub_tasks_count--;
                    }
                    sub_tasks_count_text.setText(Integer.toString(checked_sub_tasks_count) + "/" + Integer.toString(birthdayList.get(todo_item_position).getSubTasksCount()));

                    setSubTasksCompleteOrNot(sub_tasks_image);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return birthdayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setSubTasksCompleteOrNot(ImageView sub_tasks_image)
    {
        if(checked_sub_tasks_count == birthdayList.get(todo_item_position).getSubTasksCount())
        {
            sub_tasks_image.setBackgroundResource(R.drawable.ic_sub_tasks_full);
        }
        else{
            sub_tasks_image.setBackgroundResource(R.drawable.ic_sub_tasks_empty);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sub_tasks_expand:
                birthdayViewHolder.expand(birthdayViewHolder.cardView);
                break;

            case R.id.sub_tasks_collaps:
                birthdayViewHolder.collapse(birthdayViewHolder.cardView);
                break;
        }
    }

    public static class BirthdayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;

        //Top
        TextView birthday_task;
        TextView birthday_type;
        TextView birthday_date;

        //Sub Tasks
        TextView sub_task_task;
        CheckBox sub_task_isdone;

        //Sub Tasks title
        TextView sub_tasks_title;

        //Dividers
        View divider;
        View divider_com_int;

        //Interest Image
        ImageView interest_icon;

        //Coment
        TextView coment;

        //Interest
        TextView interest;

        //Sub tasks container
        LinearLayout subTasksLayout;

        LinearLayout subTasksAndComentLayout;
        RelativeLayout subTasksAndComentCountLayout;

        LinearLayout commentLayout;
        LinearLayout interestLayout;

        //Sub_Tasks counters
        TextView comment_count;
        TextView sub_tasks_count;

        //Sub_Tasks image
        ImageView sub_tasks_icon;

        //Expand and collapse buttons
        ImageView expand_button;
        ImageView collapse_button;

        EventActivity activity;

        public BirthdayViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            //Work task (top)
            birthday_task = (TextView)itemView.findViewById(R.id.birthday_task);
            birthday_type = (TextView)itemView.findViewById(R.id.birthday_type);
            birthday_date = (TextView)itemView.findViewById(R.id.birthday_date);

            //Comment
            coment = (TextView)itemView.findViewById(R.id.coment);

            //Interest
            interest = (TextView)itemView.findViewById(R.id.interest);

            //containers
            subTasksLayout = (LinearLayout) itemView.findViewById(R.id.sub_tasks_layout);
            subTasksAndComentLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_and_coments_and_interest_lay);
            subTasksAndComentCountLayout = (RelativeLayout)itemView.findViewById(R.id.sub_tasks_counter_lay);

            commentLayout = (LinearLayout) itemView.findViewById(R.id.coments_layout);
            interestLayout = (LinearLayout) itemView.findViewById(R.id.interests_layout);

            //sub tasks counters
            comment_count = (TextView)itemView.findViewById(R.id.comment_count);
            sub_tasks_count = (TextView)itemView.findViewById(R.id.sub_tasks_count);

            //sub task image
            sub_tasks_icon = (ImageView) itemView.findViewById(R.id.sub_tasks_icon);

            //Sub Tasks Title
            sub_tasks_title = (TextView) itemView.findViewById(R.id.sub_tasks_title);

            //Dividers
            divider  = itemView.findViewById(R.id.divider);
            divider_com_int  = itemView.findViewById(R.id.divider_com_int);

            //Expand and collapse buttons
            expand_button = (ImageView) itemView.findViewById(R.id.sub_tasks_expand);
            collapse_button = (ImageView) itemView.findViewById(R.id.sub_tasks_collaps);

            //Interest
            //Image
            interest_icon = (ImageView) itemView.findViewById(R.id.interest_icon);
        }

        @Override
        public void onClick(View v)
        {

        }

        public  void addSubTask()
        {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View subTaskView = layoutInflater.inflate(R.layout.sub_task_event, null);

            sub_task_task = (TextView) subTaskView.findViewById(R.id.sub_task_title);
            sub_task_isdone = (CheckBox) subTaskView.findViewById(R.id.sub_task_checkBox);

           // subTasksLayout = (LinearLayout)itemView.findViewById(R.id.sub_tasks_layout);
            subTasksLayout.addView(subTaskView);

        }

        public void setActivity(EventActivity activity)
        {
            this.activity = activity;
        }

        public void expand(final View v)
        {
            v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            subTasksAndComentLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            subTasksAndComentCountLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            final int currentHeight = v.getMeasuredHeight();
            final int targetHeight = subTasksAndComentLayout.getMeasuredHeight() - subTasksAndComentCountLayout.getMeasuredHeight();

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {

                    subTasksAndComentCountLayout.setVisibility(View.GONE);

                    v.getLayoutParams().height = currentHeight + (int) (targetHeight * interpolatedTime);
                    v.requestLayout();

                    subTasksAndComentLayout.setVisibility(View.VISIBLE);

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
            subTasksAndComentLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            subTasksAndComentCountLayout.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            final int initialHeight = v.getMeasuredHeight();
            final int targetHeight = subTasksAndComentLayout.getMeasuredHeight() - subTasksAndComentCountLayout.getMeasuredHeight();

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {
                    if(v.getLayoutParams().height == (initialHeight - targetHeight))
                    {
                        subTasksAndComentLayout.setVisibility(View.GONE);

                        subTasksAndComentCountLayout.setVisibility(View.VISIBLE);
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
