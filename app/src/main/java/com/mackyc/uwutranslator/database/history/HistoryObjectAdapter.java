package com.mackyc.uwutranslator.database.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mackyc.uwutranslator.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryObjectAdapter extends RecyclerView.Adapter<HistoryObjectAdapter.HistoryObjectVH> {

    private final LayoutInflater inflater;
    private List<HistoryObject> objects;

    HistoryObjectAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryObjectVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.history_list_item, parent, false);
        return new HistoryObjectVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryObjectVH holder, int position) {
        if(objects != null) {
            HistoryObject current = objects.get(position);

            String pattern = "dd MMM hh:mmaa";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());

            holder.historyTime.setText(sdf.format(current.getTimestamp()));
            holder.historyRaw.setText(current.getRaw());
            holder.historyTranslated.setText(current.getTranslated());
        }
    }

    @Override
    public int getItemCount() {
        if(objects != null) return objects.size();
        else return 0;
    }

    class HistoryObjectVH extends RecyclerView.ViewHolder {

        private final TextView historyTranslated, historyRaw, historyTime;

        HistoryObjectVH(@NonNull View itemView) {
            super(itemView);
            historyTranslated = itemView.findViewById(R.id.history_translated_text);
            historyRaw = itemView.findViewById(R.id.history_raw_text);
            historyTime = itemView.findViewById(R.id.history_timedate);
        }
    }

    void setItems(List<HistoryObject> items) {
        objects = items;
    }
}
