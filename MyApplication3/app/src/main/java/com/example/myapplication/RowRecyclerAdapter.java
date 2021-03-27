package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RowRecyclerAdapter extends FirestoreRecyclerAdapter<Row, RowRecyclerAdapter.RowViewHolder> {

    RowListener rowListener;

    public RowRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Row> options, RowListener rowListener) {
        super(options);
        this.rowListener = rowListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull RowViewHolder holder, int position, @NonNull Row row) {
        holder.titleTextView.setText(row.getTitle());
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.note_row, parent, false);
        return new RowViewHolder(view);
    }

    class RowViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView imageView;
        public RowViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.image_add);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());
                    rowListener.handleEditNote(snapshot);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    interface RowListener{
        public void handleEditNote(DocumentSnapshot snapshot);
    }

}
