package com.example.jiafang_jiang.todolist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jiafang_jiang.todolist.R;
import com.example.jiafang_jiang.todolist.model.TodoListItem;

public class EditItemActivity extends AppCompatActivity {

    private EditText editBar;
    private int itemPos;
    private TodoListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editBar = (EditText) findViewById(R.id.etItem);

        Intent editIntent = getIntent();
        itemPos = editIntent.getIntExtra("pos", 0);
        item = editIntent.getParcelableExtra("content");
        editBar.setText(item.text);
        editBar.setSelection(item.text.length());
    }

    public void onSubmit(View view) {
        item.text = editBar.getText().toString();
        Intent mainActivityIntent = new Intent();
        mainActivityIntent.putExtra("content", item);
        mainActivityIntent.putExtra("pos", itemPos);
        setResult(RESULT_OK, mainActivityIntent);
        finish();
    }
}
