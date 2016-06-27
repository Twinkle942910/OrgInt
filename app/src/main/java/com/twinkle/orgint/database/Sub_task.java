package com.twinkle.orgint.database;

public class Sub_task
{
    // Labels table name
    public static final String TABLE = "Sub_task";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TODO_ID = "todo_id";
    public static final String WORK_TASK_ID = "work_task_id";
    public static final String BIRTHDAY_ID = "birthday_id";
    public static final String CONTENT = "content";
    public static final String ISDONE = "isDone";

    // property help us to keep data
    private int sub_task_id;
    private int todo_ID;
    private int work_task_id;
    private int birthday_id;
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


    public int getBirthday_id() {
        return birthday_id;
    }

    public void setBirthday_id(int birthday_id) {
        this.birthday_id = birthday_id;
    }

    public int getWork_task_id() {
        return work_task_id;
    }

    public void setWork_task_id(int work_task_id) {
        this.work_task_id = work_task_id;
    }
}
