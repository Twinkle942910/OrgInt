package com.twinkle.orgint.pages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.twinkle.orgint.R;
import com.twinkle.orgint.adapter.InterestsRecycleAdapter;
import com.twinkle.orgint.database.Interest;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity
{

    private static final int LAYOUT = R.layout.activity_interests;

    private Toolbar toolbar;
    private FloatingActionButton interest_add;

    private List<Interest> interests;
    private Interest interest;

    private InterestsRecycleAdapter adapter;

    //Interest importance
    private boolean importance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();
        initFAB();

        initList();
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        switch(item.getItemId())
        {
           case android.R.id.home:

                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFAB()
    {
       interest_add = (FloatingActionButton)findViewById(R.id.interest_add);

        interest_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showChangeLangDialog();
            }
        });
    }

    private void showChangeLangDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_adding_interest, null);
        dialogBuilder.setView(dialogView);

        final EditText interest_title = (EditText) dialogView.findViewById(R.id.interest_title);
        final SwitchCompat interest_importance = (SwitchCompat) dialogView.findViewById(R.id.importance);

        importance = false;

        dialogBuilder.setTitle("New Interest");

        interest_importance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
               importance = isChecked;
            }
        });

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
               interest = new Interest();

                interest.setTitle(interest_title.getText().toString());

                interest.setImportance(importance);



                interests.add(interest);
                adapter.notifyItemInserted(interests.size()-1);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void initList()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.interest_list);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);

        initializeData();

        adapter = new  InterestsRecycleAdapter(interests, this);
        recyclerView.setAdapter(adapter);
    }

    private void initializeData()
    {
        interests = new ArrayList<>();

        Interest in1 = new Interest();
        in1.setTitle("Books");
        in1.setImportance(true);

        Interest in2 = new Interest();
        in2.setTitle("Study");
        in2.setImportance(true);

        Interest in3 = new Interest();
        in3.setTitle("Work");
        in3.setImportance(true);

        interests.add(in1);
        interests.add(in2);
        interests.add(in3);
    }


}
