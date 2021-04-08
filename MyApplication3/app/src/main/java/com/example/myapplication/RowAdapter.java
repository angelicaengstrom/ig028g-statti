package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
    private List<Row> mRowList;
    private static final String TAG = "RowAdapter";
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView imageAdd;
        public RecyclerView dataList;

        public RowViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            imageAdd = itemView.findViewById(R.id.image_add);
            dataList = itemView.findViewById(R.id.dataRecyclerView);


            imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        Log.d("demo", "onClick: add to title " + position);
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }

            });

        }
    }

    public RowAdapter(List<Row> rowList){
        mRowList = rowList;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        RowViewHolder rvh = new RowViewHolder(v, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        Row currentTitle = mRowList.get(position);
        String titleName = currentTitle.getTitle();
        List<Data> data = currentTitle.getTitleItems();

        holder.title.setText(titleName);
        DataAdapter dataAdapter = new DataAdapter(data);
        //holder.dataList.setHasFixedSize(false);
        holder.dataList.setAdapter(dataAdapter);

        dataAdapter.setOnEraseClickListener(new DataAdapter.OnEraseClickListener() {
            @Override
            public void onEraseClick(int position) {
                currentTitle.eraseTitleItem(position);
                holder.dataList.setAdapter(dataAdapter);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRowList.size();
    }
}
