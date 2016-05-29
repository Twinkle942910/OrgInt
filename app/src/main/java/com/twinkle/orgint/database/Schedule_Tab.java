package com.twinkle.orgint.database;


public class Schedule_Tab
{
    // Labels table name
    public static final String TABLE = "Schedules_Tab";

    // Labels Table Columns names
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final String URGENT_IMPORTANT_COUNT = "urgent_important_count";
    public static final String NOT_URGENT_IMPORTANT_COUNT = "not_urgent_important_count";
    public static final String URGENT_NOT_IMPORTANT_COUNT = "urgent_not_important_count";
    public static final String NOT_URGENT_NOT_IMPORTANT_COUNT = "not_urgent_not_important_count";

    // property help us to keep data
    private int schedule_tab_ID;
    private String title;
    private int image;
    private int urgent_important_count;
    private int not_urgent_important_count;
    private int urgent_not_important_count;
    private int not_urgent_not_important_count;

    public int getSchedule_tab_ID()
    {
        return schedule_tab_ID;
    }

    public void setSchedule_tab_ID(int schedule_tab_ID)
    {
        this.schedule_tab_ID = schedule_tab_ID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public int getUrgent_important_count()
    {
        return urgent_important_count;
    }

    public void setUrgent_important_count(int urgent_important_count)
    {
        this.urgent_important_count = urgent_important_count;
    }

    public int getNot_urgent_important_count()
    {
        return not_urgent_important_count;
    }

    public void setNot_urgent_important_count(int not_urgent_important_count)
    {
        this.not_urgent_important_count = not_urgent_important_count;
    }

    public int getUrgent_not_important_count()
    {
        return urgent_not_important_count;
    }

    public void setUrgent_not_important_count(int urgent_not_important_count)
    {
        this.urgent_not_important_count = urgent_not_important_count;
    }

    public int getNot_urgent_not_important_count()
    {
        return not_urgent_not_important_count;
    }

    public void setNot_urgent_not_important_count(int not_urgent_not_important_count)
    {
        this.not_urgent_not_important_count = not_urgent_not_important_count;
    }
}
