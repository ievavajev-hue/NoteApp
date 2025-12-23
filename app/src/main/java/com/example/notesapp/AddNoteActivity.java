package com.example.notesapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private EditText txtNoteName;
    private EditText txtNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setTitle(getString(R.string.title_add_note));

        txtNoteName = findViewById(R.id.txtNoteName);
        txtNoteContent = findViewById(R.id.txtNoteContent);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String name = txtNoteName.getText().toString().trim();
        String content = txtNoteContent.getText().toString().trim();

        // Validate empty fields (LAB requirement)
        if (name.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_empty_field), Toast.LENGTH_SHORT).show();
            return;
        }

        NotesStorage storage = StorageManager.getStorage(this);
        List<Note> notes = storage.loadNotes();
        notes.add(new Note(name, content));
        storage.saveNotes(notes);

        Toast.makeText(this, getString(R.string.toast_note_saved), Toast.LENGTH_SHORT).show();
        finish();
    }
}
