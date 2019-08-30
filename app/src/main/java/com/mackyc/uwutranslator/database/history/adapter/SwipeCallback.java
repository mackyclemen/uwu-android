package com.mackyc.uwutranslator.database.history.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {

    public interface OnSwipeListener {
        void onSwipe(int position);
    }

    private OnSwipeListener listener;

    public SwipeCallback(HistoryObjectAdapter adapter) {
        super(0, ItemTouchHelper.START | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        listener.onSwipe(viewHolder.getAdapterPosition());
    }

    public void setOnSwipeListener(OnSwipeListener listener) {
        this.listener = listener;
    }
}
