package com.example.notesapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrefsNotesStorage implements NotesStorage {

    private static final String PREFS_NAME = "notes_prefs";
    private static final String KEY_NOTES_JSON = "notes_json";

    private final Context context;

    public PrefsNotesStorage(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> loadNotes() {
        List<Note> notes = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(KEY_NOTES_JSON, "[]");

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                notes.add(new Note(obj.getString("name"), obj.getString("content")));
            }
        } catch (Exception ignored) { }

        return notes;
    }

    @Override
    public void saveNotes(List<Note> notes) {
        JSONArray arr = new JSONArray();

        try {
            for (Note n : notes) {
                JSONObject obj = new JSONObject();
                obj.put("name", n.getName());
                obj.put("content", n.getContent());
                arr.put(obj);
            }
        } catch (Exception ignored) { }

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_NOTES_JSON, arr.toString())
                .apply();
    }
}
