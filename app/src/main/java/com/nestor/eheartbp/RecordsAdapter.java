package com.nestor.eheartbp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordViewHolder> {
    private List<RecordItem> records;

    public RecordsAdapter(List<RecordItem> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        RecordItem record = records.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return records != null ? records.size() : 0;
    }

    public void updateRecords(List<RecordItem> newRecords) {
        this.records = newRecords;
        notifyDataSetChanged();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bind(RecordItem record) {
            String displayText = String.format("           %s                    %s                     %s                  %s",
                    record.getDate(),
                    record.getSystolic(),
                    record.getDiastolic(),
                    record.getPulse());
            textView.setText(displayText);
        }
    }
} 