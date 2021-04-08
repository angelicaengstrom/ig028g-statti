package com.example.myapplication;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<Data> data;
    private OnEraseClickListener mListener;
    private static final String TAG = "DataAdapter";

    boolean isOnTextChanged = false;

    public DataAdapter(List<Data> data){
        this.data = data;
    }

    public interface OnEraseClickListener{
        void onEraseClick(int position);
    }

    public void setOnEraseClickListener(OnEraseClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.exersice_row, parent, false);
        return new ViewHolder(view, mListener);
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
        ImageView imageErase;
        public  ViewHolder(@NonNull View itemView, OnEraseClickListener listener){
            super(itemView);
            editValue = itemView.findViewById(R.id.editData);
            editPrefix = itemView.findViewById(R.id.editPrefix);
            imageErase = itemView.findViewById(R.id.image_remove);

            imageErase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        Log.d("demo", "onClick: erase title at " + position);
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEraseClick(position);
                        }
                    }
                }
            });
        }
    }

}
