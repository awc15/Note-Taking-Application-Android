package com.example.notetakingapp;

import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title;
    public TextView description;
    public LinearLayout linearLayout;
    public NotesAdapter.OnNoteListener onNoteListener;

    public NotesViewHolder(@NonNull View itemView, NotesAdapter.OnNoteListener onNoteListener) {
        super(itemView);
        title = itemView.findViewById(R.id.recyclerTitle);
        description = itemView.findViewById(R.id.recyclerDescription);
       // linearLayout = itemView.findViewById(R.id.linearLayout);
        this.onNoteListener = onNoteListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }
}
