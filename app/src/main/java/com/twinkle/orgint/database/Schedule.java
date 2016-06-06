package com.twinkle.orgint.database;

import java.util.ArrayList;
import java.util.List;

public class Schedule
{
    // Labels table name
    public static final String TABLE = "Schedule";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String DAY = "day";
    public static final String TYPE = "type";
    public static final String DATE = "date";

    // property help us to keep data
    private int schedule_ID;
    private String day;
    private String type;
    private String date;

    private List<Sub_schedule> sub_schedules;

    public Schedule(List<Sub_schedule> sub_schedules)
    {
        this.sub_schedules = new ArrayList<>();
        this.sub_schedules = sub_schedules;
    }

    public int getSchedule_ID() {
        return schedule_ID;
    }

    public void setSchedule_ID(int schedule_ID) {
        this.schedule_ID = schedule_ID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Sub_schedule getSub_schedule(int position)
    {
        return sub_schedules.get(position);
    }

    public int getSubSchedulesCount()
    {
       return sub_schedules.size();
    }

}
