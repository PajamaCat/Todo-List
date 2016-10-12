package com.example.jiafang_jiang.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jiafang_jiang.todolist.R;
import com.example.jiafang_jiang.todolist.model.TodoListItem;

import java.util.List;

public class TodoListAdapter extends ArrayAdapter<TodoListItem> {
    public TodoListAdapter(Context context, int resource, List<TodoListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoListItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvTitle);

        // Populate the data into the template view using the data object
        tvName.setText(item.text);

        return convertView;
    }
}
