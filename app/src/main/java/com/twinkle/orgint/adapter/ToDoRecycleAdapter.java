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
import android.widget.Toast;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.ToDo;
import com.twinkle.orgint.pages.EventActivity;

import java.util.List;

public class ToDoRecycleAdapter extends RecyclerView.Adapter<ToDoRecycleAdapter.ToDoViewHolder> implements View.OnClickListener
{
    ToDoViewHolder toDoViewHolder;
    List<ToDo> toDoList;
    Context context;

    private int sub_task_item_position;
    private int checked_sub_tasks_count;

    public ToDoRecycleAdapter( List<ToDo> toDoList, Context context)
    {
        this.toDoList = toDoList;
        this.context = context;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card, parent, false);
        toDoViewHolder = new ToDoViewHolder(view);
        toDoViewHolder.setActivity((EventActivity)context);
        return toDoViewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position)
    {
        //Sub tasks isDone / All counter
        final TextView sub_tasks_count_text = holder.sub_tasks_count;
        //Sub task image;
        final ImageView sub_tasks_image = holder.sub_tasks_icon;

        //Set top ToDo text
        setToDoTopText(holder, position);

        //Set listeners to expand and collapse buttons
        holder.expand_button.setOnClickListener(this);
        holder.collapse_button.setOnClickListener(this);

        //If there's no sub tasks and comments
        if(isSubTasksAndCommentAndInterest(position))
        {
            holder.subTasksAndComentLayout.setVisibility(View.GONE);
            holder.subTasksAndComentCountLayout.setVisibility(View.GONE);
        }
        else
        //If there's no sub_tasks
        if(isSubTasks(position))
        {
            holder.subTasksLayout.setVisibility(View.GONE);
            holder.sub_tasks_title.setVisibility(View.GONE);
        }
        else
        {
            addSubTask(holder, sub_tasks_count_text, sub_tasks_image, position);
        }

        //If there's no comment
        if(isComment(position))
        {
            holder.commentLayout.setVisibility(View.GONE);
            holder.comment_count.setText(Integer.toString(0));
            holder.divider.setVisibility(View.GONE);
        }
        else
        {
            if(isSubTasks(position))
            {
                holder.divider.setVisibility(View.GONE);
            }
            holder.coment.setText(toDoList.get(position).getComment());
            holder.comment_count.setText(Integer.toString(1));
        }

        //If there's no interest
        if(isInterest(position))
        {
            holder.interest_icon.setVisibility(View.GONE);
            holder.interestLayout.setVisibility(View.GONE);
            holder.divider_com_int.setVisibility(View.GONE);
        }
        else
        {
            if(isSubTasks(position) && isComment(position))
            {
                holder.divider_com_int.setVisibility(View.GONE);
            }
            holder.interest.setText(toDoList.get(position).getInterest());
        }


        //setting full image if all sub tasks isDone
        setSubTasksCompleteOrNot(sub_tasks_image, position);

        //Sub tasks counters init(setting)
        sub_tasks_count_text.setText(Integer.toString(checked_sub_tasks_count) + "/" + Integer.toString(toDoList.get(position).getSubTasksCount()));
    }

    //Checks if there is comment
    private boolean isComment(int position)
    {
        return "".equals(toDoList.get(position).getComment());
    }

    //Checks if there are syb tasks
    private boolean isSubTasks(int position)
    {
        return toDoList.get(position).getSub_tasks().isEmpty();
    }

    //Checks if there is interest
    private boolean isInterest(int position)
    {
        return "".equals(toDoList.get(position).getInterest());
    }

    //Checks if sub tasks and comment are empty
    private boolean isSubTasksAndComment(int position)
    {
        return isSubTasks(position) && isComment(position);
    }

    //Checks if sub tasks, comment and interest are empty
    private boolean isSubTasksAndCommentAndInterest(int position)
    {
        return isSubTasksAndComment(position) && isInterest(position);
    }

    //ToDo top text setting
    private void setToDoTopText(ToDoViewHolder holder, int position)
    {
        holder.todo_task.setText(toDoList.get(position).getTask());
        holder.todo_type.setText(toDoList.get(position).getType());
        holder.todo_date.setText(toDoList.get(position).getDate() + " " + toDoList.get(position).getTime());
    }

    //Adding sub tasks and setting values
    private void addSubTask(ToDoViewHolder holder, final TextView sub_tasks_count_text, final ImageView sub_tasks_image, final int position)
    {
        //Adding sub tasks
        for (int i = 0; i < toDoList.get(position).getSubTasksCount(); i++)
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
            child_sub_task_task.setText(toDoList.get(position).getSub_task(i).getContent());

            //in some cases, it will prevent unwanted situations
            child_sub_task_isdone.setOnCheckedChangeListener(null);
            child_sub_task_isdone.setChecked(toDoList.get(position).getSub_task(i).isDone());
            child_sub_task_isdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    toDoList.get(position).getSub_task(sub_task_item_position).setDone(isChecked);

                    if (isChecked) {
                        checked_sub_tasks_count++;
                    } else if (checked_sub_tasks_count > 0) {
                        checked_sub_tasks_count--;
                    }
                    sub_tasks_count_text.setText(Integer.toString(checked_sub_tasks_count) + "/" + Integer.toString(toDoList.get(position).getSubTasksCount()));

                    setSubTasksCompleteOrNot(sub_tasks_image, position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return toDoList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setSubTasksCompleteOrNot(ImageView sub_tasks_image, int position)
    {
        if(checked_sub_tasks_count == toDoList.get(position).getSubTasksCount())
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
                toDoViewHolder.expand(toDoViewHolder.cardView);
                break;

            case R.id.sub_tasks_collaps:
                toDoViewHolder.collapse(toDoViewHolder.cardView);
                break;
        }
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;

        //Top
        TextView todo_task;
        TextView todo_type;
        TextView todo_date;

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

        public ToDoViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            //ToDo (top)
            todo_task = (TextView)itemView.findViewById(R.id.todo_task);
            todo_type = (TextView)itemView.findViewById(R.id.todo_type);
            todo_date = (TextView)itemView.findViewById(R.id.todo_date);

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
