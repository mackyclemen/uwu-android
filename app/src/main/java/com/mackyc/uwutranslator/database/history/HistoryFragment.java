package com.mackyc.uwutranslator.database.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mackyc.uwutranslator.R;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyList;
    private HistoryObjectModel historyObjectModel;
    private HistoryObjectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyList = view.findViewById(R.id.recycler_history_list);

        adapter = new HistoryObjectAdapter(getContext());
        historyList.setAdapter(adapter);
        historyList.setLayoutManager(new LinearLayoutManager(getContext()));

        historyObjectModel = ViewModelProviders.of(this).get(HistoryObjectModel.class);
        historyObjectModel.getAllObjects().observe(getViewLifecycleOwner(), new Observer<List<HistoryObject>>() {
            @Override
            public void onChanged(@Nullable final List<HistoryObject> objects) {
                adapter.setItems(objects);
            }
        });
    }

}
