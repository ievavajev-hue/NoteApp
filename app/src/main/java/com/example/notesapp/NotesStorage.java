package com.example.notesapp;

import java.util.List;

public interface NotesStorage {
    List<Note> loadNotes();
    void saveNotes(List<Note> notes);
}
