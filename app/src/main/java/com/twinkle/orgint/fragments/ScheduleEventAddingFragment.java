package com.twinkle.orgint.fragments;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.twinkle.orgint.MainActivity;
import com.twinkle.orgint.R;
import com.twinkle.orgint.helpers.Constants;
import com.twinkle.orgint.pages.EventActivity;
import com.twinkle.orgint.pages.InterestsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleEventAddingFragment extends Fragment implements FragmentClickListener
{
    public static final int LAYOUT = R.layout.fragment_schedule_event;

    //Argument for save state
    protected static final String ARGUMENT_TYPE = "type";

    private View view;

    //Task category
    private Spinner spinner_day;

    //Day
    private String day;

    //Sub schedule name
    private EditText task_title;

    //Task coment
    private EditText comment;

    //Task interest
    private EditText interest_edit;

    private String interest;

    //Sub Schedules
    private List<String> titles;
    private List<String> times;
    private List<String> comments;
    private List<String> interests;

    private int mHour, mMinute;

    //subTasks container
    private LinearLayout subScheduleLayout;

    //Sub_tasks validation
    private boolean[] isValid;

    //Event type
    private String type;

    public static ScheduleEventAddingFragment newInstance(String type)
    {
        final Bundle args = new Bundle();

        args.putString(ARGUMENT_TYPE, type);

        final ScheduleEventAddingFragment fragment = new ScheduleEventAddingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        final Bundle args = getArguments();

        type = args.getString(ARGUMENT_TYPE);

        spinner_day = (Spinner) view.findViewById(R.id.day_spinner);

        subScheduleLayout = (LinearLayout) view.findViewById(R.id.subScheduleContainer);

        initSpinner();

        return view;
    }

    public boolean checkValidation()
    {
      //  if(validateInput())
      //  {
            sendingData();
            return true;
      //  }
      //  else
      //  {
         //   return false;
        //}
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
        int childCount = subScheduleLayout.getChildCount();
        isValid = new boolean[childCount];

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subScheduleLayout.getChildAt(i);

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
        Intent intent = new Intent();

        //Added data
        intent.putExtra("day", day);

        String[] scheduleTitles;
        String[] scheduleTimes;
        String[] scheduleComments;
        String[] scheduleInterests;

        if(checkForSubTasks())
        {
            getAllSubSchedules();

            scheduleTitles = new String[titles.size()];
            scheduleTimes = new String[times.size()];
            scheduleComments = new String[comments.size()];
            scheduleInterests = new String[interests.size()];

            for (int i = 0; i < scheduleTitles.length; i++)
            {
                scheduleTitles[i] = titles.get(i);
                scheduleTimes[i] = times.get(i);
                scheduleComments[i] = comments.get(i);
                scheduleInterests[i] = interests.get(i);
            }
        }
        else
        {
            scheduleTitles = new String[0];
            scheduleTimes = new String[0];
            scheduleComments = new String[0];
            scheduleInterests = new String[0];
        }

        intent.putExtra("title", scheduleTitles);
        intent.putExtra("time", scheduleTimes);
        intent.putExtra("comment", scheduleComments);
        intent.putExtra("interest", scheduleInterests);

        intent.putExtra("type", type);

        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void initSpinner()
    {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.day_array, R.layout.spinner_category_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_category_drop_item);
        // Apply the adapter to the spinner
        spinner_day.setAdapter(adapter);

        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                                   {

                                                       @Override
                                                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                                       {
                                                           day = spinner_day.getSelectedItem().toString();
                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> parent)
                                                       {
                                                           day = spinner_day.getSelectedItem().toString();
                                                       }
                                                   }
        );
    }

    public void timePick(final EditText task_time)
    {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.MyDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String myFormatTime = "kk:mm";

                        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        task_time.setText(stf.format(c.getTime()));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void setInterestText(final EditText interest)
    {
        Intent intent = new Intent(getActivity(), InterestsActivity.class);
        intent.putExtra("red_code", "From adding activity");
        getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_INTEREST);

        interest_edit = interest;
    }

    public void setInterestText()
    {
        interest_edit.setText(this.interest);
    }

    public void addSubSchedule()
    {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.sub_schedule_adding, null);

        task_title = (EditText)addView.findViewById(R.id.title);

       final EditText task_time = (EditText)addView.findViewById(R.id.time);

        final View.OnClickListener timePickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                timePick(task_time);
            }
        };

        task_time.setOnClickListener(timePickListener);

        comment = (EditText)addView.findViewById(R.id.comment);

        final EditText interest = (EditText)addView.findViewById(R.id.interest);

        final View.OnClickListener interestPickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setInterestText(interest);
            }
        };

        interest.setOnClickListener(interestPickListener);

        if (subScheduleLayout != null)
        {
            subScheduleLayout.addView(addView);
        }

        ImageView buttonRemove = (ImageView) addView.findViewById(R.id.sub_schedule_remove);

        final View.OnClickListener thisListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((LinearLayout)addView.getParent()).removeView(addView);
            }
        };

        buttonRemove.setOnClickListener(thisListener);
    }

    private boolean checkForSubTasks()
    {
        if (subScheduleLayout != null)
        {
            return true;
        }
        return false;
    }

    private void getAllSubSchedules()
    {
        int childCount = subScheduleLayout.getChildCount();

        titles = new ArrayList<>();
        times = new ArrayList<>();
        comments = new ArrayList<>();
        interests = new ArrayList<>();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = subScheduleLayout.getChildAt(i);

            EditText childTitle = (EditText) thisChild.findViewById(R.id.title);
            EditText childTime = (EditText) thisChild.findViewById(R.id.time);
            EditText childComment = (EditText) thisChild.findViewById(R.id.comment);
            EditText childInterest = (EditText) thisChild.findViewById(R.id.interest);

            String contentTitle = childTitle.getText().toString();
            String contentTime = childTime.getText().toString();
            String contentComment = childComment.getText().toString();
            String contentInterest = childInterest.getText().toString();

            titles.add(contentTitle);
            times.add(contentTime);
            comments.add(contentComment);
            interests.add(contentInterest);
        }
    }

    @Override
    public void onFragmentClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sub_schedule_add:
                addSubSchedule();
                break;
        }
    }

    public void setInterest(String interest)
    {
        this.interest = interest;
    }

}
