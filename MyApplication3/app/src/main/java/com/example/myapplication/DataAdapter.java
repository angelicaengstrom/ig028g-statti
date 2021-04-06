package com.example.myapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<Data> data;
    ArrayList<String> PrefixNameArray = new ArrayList<String>();
    ArrayList<String> ValueAmtArray = new ArrayList<String>();
    boolean isOnTextChanged = false;

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
        String prefix = currentData.getPrefix();
        String value = currentData.getValue();

        holder.editValue.setText(value);
        holder.editPrefix.setText(prefix);

        holder.editPrefix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isOnTextChanged = true;
                data.get(holder.getAdapterPosition()).setPrefix(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isOnTextChanged == true) {
                    isOnTextChanged = false;


                }
            }
        });
        holder.editValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                data.get(holder.getAdapterPosition()).setValue(s.toString());
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
