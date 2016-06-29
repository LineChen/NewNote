package com.bocai.newnote.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.ui.adapter.viewholder.NoteCategoryViewHolder;
import com.bocai.newnote.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: aiqin
 */

public class NoteCategoryAdapter extends RecyclerView.Adapter<NoteCategoryViewHolder> {

    private final Context mContext;
    private List<Category> mCateList = new ArrayList<>();
    private OnRecyclerItemClickListener listener;
    private OnLongRecyclerItemClickListener longlistener;

    private int lastAnimatedPosition = -1;

    public NoteCategoryAdapter(Context context, ArrayList<Category> cateArrayList) {
        mContext = context;
        if (cateArrayList != null) {
            mCateList.addAll(cateArrayList);
        }
    }

    @Override
    public NoteCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new NoteCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteCategoryViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);

        holder.setCateNameText( mCateList.get(position).getCategoryName());
        holder.setNumText("(" + mCateList.get(position).getNotes().size() + ")");
        holder.setOnRippleClickListener(new NoteCategoryViewHolder.OnRippleClick() {
            @Override
            public void onRippleClick(View view) {
                if (listener != null) {
                    listener.onRecyclerItemClick(view, position);
                }
            }
        });

        holder.setOnLongRippleClickListener(new NoteCategoryViewHolder.OnLongRippleClick() {
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
        return mCateList == null ? 0 : mCateList.size();
    }

    public void addOneTop(Category category) {
        mCateList.add(0, category);
    }

    public void addAll(ArrayList<Category> cateArrayList) {
        mCateList.clear();
        mCateList.addAll(cateArrayList);
    }

    public void clearData() {
        mCateList.clear();
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
