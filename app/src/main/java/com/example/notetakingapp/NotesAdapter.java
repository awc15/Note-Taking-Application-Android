package com.example.notetakingapp;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClicked(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;

        public NotesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.recyclerTitle);
            description = itemView.findViewById(R.id.recyclerDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position=getAdapterPosition();

                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }

                }
            });
        }
    }

    private Context mContext;
    private Cursor mCursor;

    public NotesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notes, parent, false);

        return new NotesViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String noteTitle = mCursor.getString(mCursor.getColumnIndex(NotesContract.Notes.NOTES_TITLE));
        String noteDescription = mCursor.getString(mCursor.getColumnIndex(NotesContract.Notes.NOTES_DESCRIPTION));
        if(noteDescription.length()>10)
        {
            noteDescription=noteDescription.substring(0,5);
        }
        holder.title.setText(noteTitle);
        holder.description.setText(noteDescription);

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }


}
