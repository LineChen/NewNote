package com.bocai.newnote.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;


import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.bean.God;
import com.bocai.newnote.mvp.ui.adapter.viewholder.IndexViewHolder;
import com.bocai.newnote.utils.SPUtils;
import com.bocai.newnote.utils.TimeUtils;
import com.bocai.newnote.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class IndexViewAdapter extends RecyclerView.Adapter<IndexViewHolder> {

    private final Context mContext;
    private List<God> mGodList = new ArrayList<>();
    private OnRecyclerItemClickListener listener;
    private boolean isOpen;
    private int lastAnimatedPosition = -1;

    public IndexViewAdapter(Context context, ArrayList<God> godArrayList) {
        mContext = context;
        if (godArrayList != null) {
            mGodList.addAll(godArrayList);
        }
    }
    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_password_item, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        holder.setLabelText(mGodList.get(position).getTitle());
        holder.setContentText(mGodList.get(position).getUserName());
        if (isOpen) {
            holder.setPassWordTextView(mGodList.get(position).getPassWord());
        } else {
            holder.setPassWordTextView("*********");
        }
        String memoInfo = mGodList.get(position).getMemoInfo();
        if (!memoInfo.equals("")) {
            holder.setMemoInfoContentVisibility(true);
            holder.setMemoInfo(mGodList.get(position).getMemoInfo());
        } else {
            holder.setMemoInfoContentVisibility(false);
        }
        int godType = mGodList.get(position).getGodType();
        switch (godType) {
            case 0:
                holder.setMoRen(mContext);
                break;
            case 1:
                holder.setYouXiang(mContext);
                break;
            case 2:
                holder.setCard(mContext);
                break;
        }
        holder.setTimeText(TimeUtils.getConciseTime((mGodList.get(position).getTime()), mContext));
        holder.setOnRippleClickListener(new IndexViewHolder.OnRippleClick() {
            @Override
            public void onRippleClick(View view) {
                if (listener != null) {
                    listener.onRecyclerItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        isOpen = (boolean) SPUtils.get(mContext, ConstanTs.SETTING.OPEN_PASS_WORD_SHOW, true);
        return mGodList == null ? 0 : mGodList.size();
    }

    public void addOneTop(God god) {
        mGodList.add(0, god);
    }

    public void addAll(ArrayList<God> godArrayList) {
        mGodList.clear();
        mGodList.addAll(godArrayList);
    }

    public void clearData() {
        mGodList.clear();
    }


    public void setOnRecyclerItemClick(OnRecyclerItemClickListener onItemClickListener){
        listener = onItemClickListener;
    }
    public  interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(View view, int position);
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
