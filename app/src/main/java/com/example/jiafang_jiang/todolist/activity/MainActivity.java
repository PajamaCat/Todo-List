package com.example.jiafang_jiang.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jiafang_jiang.todolist.R;
import com.example.jiafang_jiang.todolist.adapter.TodoListAdapter;
import com.example.jiafang_jiang.todolist.database.TodoListDatabaseHelper;
import com.example.jiafang_jiang.todolist.model.TodoListItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoListItem> items;
    private TodoListAdapter itemsAdapter;
    private ListView lvItems;
    private TodoListDatabaseHelper database;

    private final int EDIT_TEXT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = TodoListDatabaseHelper.getInstance(this);
        items = database.getAllItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new TodoListAdapter(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT) {
            TodoListItem editedItem = data.getParcelableExtra("content");
            items.set(data.getIntExtra("pos", 0), editedItem);
            itemsAdapter.notifyDataSetChanged();
            database.updateItem(editedItem);
        }
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        TodoListItem newItem = new TodoListItem();
        newItem.text = etNewItem.getText().toString();
        items.add(newItem);
        database.addItem(newItem);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> adapter, View item, int pos, long id) {
                        TodoListItem removedItem = items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        database.deleteItem(removedItem);
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> adapterView, View view, int pos, long id) {
                        launchEditView(pos, items.get(pos));
                    }
                }
        );
    }

    private void launchEditView(int pos, TodoListItem content) {
        Intent editItem = new Intent(MainActivity.this, EditItemActivity.class);
        editItem.putExtra("pos", pos);
        editItem.putExtra("content", content);
        startActivityForResult(editItem, EDIT_TEXT);
    }

}
