package com.mackyc.uwutranslator.database.history.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mackyc.uwutranslator.database.history.HistoryObject;

import java.util.List;

public class HistoryObjectRepository {

    private HistoryObjectDAO dao;
    private LiveData<List<HistoryObject>> mAllObjects;

    public HistoryObjectRepository(Application application) {
        HistoryObjectRoomDatabase db = HistoryObjectRoomDatabase.getDatabase(application);
        dao = db.historyObjectDAO();
        mAllObjects = dao.getAllEntries();
    }

    public LiveData<List<HistoryObject>> getAllObjects(){
        return mAllObjects;
    }

    public void delete(HistoryObject object) {
        new DeleteAsyncTask(dao).execute(object);
    }

    public void insert(HistoryObject object) {
        new InsertAsyncTask(dao).execute(object);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(dao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<HistoryObject, Void, Void> {

        private HistoryObjectDAO mAsyncTaskDAO;

        InsertAsyncTask(HistoryObjectDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(HistoryObject... historyObjects) {
            mAsyncTaskDAO.insert(historyObjects[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private HistoryObjectDAO mAsyncTaskDAO;

        DeleteAllAsyncTask(HistoryObjectDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDAO.deleteAll();
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<HistoryObject, Void, Void> {

        private HistoryObjectDAO mAsyncTaskDAO;

        DeleteAsyncTask(HistoryObjectDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(HistoryObject... historyObjects) {
            mAsyncTaskDAO.delete(historyObjects[0]);
            return null;
        }
    }

}
