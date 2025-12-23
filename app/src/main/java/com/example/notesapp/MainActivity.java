package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listNotes;
    private ArrayAdapter<String> adapter;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNotes = findViewById(R.id.listNotes);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnDelete = findViewById(R.id.btnDelete);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listNotes.setAdapter(adapter);

        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddNoteActivity.class))
        );

        btnDelete.setOnClickListener(v ->
                startActivity(new Intent(this, DeleteNoteActivity.class))
        );

        // Open a full screen to view the note (instead of Toast)
        listNotes.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < notes.size()) {
                Note note = notes.get(position);

                Intent intent = new Intent(this, ViewNoteActivity.class);
                intent.putExtra(ViewNoteActivity.EXTRA_NAME, note.getName());
                intent.putExtra(ViewNoteActivity.EXTRA_CONTENT, note.getContent());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAndRefresh();
    }

    private void loadAndRefresh() {
        NotesStorage storage = StorageManager.getStorage(this);
        notes = storage.loadNotes();

        ArrayList<String> names = new ArrayList<>();
        for (Note n : notes) {
            names.add(n.getName());
        }

        adapter.clear();
        adapter.addAll(names);
        adapter.notifyDataSetChanged();
    }
}
