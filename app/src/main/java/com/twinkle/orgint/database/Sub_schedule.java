package com.twinkle.orgint.database;

public class Sub_schedule
{
    // Labels table name
    public static final String TABLE = "Sub_schedule";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TIME = "time";
    public static final String TYPE = "type";
    public static final String TASK = "task";

    // property help us to keep data
    private int sub_schedule_ID;
    private String time;
    private String type;
    private String task;

    public int getSub_schedule_ID() {
        return sub_schedule_ID;
    }

    public void setSub_schedule_ID(int sub_schedule_ID) {
        this.sub_schedule_ID = sub_schedule_ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
