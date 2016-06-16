package com.twinkle.orgint.database;

import java.util.ArrayList;
import java.util.List;

public class WorkTask
{
    // Labels table name
    public static final String TABLE = "WorkTask";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String TIME = "time";

    public static final String COMMENT = "comment";
    public static final String INTEREST = "interest";

    // property help us to keep data
    private int schedule_ID;
    private String task;
    private String type;
    private String date;
    private String time;
    private String comment = "";
    private String interest = "";

    private List<Sub_task> sub_tasks;

    public WorkTask(List<Sub_task> sub_tasks) {
        this.sub_tasks = new ArrayList<>();
        this.sub_tasks = sub_tasks;
    }

    public WorkTask()
    {
        this.sub_tasks = new ArrayList<>();
    }

    public int getSchedule_ID() {
        return schedule_ID;
    }

    public void setSchedule_ID(int schedule_ID) {
        this.schedule_ID = schedule_ID;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Sub_task getSub_task(int position) {
        return sub_tasks.get(position);
    }

    public List<Sub_task> getSub_tasks() {
        return sub_tasks;
    }

    public void setSub_tasks(List<Sub_task> sub_tasks) {
        this.sub_tasks = sub_tasks;
    }

    public int getSubTasksCount() {
        return sub_tasks.size();
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
