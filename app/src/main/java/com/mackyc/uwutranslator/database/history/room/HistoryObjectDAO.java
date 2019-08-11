package com.mackyc.uwutranslator.database.history.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mackyc.uwutranslator.database.history.HistoryObject;

import java.util.List;

@Dao
public interface HistoryObjectDAO {

    @Insert
    void insert(HistoryObject obj);

    @Delete
    void delete(HistoryObject obj);

    @Query("DELETE FROM history_table")
    void deleteAll();

    @Query("SELECT * from history_table ORDER BY timestamp DESC")
    LiveData<List<HistoryObject>> getAllEntries();

}
