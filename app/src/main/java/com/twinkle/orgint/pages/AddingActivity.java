package com.twinkle.orgint.pages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.twinkle.orgint.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddingActivity extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_adding;

    private Toolbar toolbar;
    private Spinner spinner;

    EditText txtDate, txtTime, subTask;
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
        initSubTask();
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
            case R.id.save:

            case android.R.id.home:

                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initSpinner()
    {
        spinner = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, R.layout.spinner_category_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_category_drop_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );
    }

    private void initDateTimePicker()
    {
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        String myFormatDate = "E, LLLL d yyyy";
        String myFormatTime = "kk:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);
        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

        final Calendar c = Calendar.getInstance();

        txtDate.setText(sdf.format(c.getTime()));
        txtTime.setText(stf.format(c.getTime()));
    }

    public void pick(View v)
    {
        if (v == txtDate)
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

                            txtDate.setText(sdf.format(c.getTime()));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == txtTime)
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

                            txtTime.setText(stf.format(c.getTime()));
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
                Toast.makeText(getApplicationContext(),"Lol, interest!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    int counter = 1;

    public void initSubTask()
    {

    }

    public  void addSubTask(View view)
    {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.sub_task, null);

        subTask = (EditText)addView.findViewById(R.id.sub_task);
        subTask.setText(Integer.toString(counter++) + ". ");

        subTasksLayout = (LinearLayout)findViewById(R.id.subTaskContainer);

        subTasksLayout.addView(addView);

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


    public void getAllTasks(View view)
    {
        int childCount = subTasksLayout.getChildCount();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subTasksLayout.getChildAt(i);

            EditText childTextView = (EditText) thisChild.findViewById(R.id.sub_task);

            //This is text of subTask
            String childTextViewValue = childTextView.getText().toString();
        }
    }

}
