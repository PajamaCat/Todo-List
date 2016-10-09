package com.example.jiafang_jiang.to_dolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText editBar;
    private int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editBar = (EditText) findViewById(R.id.etItem);

        Intent editIntent = getIntent();
        itemPos = editIntent.getIntExtra("pos", 0);
        String content = editIntent.getStringExtra("content");
        editBar.setText(content);
        editBar.setSelection(content.length());
    }

    public void onSubmit(View view) {
        Intent mainActivityIntent = new Intent();
        mainActivityIntent.putExtra("content", editBar.getText().toString());
        mainActivityIntent.putExtra("pos", itemPos);
        setResult(RESULT_OK, mainActivityIntent);
        finish();
    }
}
