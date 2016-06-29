package com.bocai.newnote.mvp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.bean.Note;
import com.bocai.newnote.mvp.ui.adapter.viewholder.NoteViewHolder;
import com.bocai.newnote.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final Context mContext;
    private List<Note> mNoteList = new ArrayList<>();
    private OnRecyclerItemClickListener listener;
    private OnLongRecyclerItemClickListener longlistener;

    private int lastAnimatedPosition = -1;

    public NoteAdapter(Context context, ArrayList<Note> noteArrayList) {
        mContext = context;
        if (noteArrayList != null) {
            mNoteList.addAll(noteArrayList);
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);

        holder.setTitleText(mNoteList.get(position).getTitle());

        holder.setContentText(mNoteList.get(position).getContent());

        holder.setPhotoVisible(!mNoteList.get(position).getImagepath().equals(""));

        holder.setVideoCameraVisible(!mNoteList.get(position).getVideoPath().equals(""));

        holder.setLocationVisible(!mNoteList.get(position).getLocaltionName().equals(""));

        holder.setOnRippleClickListener(new NoteViewHolder.OnRippleClick() {
            @Override
            public void onRippleClick(View view) {
                if (listener != null) {
                    listener.onRecyclerItemClick(view, position);
                }
            }
        });

        holder.setOnLongRippleClickListener(new NoteViewHolder.OnLongRippleClick() {
            @Override
            public void onLongRippleClick(View view) {
                if (longlistener != null){
                    longlistener.onLongRecyclerItemClick(view,position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mNoteList == null ? 0 : mNoteList.size();
    }

    public void addOneTop(Note note) {
        mNoteList.add(0, note);
    }

    public void addAll(ArrayList<Note> noteArrayList) {
        mNoteList.clear();
        mNoteList.addAll(noteArrayList);
    }

    public void clearData() {
        mNoteList.clear();
    }


    public void setOnRecyclerItemClick(OnRecyclerItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public void setOnLongRecyclerItemClick(OnLongRecyclerItemClickListener onLongRecyclerItemClickListener){
        longlistener = onLongRecyclerItemClickListener;
    }
    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(View view, int position);
    }

    public interface OnLongRecyclerItemClickListener {
        void onLongRecyclerItemClick(View view, int position);
    }

    private void runEnterAnimation(View view, int position) {
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(1500)
                    .start();
        }
    }
}
