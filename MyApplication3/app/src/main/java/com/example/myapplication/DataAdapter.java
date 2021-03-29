package com.example.myapplication;

import android.text.Editable;
import android.text.TextWatcher;
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
    String[] prefixArray = new String[data.size()];
    String[] valueArray = new String[data.size()];

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
        prefixArray[position] = prefix;
        valueArray[position] = value;

        holder.editValue.setText(valueArray[position]);
        holder.editPrefix.setText(prefixArray[position]);
        holder.editValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valueArray[position] =  s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.editPrefix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefixArray[position] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
