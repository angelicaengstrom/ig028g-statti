package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<Data> data;

    public DataAdapter(List<Data> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.exersice_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data currentData = data.get(position);
        String value = currentData.getValue();
        String prefix = currentData.getPrefix();

        holder.editValue.setText(value);
        holder.editPrefix.setText(prefix);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText editValue;
        EditText editPrefix;
        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            editValue = itemView.findViewById(R.id.editData);
            editPrefix = itemView.findViewById(R.id.editPrefix);
        }
    }

}
