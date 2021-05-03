package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderRecyclerAdapter extends FirestoreRecyclerAdapter<Note, CalenderRecyclerAdapter.CalenderViewHolder>{
    private OnDeleteClickListener mListener;
    private static final String TAG = "CalenderRecyclerAdapter";


    public CalenderRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(DocumentSnapshot Snapshot);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        mListener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull CalenderViewHolder holder, int position, @NonNull Note note) {
        holder.trainingTypeTextView.setText(note.getTrainingType());
        holder.feelingTextView.setText(""+note.getFeeling());
        holder.trainingsessionTextView.setText(""+note.getTrainsession());
        holder.otherNotesTextView.setText(note.getText());
        String noExcersize = "Inga övningar";
        holder.titleTextView.setText(noExcersize);

        FirebaseFirestore.getInstance().collection("notes")
                .whereEqualTo("created", note.getCreated())
                .whereEqualTo("userId", note.getUserId())
                .whereEqualTo("text", note.getText())
                .whereEqualTo("trainingType", note.getTrainingType())
                .whereEqualTo("feeling", note.getFeeling())
                .whereEqualTo("trainsession", note.getTrainsession())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        if(snapshotList.get(0).exists()){
                            List<Map<String, Object>> titles = (List<Map<String, Object>>) snapshotList.get(0).get("exercise");
                            holder.titleTextView.setText(titles.toString());
                        }
                    }
                });

/*
        FirebaseFirestore.getInstance().collection("notes")
                .document("mghEXEWaSrNcWbVjRyb9")
                .get()
                .addOnCompleteListener(task ->{
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<Map<String, Object>> titles = (List<Map<String, Object>>) document.get("exercise");
                            String excersize = titles.toString();
                            holder.titleTextView.setText(excersize);
                        }
                    }
                    });*/


        if(note.getText() == "") {
            holder.otherNotesTextView.setText("Inga övriga anteckningar den här dagen");
        }

        int trainingsession = note.getTrainsession();
        int feeling = note.getFeeling();

        if(feeling >= 7){
            holder.feelingImageView.setImageResource(R.drawable.ic_baseline_tag_faces_24);
        } else if (feeling > 3 && feeling < 7){
            holder.feelingImageView.setImageResource(R.drawable.ic_baseline_sentiment_neutral_24);
        } else if (feeling <= 3){
            holder.feelingImageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24);
        }
        if(trainingsession >= 7){
            holder.trainsImageView.setImageResource(R.drawable.ic_baseline_tag_faces_24);
        } else if(trainingsession > 3 && trainingsession < 7){
            holder.trainsImageView.setImageResource(R.drawable.ic_baseline_sentiment_neutral_24);
        } else if (trainingsession <= 3){
            holder.trainsImageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24);
        }
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calender_row, parent, false);
        return new CalenderViewHolder(view, mListener);
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {
        TextView trainingTypeTextView, feelingTextView, trainingsessionTextView, otherNotesTextView, titleTextView;
        ImageView imageDelete;
        ImageView feelingImageView, trainsImageView;
        public CalenderViewHolder(@NonNull View itemView, OnDeleteClickListener listener) {
            super(itemView);
            trainingTypeTextView = itemView.findViewById(R.id.trainingTypeTextView);
            feelingTextView = itemView.findViewById(R.id.feelingTextView);
            trainingsessionTextView = itemView.findViewById(R.id.trainingsessionTextView);
            otherNotesTextView = itemView.findViewById(R.id.otherNotesTextView);
            imageDelete = itemView.findViewById(R.id.imagedelNote);
            feelingImageView = itemView.findViewById(R.id.image_feeling);
            trainsImageView = itemView.findViewById(R.id.image_trains);
            titleTextView = itemView.findViewById(R.id.calTitleTextView);

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(getSnapshots().getSnapshot(position));
                        }
                    }
                }
            });

        }
    }
}
