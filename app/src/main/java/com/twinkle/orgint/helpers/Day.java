package com.twinkle.orgint.helpers;

public enum Day
{
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

        private String day;

        Day(String day)
        {
            this.day = day;
        }

        public String getDay()
        {
            return day;
        }
}
