package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class CalTitlesRecyclerAdapter extends RecyclerView.Adapter<CalTitlesRecyclerAdapter.CalTitlesViewHolder> {
    List<Row> titles;
    public CalTitlesRecyclerAdapter(List<Row> titles) {
        this.titles = titles;
    }

    @NonNull
    @Override
    public CalTitlesRecyclerAdapter.CalTitlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calender_row_title, parent, false);
        return new CalTitlesRecyclerAdapter.CalTitlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalTitlesRecyclerAdapter.CalTitlesViewHolder holder, int position) {
        Row currentTitle = titles.get(position);
        holder.titleTextView.setText(currentTitle.getTitle());
        List<Data> data = currentTitle.getTitleItems();

        /*
        DataAdapter dataAdapter = new DataAdapter(data);
        holder.dataList.setAdapter(dataAdapter);*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CalTitlesViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public RecyclerView dataRecyclerView;

        public CalTitlesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.calTitle);
            dataRecyclerView = itemView.findViewById(R.id.calDataRecyclerView);

        }
    }
}
