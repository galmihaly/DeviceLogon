package hu.unideb.inf.devicelogon.roomdatabase;

import android.content.Context;

import androidx.room.RoomDatabase;

public abstract class LocalROOMDatabase extends RoomDatabase {

    private static LocalROOMDatabase INSTANCE;

    public static LocalROOMDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(), LocalROOMDatabase.class, "LocalROOMDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static LocalROOMDatabase getInstance() {
        if (INSTANCE == null) return null;

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}