package com.example.notesapp;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {

    private static final String APP_PREFS = "app_settings";
    private static final String KEY_STORAGE = "storage_type";

    public enum StorageType { PREFS, FILE }

    public static void setStorageType(Context ctx, StorageType type) {
        ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_STORAGE, type.name())
                .apply();
    }

    public static NotesStorage getStorage(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        String type = sp.getString(KEY_STORAGE, StorageType.PREFS.name());

        if (StorageType.FILE.name().equals(type)) {
            return new FileNotesStorage(ctx);
        }
        return new PrefsNotesStorage(ctx);
    }
}
