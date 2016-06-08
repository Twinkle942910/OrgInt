package com.twinkle.orgint.database;

public class Sub_task
{
    // Labels table name
    public static final String TABLE = "Sub_task";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String ISDONE = "isDone";

    // property help us to keep data
    private int schedule_ID;
    private String content;
    private boolean isDone;

    public int getSchedule_ID()
    {
        return schedule_ID;
    }

    public void setSchedule_ID(int schedule_ID)
    {
        this.schedule_ID = schedule_ID;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public void setDone(boolean done)
    {
        isDone = done;
    }
}
