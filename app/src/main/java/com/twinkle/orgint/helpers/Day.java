package com.twinkle.orgint.helpers;

//ToDo: Work on it later!
public enum Day
{
    MONDAY(1, "Monday"),
    TUESDAY(2, "Tuesday"),
    WEDNESDAY(3, "Wednesday"),
    THURSDAY(4, "Thursday"),
    FRIDAY(5, "Friday"),
    SATURDAY(6, "Saturday"),
    SUNDAY(7, "Sunday");

    private final int code;
    private final String day;

    Day(int code, String day)
    {
        this.code = code;
        this.day = day;
    }

    public int getCode()
    {
        return code;
    }

    public String getDay()
    {
        return day;
    }

    public static String getClassName()
    {
        return Day.class.getName();
    }
}
