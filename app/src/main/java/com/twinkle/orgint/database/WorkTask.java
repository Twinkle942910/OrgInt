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
    public static final String IMPORTANCE = "importance";
    public static final String IMPORTANCE_VALUE = "importance_value";

    // property help us to keep data
    private int workTask_ID;
    private String task;
    private String type;
    private String date;
    private String time;
    private String comment = "";
    private String importance = "";
    private int importance_value;

    private List<Sub_task> sub_tasks;

    public WorkTask()
    {
        this.sub_tasks = new ArrayList<>();
    }

    public int getID() {
        return workTask_ID;
    }

    public void setID(int workTask_ID) {
        this.workTask_ID = workTask_ID;
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

    public void setSub_task(Sub_task sub_task) {
        sub_tasks.add(sub_task);
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public int getImportance_value() {
        return importance_value;
    }

    public void setImportance_value(int importance_value) {
        this.importance_value = importance_value;
    }
}
