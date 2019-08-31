package com.mackyc.uwutranslator.database.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mackyc.uwutranslator.R;
import com.mackyc.uwutranslator.database.history.HistoryObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HistoryObjectAdapter extends ListAdapter<HistoryObject, HistoryObjectAdapter.HistoryObjectVH> {

    private final LayoutInflater inflater;
    // private List<HistoryObject> objects;

    public HistoryObjectAdapter(Context context) {
        super(DIFF_CALLBACK);
        inflater = LayoutInflater.from(context);
    }

    private static final DiffUtil.ItemCallback<HistoryObject> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<HistoryObject>() {
                @Override
                public boolean areItemsTheSame(@NonNull HistoryObject oldItem, @NonNull HistoryObject newItem) {
                    return oldItem.getTimestamp() == newItem.getTimestamp();
                }

                @Override
                public boolean areContentsTheSame(@NonNull HistoryObject oldItem, @NonNull HistoryObject newItem) {
                    return oldItem.getTimestamp().equals(newItem.getTimestamp()) &&
                        oldItem.getRaw().equals(newItem.getRaw())&&
                        oldItem.getTranslated().equals(newItem.getTranslated());
                }
            };

    public interface OnItemClickAdapter {
        /**
         * Interface to listen in clicks on items in this adapter.
         * @param position position of object clicked in RecyclerView
         */
        void onItemClick(int position);
    }

    private OnItemClickAdapter adapter;



    @NonNull
    @Override
    public HistoryObjectVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.history_list_item, parent, false);
        return new HistoryObjectVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryObjectVH holder, final int position) {
        HistoryObject current = getObjectAt(position);

        String pattern = "dd MMM hh:mmaa";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());

        holder.historyTime.setText(sdf.format(current.getTimestamp()));
        holder.historyRaw.setText(current.getRaw());
        holder.historyTranslated.setText(current.getTranslated());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.onItemClick(position);
            }
        });
    }

    class HistoryObjectVH extends RecyclerView.ViewHolder {

        private final ConstraintLayout container;
        private final TextView historyTranslated, historyRaw, historyTime;

        HistoryObjectVH(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.history_list_container);
            historyTranslated = itemView.findViewById(R.id.history_translated_text);
            historyRaw = itemView.findViewById(R.id.history_raw_text);
            historyTime = itemView.findViewById(R.id.history_timedate);
        }
    }

    public void setOnItemClickAdapter(OnItemClickAdapter adapter) {
        this.adapter = adapter;
    }

    public HistoryObject getObjectAt(int position) {
        return getItem(position);
    }
}
