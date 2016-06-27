package com.twinkle.orgint.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.twinkle.orgint.R;
import com.twinkle.orgint.pages.EventActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SimpleEventAddingFragment extends Fragment implements FragmentClickListener
{
    public static final int LAYOUT = R.layout.fragment_simple_event;

    //Argument for save state
    protected static final String ARGUMENT_TYPE = "type";

    private View view;

    //Task category
    private Spinner spinner_category;
    private String category;

    //task name
    private EditText task_title;

    //Task date
    private EditText task_date;

    //Task time
    private EditText task_time;

    //Sub Task
    private EditText subTask;
    private List<String> sub_tasks;

    //Task interest
    private EditText interest;

    //Task coment
    private EditText comment;

    private int mYear, mMonth, mDay, mHour, mMinute;

    //subTasks container
    private LinearLayout subTasksLayout;

    //Sub_tasks validation
    private boolean[] isValid;

    //Add sub task counter
    private int counter = 1;

    //Event type
    private String type;

    public static SimpleEventAddingFragment newInstance(String type)
    {
        final Bundle args = new Bundle();

        args.putString(ARGUMENT_TYPE, type);

        final SimpleEventAddingFragment fragment = new SimpleEventAddingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        final Bundle args = getArguments();

        type = args.getString(ARGUMENT_TYPE);

        spinner_category = (Spinner) view.findViewById(R.id.categories_spinner);
        task_date = (EditText) view.findViewById(R.id.in_date);
        task_time = (EditText) view.findViewById(R.id.in_time);
        interest = (EditText) view.findViewById(R.id.interest);
        task_title = (EditText) view.findViewById(R.id.task_name);
        comment = (EditText) view.findViewById(R.id.comment);

        subTasksLayout = (LinearLayout) view.findViewById(R.id.subTaskContainer);

        initSpinner();
        initDateTimePicker();

        return view;
    }

    public boolean checkValidation()
    {
        if(validateInput())
        {
            sendingData();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validateInput()
    {
        final String title = task_title.getText().toString();
        boolean choice = true;

        if(checkForSubTasks())
        {
            validateAllSubTasks();

            for (boolean anIsValid : isValid)
            {
                if (!anIsValid)
                {
                    choice = false;
                    break;
                }
                else
                {
                    choice = true;
                }
            }
        }

        if (!isValidTitle(title))
        {
            task_title.setError("Title can't be empty!");
            choice = false;
        }
        return choice;
    }

    private void validateAllSubTasks()
    {
        int childCount = subTasksLayout.getChildCount();
        isValid = new boolean[childCount];

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subTasksLayout.getChildAt(i);

            EditText childTextView = (EditText) thisChild.findViewById(R.id.sub_task);

            String content = childTextView.getText().toString();
            isValid[i] = validateInputSubTask(content, childTextView);
        }
    }

    private boolean validateInputSubTask(String sub_task, EditText sub_task_textField)
    {
        if (!isValidSubTask(sub_task))
        {
            sub_task_textField.setError("Sub task can't be empty!");
            return false;
        }
        return true;
    }

    // validating title
    private boolean isValidTitle(String title)
    {
        if (title != null && title.length() > 0)
        {
            return true;
        }
        return false;
    }

    // validating sub_task
    private boolean isValidSubTask(String sub_task)
    {
        if (sub_task != null && sub_task.length() > 3)
        {
            return true;
        }
        return false;
    }

    private void sendingData()
    {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra("calling", "From Adding Activity");

        //Added data
        intent.putExtra("category", category);
        intent.putExtra("title", task_title.getText().toString());
        intent.putExtra("date", task_date.getText().toString());
        intent.putExtra("time", task_time.getText().toString());

        String[] subTasks_array;

        if(checkForSubTasks())
        {
            getAllSubTasks();
            subTasks_array = new String[sub_tasks.size()];
            for (int i = 0; i < subTasks_array.length; i++) {
                subTasks_array[i] = sub_tasks.get(i);
            }
        }
        else
        {
            subTasks_array = new String[0];
        }

        intent.putExtra("sub_tasks", subTasks_array);
        intent.putExtra("comment", comment.getText().toString());
        intent.putExtra("interest", interest.getText().toString());

        intent.putExtra("type", type);

        startActivity(intent);
        getActivity().finish();
    }

    private void initSpinner()
    {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, R.layout.spinner_category_item);
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
        String myFormatDate = "E, LLLL d yyyy";
        String myFormatTime = "kk:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);
        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

        final Calendar c = Calendar.getInstance();

        task_date.setText(sdf.format(c.getTime()));
        task_time.setText(stf.format(c.getTime()));
    }

    public void datePick()
    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDialogTheme,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        //String myFormatDate = "EEEE, LLLL d";
                        String myFormatDate = "LLLL d, yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormatDate, Locale.US);

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        task_date.setText(sdf.format(c.getTime()));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timePick()
    {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.MyDialogTheme,
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

    public void addSubTask()
    {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.sub_task, null);

        subTask = (EditText)addView.findViewById(R.id.sub_task);
        subTask.setText(Integer.toString(counter++) + ". ");

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

    private boolean checkForSubTasks()
    {
        if (subTasksLayout != null)
        {
            return true;
        }
        return false;
    }

    private void getAllSubTasks()
    {
        int childCount = subTasksLayout.getChildCount();
        sub_tasks = new ArrayList<>();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subTasksLayout.getChildAt(i);

            EditText childTextView = (EditText) thisChild.findViewById(R.id.sub_task);

            String content = childTextView.getText().toString();
            sub_tasks.add(content);
        }
    }

    @Override
    public void onFragmentClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sub_task_add:
                addSubTask();
                break;

            case R.id.in_date:
                datePick();
                break;

            case R.id.in_time:
                timePick();
                break;
        }
    }

    public void setInterest(String interest)
    {
        this.interest.setText(interest);
    }

}
