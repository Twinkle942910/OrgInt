package com.twinkle.orgint.database;

public class Sub_task
{
    // Labels table name
    public static final String TABLE = "Sub_task";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TODO_ID = "todo_id";
    public static final String CONTENT = "content";
    public static final String ISDONE = "isDone";

    // property help us to keep data
    private int sub_task_id;
    private int todo_ID;
    private String content;
    private boolean isDone;

    public int getID()
    {
        return sub_task_id;
    }

    public void setID(int sub_schedule_ID)
    {
        this.sub_task_id = sub_schedule_ID;
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

    public int getTodo_ID() {
        return todo_ID;
    }

    public void setTodo_ID(int todo_ID) {
        this.todo_ID = todo_ID;
    }
}
