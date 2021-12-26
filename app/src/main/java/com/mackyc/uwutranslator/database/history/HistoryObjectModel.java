package com.mackyc.uwutranslator.database.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mackyc.uwutranslator.database.history.room.HistoryObjectRepository;

import java.util.List;

public class HistoryObjectModel extends AndroidViewModel {

    final private HistoryObjectRepository repo;
    final private LiveData<List<HistoryObject>> allObjects;

    public HistoryObjectModel(@NonNull Application application) {
        super(application);
        repo = new HistoryObjectRepository(application);
        allObjects = repo.getAllObjects();
    }

    LiveData<List<HistoryObject>> getAllObjects() {
        return allObjects;
    }

    public void insert(HistoryObject obj) {
        repo.insert(obj);
    }

    void delete(HistoryObject obj) {
        repo.delete(obj);
    }

    void deleteAll() {
        repo.deleteAll();
    }
}
