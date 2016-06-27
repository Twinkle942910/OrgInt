package com.twinkle.orgint.database;

public class Planning
{
    private String category;
    private int image;

    private int schedules_count;
    private int todo_count;
    private int work_tasks_count;
    private int birthdays_count;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getSchedules_count() {
        return schedules_count;
    }

    public void setSchedules_count(int schedules_count) {
        this.schedules_count = schedules_count;
    }

    public int getTodo_count() {
        return todo_count;
    }

    public void setTodo_count(int todo_count) {
        this.todo_count = todo_count;
    }

    public int getWork_tasks_count() {
        return work_tasks_count;
    }

    public void setWork_tasks_count(int work_tasks_count) {
        this.work_tasks_count = work_tasks_count;
    }

    public int getBirthdays_count() {
        return birthdays_count;
    }

    public void setBirthdays_count(int birthdays_count) {
        this.birthdays_count = birthdays_count;
    }
}
