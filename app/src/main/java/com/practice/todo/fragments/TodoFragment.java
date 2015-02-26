package com.practice.todo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.practice.todo.R;
import com.practice.todo.datamgr.DataMgr;
import com.practice.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by sauravpandey on 2/20/15.
 */
public class TodoFragment extends Fragment
{
    @InjectView(R.id.addTask)
    EditText mNewTaskEditText;

    @InjectView(R.id.taskListView)
    ListView mTaskListView;

    @InjectView(R.id.addButton)
    Button mAddButton;

    @OnClick(R.id.addButton)
    public void onAddButtonClicked(Button buttonView)
    {
        String newTaskText =  mNewTaskEditText.getText().toString();

        DataMgr.getInstance().addTask(newTaskText);

        mListAdapter.notifyDataSetChanged();
    }

    private TaskAdapter mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.inject(this, rootView);
        mAddButton.setEnabled(false);
        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        toggleAddButton(mNewTaskEditText.getText().toString());

        mNewTaskEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String typedText = s.toString();
                toggleAddButton(typedText);
            }
        });

        ArrayList<Task> tasks = DataMgr.getInstance().getTasks();

        mListAdapter = new TaskAdapter(getActivity(), tasks);
        mTaskListView.setAdapter(mListAdapter);
    }

    private void toggleAddButton(final String typedText)
    {
        if(TextUtils.isEmpty(typedText))
        {
            mAddButton.setEnabled(false);
        }
        else
        {
            mAddButton.setEnabled(true);
        }
    }

    private static class TaskAdapter extends ArrayAdapter<Task>
    {

        private TaskAdapter(Context context, List<Task> objects)
        {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_task, null);
            }

            final Task task = getItem(position);

            Log.d("TodoFragment", "position = " + position);

            Task.DumpTask(task);

            CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.checkBox);
            TextView taskName = (TextView)convertView.findViewById(R.id.taskName);

            checkbox.setTag(task);

            checkbox.setChecked(task.getIsChecked());
            taskName.setText(task.getTitle());

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if(buttonView.getTag() != null && (buttonView.getTag() instanceof Task))
                    {
                        Task checkedTask = (Task) buttonView.getTag();
                        if(checkedTask.getIsChecked() != isChecked)
                        {
                            Log.d("TodoFragment", "checkbox->onCheckedChanged called for task.id = " + checkedTask.getId());
                            checkedTask.setIsChecked(isChecked);
                        //    DataMgr.getInstance().updateTask(checkedTask.getId(), isChecked);
                        }
                    }

                }
            });

            return convertView;
        }
    }
}
