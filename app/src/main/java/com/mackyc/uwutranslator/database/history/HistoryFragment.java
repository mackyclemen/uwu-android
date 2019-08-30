package com.mackyc.uwutranslator.database.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mackyc.uwutranslator.R;
import com.mackyc.uwutranslator.clipboard.ClipboardHandler;
import com.mackyc.uwutranslator.database.history.adapter.HistoryObjectAdapter;
import com.mackyc.uwutranslator.database.history.adapter.SwipeCallback;
import com.mackyc.uwutranslator.dialogs.DialogHandler;

import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryObjectAdapter adapter;
    private FragmentManager manager;
    private List<HistoryObject> historyObjectList;
    private Context context;
    private HistoryObjectModel historyObjectModel;
    private DialogHandler deleteAllConfirmDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        manager = getFragmentManager();
        adapter = new HistoryObjectAdapter(getContext());
        historyObjectModel = ViewModelProviders.of(this).get(HistoryObjectModel.class);

        return inflater.inflate(R.layout.fragment_main_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        deleteAllConfirmDialog = new DialogHandler();
        deleteAllConfirmDialog.setMessage(getString(R.string.history_clear_dialog_msg));
        deleteAllConfirmDialog.setPositiveButton(getString(R.string.history_clear_dialog_confirm));
        deleteAllConfirmDialog.setNegativeButton(getString(R.string.history_clear_dialog_cancel));
        deleteAllConfirmDialog.setOnDialogAnswerListener(new DialogHandler.OnDialogAnswerListener() {

            @Override
            public void onPositiveClick() {
                historyObjectModel.deleteAll();
                Snackbar.make(view, "History cleared", Snackbar.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onNeutralClick() {
                // no neutral clicks
            }

            @Override
            public void onNegativeClick() {
                // ignore negative clicks
            }
        });

        final RecyclerView historyList = view.findViewById(R.id.recycler_history_list);
        ProgressBar progressBar = view.findViewById(R.id.recycler_history_progress);

        final Button clearBtn = view.findViewById(R.id.history_container_clear_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                deleteAllConfirmDialog.show(manager, "DIALOG_DELETEALL");
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        DividerItemDecoration divider =
                new DividerItemDecoration(historyList.getContext(), manager.getOrientation());

        historyList.setAdapter(adapter);
        historyList.addItemDecoration(divider);
        historyList.setLayoutManager(manager);

        SwipeCallback callback = new SwipeCallback(adapter);

        progressBar.setVisibility(View.GONE);
        historyList.setVisibility(View.VISIBLE);

        historyObjectModel.getAllObjects()
                .observe(getViewLifecycleOwner(), new Observer<List<HistoryObject>>() {
                    @Override
                    public void onChanged(@Nullable final List<HistoryObject> objects) {
                        if((objects != null ? objects.size() : 0) == 0) {
                            clearBtn.setEnabled(false);
                        } else {
                            clearBtn.setEnabled(true);
                        }
                        adapter.setItems(objects);
                        adapter.notifyDataSetChanged();

                        historyObjectList = objects;
                    }
                });

        adapter.setOnItemClickAdapter(new HistoryObjectAdapter.OnItemClickAdapter() {
            @Override
            public void onItemClick(int position) {
                String strCopy = historyObjectList.get(position).getTranslated();
                ClipboardHandler.addPlainText(context, strCopy);
            }
        });

        callback.setOnSwipeListener(new SwipeCallback.OnSwipeListener() {
            @Override
            public void onSwipe(int position) {
                final HistoryObject obj = adapter.getItem(position);
                historyObjectModel.delete(obj);

                adapter.notifyDataSetChanged();

                Snackbar.make(view, getString(R.string.history_deleted_item), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.history_deleted_undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                historyObjectModel.insert(obj);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(historyList);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
