package com.practice.todo.model;

import android.util.Log;

/**
 * Created by sauravpandey on 2/20/15.
 */
public class Task
{
    private int mId = 0;
    private String mTitle;
    private boolean mIsChecked;

    public Task(String title, boolean isChecked)
    {
        mTitle = title;
        mIsChecked = isChecked;
    }

    public Task(String title)
    {
        mTitle = title;
        mIsChecked = false;
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int mId)
    {
        this.mId = mId;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public boolean getIsChecked()
    {
        return mIsChecked;
    }

    public void setIsChecked(boolean mIsChecked)
    {
        this.mIsChecked = mIsChecked;
    }

    public static void DumpTask(Task task)
    {
        Log.d("Task", "task.id = " + task.getId() + ", task.title = " + task.getTitle() +
                ", task.isChecked = " + task.getIsChecked());
    }
}
