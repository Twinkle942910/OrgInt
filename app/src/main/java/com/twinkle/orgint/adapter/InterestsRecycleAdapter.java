package com.twinkle.orgint.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twinkle.orgint.R;
import com.twinkle.orgint.database.Interest;

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
        return svh;
    }

    @Override
    public void onBindViewHolder(InterestsViewHolder holder, int position)
    {
        holder.interest_title.setText(interests.get(position).getTitle());

        if(interests.get(position).isImportance())
        {
            holder.interest_importance.setText("Important");
            holder.interest_importance.setTextColor(context.getResources().getColor(R.color.colorInterestImportant));
        }
        else {
            holder.interest_importance.setText("Not Important");
            holder.interest_importance.setTextColor(context.getResources().getColor(R.color.colorInterestNotImportant));
        }


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

    public static class InterestsViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView interest_title;
        TextView interest_importance;

        public InterestsViewHolder(View itemView)
        {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardView);

            interest_title = (TextView)itemView.findViewById(R.id.interest_card_title);
            interest_importance = (TextView)itemView.findViewById(R.id.interest_card_importance);
        }

        //TODO: add interest touch
        private void initInterestTap()
        {

        }
    }
}
