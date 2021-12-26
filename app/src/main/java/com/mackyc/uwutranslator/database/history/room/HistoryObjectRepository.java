package com.mackyc.uwutranslator.database.history.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mackyc.uwutranslator.database.history.HistoryObject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryObjectRepository {

    private final HistoryObjectDAO dao;
    private final LiveData<List<HistoryObject>> mAllObjects;
    private final ExecutorService executorService;

    public HistoryObjectRepository(Application application) {
        HistoryObjectRoomDatabase db = HistoryObjectRoomDatabase.getDatabase(application);
        dao = db.historyObjectDAO();
        mAllObjects = dao.getAllEntries();

        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<HistoryObject>> getAllObjects(){
        return mAllObjects;
    }

    public void delete(HistoryObject object) {
        executorService.execute(() -> dao.delete(object));
    }

    public void insert(HistoryObject object) {
        executorService.execute(() -> dao.insert(object));
    }

    public void deleteAll() {
        executorService.execute(dao::deleteAll);
    }
}
