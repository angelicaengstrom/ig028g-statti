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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.List;

public class CalenderRecyclerAdapter extends FirestoreRecyclerAdapter<Note, CalenderRecyclerAdapter.CalenderViewHolder>{
    private OnDeleteClickListener mListener;


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

        List<Row> titles = new ArrayList<>();
        List<String> test = new ArrayList<>();
        CalTitlesRecyclerAdapter titleAdapter = new CalTitlesRecyclerAdapter(titles);
        List<Data> data = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("notes").child("title");
        DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child("title").child("titleItems");
        dataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Row> Temp = new ArrayList<>();
                Log.d("Count: ",""+ dataSnapshot.getChildrenCount());


                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note notes = snapshot.getValue(Note.class);
                    Log.d("Get Data", "" + notes.getFeeling());
                    //Temp = note.getTitles();
                }
                for(int i = 0; i < Temp.size(); i++){ //Temp är tom så den går ej vidare
                    String txt = Temp.get(i).getTitle();
                    test.add(txt);
                    data.add(new Data());
                    titles.add(new Row(txt, data));
                }
                    titleAdapter.notifyDataSetChanged();
                Log.d("TITLE", "Titles: " + test + "Notes " + note.getText());
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //CalTitlesRecyclerAdapter titleAdapter = new CalTitlesRecyclerAdapter(titles);
        holder.titlesRecyclerView.setAdapter(titleAdapter);

        if(note.getText() == "") {
            holder.otherNotesTextView.setText("Inga anteckningar den här dagen");
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
        TextView trainingTypeTextView, feelingTextView, trainingsessionTextView, otherNotesTextView;
        ImageView imageDelete;
        RecyclerView titlesRecyclerView;
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
            titlesRecyclerView = itemView.findViewById(R.id.calTitles);

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
