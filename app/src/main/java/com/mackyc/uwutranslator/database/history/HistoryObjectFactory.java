package com.mackyc.uwutranslator.database.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HistoryObjectFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application ctx;

    public HistoryObjectFactory(@NonNull Application ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == HistoryObjectModel.class) {
            return (T) new HistoryObjectModel(ctx);
        }
        return super.create(modelClass);
    }
}
