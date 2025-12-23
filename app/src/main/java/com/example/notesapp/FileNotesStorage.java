package com.example.notesapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileNotesStorage implements NotesStorage {

    private static final String FILE_NAME = "notes.json";
    private final Context context;

    public FileNotesStorage(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> loadNotes() {
        List<Note> notes = new ArrayList<>();
        String json = "[]";

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            json = sb.toString();

        } catch (Exception ignored) { }

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

        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(arr.toString().getBytes());
        } catch (Exception ignored) { }
    }
}
