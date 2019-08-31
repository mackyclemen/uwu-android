package com.mackyc.uwutranslator.database.history.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mackyc.uwutranslator.database.history.HistoryObject;

@Database(entities = {HistoryObject.class}, version = 1, exportSchema = false)
@TypeConverters({com.mackyc.uwutranslator.database.history.converters.DateTimeConverter.class})
public abstract class HistoryObjectRoomDatabase extends RoomDatabase {
    public abstract HistoryObjectDAO historyObjectDAO();

    public static volatile HistoryObjectRoomDatabase INSTANCE;

    static HistoryObjectRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {

            synchronized (HistoryObjectRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    HistoryObjectRoomDatabase.class,
                                                    "history_db").build();

                }
            }

        }

        return INSTANCE;
    }

}


