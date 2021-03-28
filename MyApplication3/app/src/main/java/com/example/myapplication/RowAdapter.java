package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
    private ArrayList<Row> mRowList;

    public static class RowViewHolder extends RecyclerView.ViewHolder{
        public TextView title;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
        }
    }

    public RowAdapter(ArrayList<Row> rowList){
        mRowList = rowList;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        RowViewHolder rvh = new RowViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        Row currentTitle = mRowList.get(position);

        holder.title.setText(currentTitle.getTitle());
    }

    @Override
    public int getItemCount() {
        return mRowList.size();
    }
}
