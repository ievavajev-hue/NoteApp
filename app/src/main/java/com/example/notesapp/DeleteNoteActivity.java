package com.example.notesapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DeleteNoteActivity extends AppCompatActivity {

    private NotesStorage storage;
    private List<Note> notes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        // Screen title
        setTitle(getString(R.string.title_delete_note));

        // Storage implementation (Prefs or File)
        storage = StorageManager.getStorage(this);

        // ListView setup
        ListView listDeleteNotes = findViewById(R.id.listDeleteNotes);
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );
        listDeleteNotes.setAdapter(adapter);

        // Delete note on tap
        listDeleteNotes.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < notes.size()) {
                notes.remove(position);
                storage.saveNotes(notes);

                Toast.makeText(
                        this,
                        getString(R.string.toast_note_deleted),
                        Toast.LENGTH_SHORT
                ).show();

                refresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        notes = storage.loadNotes();

        ArrayList<String> names = new ArrayList<>();
        for (Note note : notes) {
            names.add(note.getName());
        }

        adapter.clear();
        adapter.addAll(names);
        adapter.notifyDataSetChanged();
    }
}
