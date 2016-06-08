package com.twinkle.orgint.pages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.twinkle.orgint.R;
import com.twinkle.orgint.helpers.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddingActivity extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_adding;

    private Toolbar toolbar;

    //Task category
    private Spinner spinner_category;
    private String category;

    //task name
    EditText task_title;

    //Task date
    EditText task_date;

    //Task time
    EditText task_time;

    //Sub Task
    EditText subTask;
    List<String> sub_tasks;

    //Task interest
    EditText interest;

    //Task coment
    EditText comment;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private FloatingActionButton fab_interest;

    //subTasks container
    private LinearLayout subTasksLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        setupActionBar();
        initSpinner();
        initFAB();

        initDateTimePicker();
        initTitleAndComment();
        initInterest();
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

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adding, menu);
        return true;
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

            case R.id.save:
                sendingData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendingData()
    {
        Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
        intent.putExtra("calling", "From Adding Activity");

        //Added data
        intent.putExtra("category", category);
        intent.putExtra("title", task_title.getText().toString());
        intent.putExtra("date", task_date.getText().toString());
        intent.putExtra("time", task_time.getText().toString());

        getAllSubTasks();
        String[] subTasks_array = new String[sub_tasks.size()];
        for (int i = 0 ; i< subTasks_array.length; i++)
        {
            subTasks_array[i] = sub_tasks.get(i);
        }

        intent.putExtra("sub_tasks", subTasks_array);
        intent.putExtra("comment", comment.getText().toString());
        intent.putExtra("interest", interest.getText().toString());

        startActivity(intent);
        this.finish();
    }

    private void initSpinner()
    {
        spinner_category = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, R.layout.spinner_category_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_category_drop_item);
        // Apply the adapter to the spinner
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                    category = spinner_category.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                category = spinner_category.getSelectedItem().toString();
            }
        }
        );
    }

    private void initDateTimePicker()
    {
        task_date =(EditText)findViewById(R.id.in_date);
        task_time =(EditText)findViewById(R.id.in_time);

        String myFormatDate = "E, LLLL d yyyy";
        String myFormatTime = "kk:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);
        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

        final Calendar c = Calendar.getInstance();

        task_date.setText(sdf.format(c.getTime()));
        task_time.setText(stf.format(c.getTime()));
    }

    public void pick(View v)
    {
        if (v == task_date)
        {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MyDialogTheme,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth)
                        {
                            String myFormatDate = "E, LLLL d yyyy";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);

                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, monthOfYear);
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            task_date.setText(sdf.format(c.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == task_time)
        {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.MyDialogTheme,
                    new TimePickerDialog.OnTimeSetListener()
                    {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                        {
                            String myFormatTime = "kk:mm";

                            SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);

                            task_time.setText(stf.format(c.getTime()));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    private void initFAB()
    {
        fab_interest = (FloatingActionButton)findViewById(R.id.fab_interest);

        fab_interest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(getApplicationContext(), InterestsActivity.class);

                //Added recently
                intent.putExtra("red_code", "From adding activity");

                startActivityForResult(intent, Constants.REQUEST_CODE_INTEREST);


            }
        });
    }

    private void initInterest()
    {
        interest=(EditText)findViewById(R.id.interest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == Constants.REQUEST_CODE_INTEREST)
            {
                String interest_title = data.getStringExtra("interest_title");
                interest.setText(interest_title);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error! Different result codes", Toast.LENGTH_SHORT).show();
        }
    }

    int counter = 1;

    public void initTitleAndComment()
    {
        task_title = (EditText) findViewById(R.id.task_name);
        comment = (EditText) findViewById(R.id.comment);
    }

    public  void addSubTask(View view)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.sub_task, null);

        subTask = (EditText)addView.findViewById(R.id.sub_task);
        subTask.setText(Integer.toString(counter++) + ". ");

        subTasksLayout = (LinearLayout)findViewById(R.id.subTaskContainer);

        if (subTasksLayout != null)
        {
            subTasksLayout.addView(addView);
        }

        ImageView buttonRemove = (ImageView) addView.findViewById(R.id.sub_task_remove);

        final View.OnClickListener thisListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
                counter--;

            }
        };

        buttonRemove.setOnClickListener(thisListener);

    }

    private void getAllSubTasks()
    {
        int childCount = subTasksLayout.getChildCount();
        sub_tasks = new ArrayList<>();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subTasksLayout.getChildAt(i);

            EditText childTextView = (EditText) thisChild.findViewById(R.id.sub_task);

            sub_tasks.add(childTextView.getText().toString());
        }
    }

}
