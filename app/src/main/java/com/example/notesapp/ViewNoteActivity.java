package com.example.notesapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_CONTENT = "extra_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);

        TextView txtTitle = findViewById(R.id.txtViewTitle);
        TextView txtContent = findViewById(R.id.txtViewContent);

        txtTitle.setText(name != null ? name : "");
        txtContent.setText(content != null ? content : "");

        setTitle(name != null ? name : getString(R.string.app_name));
    }
}
