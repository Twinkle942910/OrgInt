package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Planning;

import java.util.List;

public class PlanningRecycleAdapter extends RecyclerView.Adapter<PlanningRecycleAdapter.PlanningViewHolder>
{
    List<Planning> plannings;
    Context context;

    public PlanningRecycleAdapter(List<Planning> plannings, Context context)
    {
        this.plannings = plannings;
        this.context = context;
    }

    @Override
    public PlanningViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planning_card, parent, false);
        PlanningViewHolder svh = new PlanningViewHolder(view);
       // svh.setActivity((InterestsActivity)context);
        return svh;
    }

    @Override
    public void onBindViewHolder(PlanningViewHolder holder, int position)
    {
        holder.planning_category.setText(plannings.get(position).getCategory());
        holder.image.setBackgroundResource(plannings.get(position).getImage());

        holder.schedule_count.setText(Integer.toString(plannings.get(position).getSchedules_count()));
        holder.todo_count.setText(Integer.toString(plannings.get(position).getTodo_count()));
        holder.work_task_count.setText(Integer.toString(plannings.get(position).getWork_tasks_count()));
        holder.birthday_count.setText(Integer.toString(plannings.get(position).getBirthdays_count()));


        switch (plannings.get(position).getCategory())
        {
            case "Urgent \nImportant":
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorUICategory));
                break;

            case "Not Urgent \nImportant":
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorNUICategory));
                break;

            case "Urgent \nNot Important":
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorUNICategory));
                break;

            case "Not Urgent \nNot Important":
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorNUNICategory));
                break;
        }



    /*    if(plannings.get(position).getCategory())
        {
            holder.interest_importance.setText("Important");
            holder.interest_importance.setTextColor(context.getResources().getColor(R.color.colorInterestImportant));
        }
        else {
            holder.interest_importance.setText("Not Important");
            holder.interest_importance.setTextColor(context.getResources().getColor(R.color.colorInterestNotImportant));
        }*/


    }

    @Override
    public int getItemCount()
    {
        return plannings.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PlanningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;

        TextView planning_category;
        ImageView image;

        TextView schedule_count;
        TextView todo_count;
        TextView work_task_count;
        TextView birthday_count;

        //InterestsActivity activity;

        public PlanningViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            planning_category = (TextView)itemView.findViewById(R.id.category_title);
            image = (ImageView) itemView.findViewById(R.id.planning_image);

            schedule_count = (TextView)itemView.findViewById(R.id.schedules_count_txt);
            todo_count = (TextView)itemView.findViewById(R.id.todo_count_txt);
            work_task_count = (TextView)itemView.findViewById(R.id.work_task_count_txt);
            birthday_count = (TextView)itemView.findViewById(R.id.birthday_count_txt);
        }

        @Override
        public void onClick(View v)
        {
            //Log.i("R_Click", "Lol!" + interest_title.getText().toString());

           /* //Added recently
            Intent add_activ = activity.getIntent();
            String red_code = add_activ.getStringExtra("red_code");

            if("From adding activity".equals(red_code))
            {
                Intent intent = new Intent();
                intent.putExtra("interest_title", interest_title.getText().toString());
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
            else
            {
                Log.i("R_Click", "Inside else");
                //TODO: Write action for interests tap
            }*/
        }

     /*   public void setActivity(InterestsActivity activity)
        {
            this.activity = activity;
        }*/

    }
}
