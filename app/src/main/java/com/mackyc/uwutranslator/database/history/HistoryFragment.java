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
import androidx.lifecycle.ViewModelProvider;
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
    private List<HistoryObject> historyObjectList;
    private Context context;
    private HistoryObjectModel historyObjectModel;
    private DialogHandler deleteAllConfirmDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        adapter = new HistoryObjectAdapter(getContext());

        HistoryObjectFactory factory = new HistoryObjectFactory(requireActivity().getApplication());
        historyObjectModel = new ViewModelProvider(this, factory).get(HistoryObjectModel.class);

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
        clearBtn.setOnClickListener(btnClearView -> deleteAllConfirmDialog.show(getParentFragmentManager(), deleteAllConfirmDialog.TAG));

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        DividerItemDecoration divider =
                new DividerItemDecoration(historyList.getContext(), manager.getOrientation());

        historyList.setAdapter(adapter);
        historyList.addItemDecoration(divider);
        historyList.setLayoutManager(manager);

        SwipeCallback callback = new SwipeCallback(adapter);

        progressBar.setVisibility(View.GONE);
        historyList.setVisibility(View.VISIBLE);

        historyObjectModel.getAllObjects().observe(getViewLifecycleOwner(), objects -> {
            clearBtn.setEnabled((objects != null ? objects.size() : 0) != 0);
            adapter.submitList(objects);
            historyObjectList = objects;
        });

        adapter.setOnItemClickAdapter(position -> {
            String strCopy = historyObjectList.get(position).getTranslated();
            ClipboardHandler.addPlainText(context, strCopy);
        });

        callback.setOnSwipeListener(position -> {
            final HistoryObject obj = adapter.getObjectAt(position);
            historyObjectModel.delete(obj);
            Snackbar.make(view, getString(R.string.history_deleted_item), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.history_deleted_undo), view1 -> historyObjectModel.insert(obj))
                    .show();
        });

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(historyList);
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
