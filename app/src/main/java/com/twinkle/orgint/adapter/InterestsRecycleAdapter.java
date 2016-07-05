package com.twinkle.orgint.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Interest;
import com.twinkle.orgint.pages.InterestsActivity;

import java.util.List;

public class InterestsRecycleAdapter extends RecyclerView.Adapter<InterestsRecycleAdapter.InterestsViewHolder>
{
    List<Interest> interests;
    Context context;

    public InterestsRecycleAdapter(List<Interest> interests, Context context)
    {
        this.interests = interests;
        this.context = context;
    }

    @Override
    public InterestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_card, parent, false);
        InterestsViewHolder svh = new InterestsViewHolder(view);
        svh.setActivity((InterestsActivity)context);
        return svh;
    }

    @Override
    public void onBindViewHolder(InterestsViewHolder holder, int position)
    {
        holder.interest_title.setText(interests.get(position).getTitle());

        int importance_value = interests.get(position).getImportance();

      //  (importance_value > 0 && importance_value < 30) ? holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorNUNICategory)) ;

        if(importance_value >= 0 && importance_value < 30)
        {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorNUNICategory));
        }
        else if(importance_value > 30 && importance_value < 50)
        {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorUNICategory));
        }
        else if(importance_value > 50 && importance_value < 70)
        {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorNUICategory));
        }
        else if(importance_value > 70 && importance_value <= 100)
        {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorUICategory));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        }

        holder.interest_importance.setText(Integer.toString(importance_value) + "%");
    }

    @Override
    public int getItemCount()
    {
        return interests.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class InterestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView cardView;
        TextView interest_title;
        TextView interest_importance;

        InterestsActivity activity;

        public InterestsViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            cardView = (CardView)itemView.findViewById(R.id.interest_card);

            interest_title = (TextView)itemView.findViewById(R.id.interest_card_title);
            interest_importance = (TextView)itemView.findViewById(R.id.interest_card_importance);

        }

        @Override
        public void onClick(View v)
        {
            //Log.i("R_Click", "Lol!" + interest_title.getText().toString());

            //Added recently
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
            }
        }

        public void setActivity(InterestsActivity activity)
        {
            this.activity = activity;
        }

    }
}
