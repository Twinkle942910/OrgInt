package com.twinkle.orgint.database;

public class Sub_schedule
{
    // Labels table name
    public static final String TABLE = "Sub_schedule";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String SCHEDULE_ID = "id_schedule";
    public static final String TIME = "time";
    public static final String TYPE = "type";
    public static final String TASK = "task";
    public static final String COMMENT = "comment";
    public static final String IMPORTANCE = "importance";
    public static final String IMPORTANCE_VALUE = "importance_value";

    // property help us to keep data
    private int sub_schedule_ID;
    private int schedule_ID;
    private String time;
    private String type;
    private String task;
    private String comment;
    private String importance;
    private int importance_value;

    public int getSub_schedule_ID() {
        return sub_schedule_ID;
    }

    public void setSub_schedule_ID(int sub_schedule_ID) {
        this.sub_schedule_ID = sub_schedule_ID;
    }

    public int getSchedule_ID() {
        return schedule_ID;
    }

    public void setSchedule_ID(int schedule_ID) {
        this.schedule_ID = schedule_ID;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
