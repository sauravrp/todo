package com.practice.todo.datamgr;

import android.os.Debug;
import android.util.Log;

import com.practice.todo.model.Task;

import java.util.ArrayList;

/**
 * Created by sauravpandey on 2/20/15.
 */
public class DataMgr
{


    private static int sId;

    private ArrayList<Task> mTasks;
    private static DataMgr sInstance = null;

    private  DataMgr()
    {
        mTasks = new ArrayList<Task>();

        // test code
      //  Task task1 = new Task("Task A");

        // Task mTasks.add(task1);

        sId = 0;
    }

    public static DataMgr getInstance()
    {
        if(sInstance == null)
        {
            sInstance = new DataMgr();
        }

        return sInstance;
    }

    public ArrayList<Task> getTasks()
    {
        return mTasks;
    }

    public void addTask(final Task task)
    {
        sId++;
        task.setId(sId);
        mTasks.add(task);
    }

    public void addTask(final String taskTitle)
    {
        Task task = new Task(taskTitle);
        sId++;
        task.setId(sId);
        mTasks.add(task);
    }

    public void updateTask(int id, boolean checkedStatus)
    {
        for(Task task : mTasks)
        {
            if(task.getId() == id)
            {
                task.setIsChecked(checkedStatus);
                break;
            }
        }

        DumpData();

    }

    public static void DumpData()
    {
        Log.d("Tasks", "-----------------------DumpData-------------------------------");

        ArrayList<Task> tasks = DataMgr.getInstance().getTasks();
        for(Task task : tasks)
        {
            Task.DumpTask(task);
        }

        Log.d("Tasks", "------------------------EndOfDumpData------------------------------");
    }




}
