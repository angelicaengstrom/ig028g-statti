package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CalenderRecyclerAdapter extends FirestoreRecyclerAdapter<Note, CalenderRecyclerAdapter.CalenderViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CalenderRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CalenderViewHolder holder, int position, @NonNull Note note) {
        holder.trainingTypeTextView.setText(note.getText());
        holder.feelingTextView.setText(note.getText());
        holder.trainingsessionTextView.setText(note.getText());
        holder.otherNotesTextView.setText(note.getText());
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calender_row, parent);
        return new CalenderViewHolder(view);
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {
        TextView trainingTypeTextView, feelingTextView, trainingsessionTextView, otherNotesTextView;
        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);
            trainingTypeTextView = itemView.findViewById(R.id.trainingTypeTextView);
            feelingTextView = itemView.findViewById(R.id.feelingTextView);
            trainingsessionTextView = itemView.findViewById(R.id.trainingsessionTextView);
            otherNotesTextView = itemView.findViewById(R.id.otherNotesTextView);

        }
    }
}
