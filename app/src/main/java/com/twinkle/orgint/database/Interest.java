package com.twinkle.orgint.database;


public class Interest
{
    // Labels table name
    public static final String TABLE = "Schedules_Tab";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String IMPORTANCE = "importance";

    // property help us to keep data
    private int interest_ID;
    private String title;
    private boolean importance;


    public int getInterest_ID()
    {
        return interest_ID;
    }

    public void setInterest_ID(int interest_ID)
    {
        this.interest_ID = interest_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isImportance()
    {
        return importance;
    }

    public void setImportance(boolean importance)
    {
        this.importance = importance;
    }




}
