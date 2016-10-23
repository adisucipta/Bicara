package com.sharinproject.bicara.adapter;

/**
 * Created by Hermansyah on 12/26/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sharinproject.bicara.database.ForumChat;
import com.sharinproject.bicara.R;

import java.util.List;

/**
 * Created by Hermansyah on 11/7/2015.
 */
public class ForumMessageAdapter extends RecyclerView.Adapter<ForumViewHolder> {

    private List<ForumChat> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ForumMessageAdapter(List<ForumChat> list, Context context,
                               OnItemClickListener onItemClickListener) {
        mList = list;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public ForumMessageAdapter(Context context,
                               OnItemClickListener onItemClickListener) {
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<ForumChat> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ForumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.item_forumchat, parent, false),
                mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ForumViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        }else{
            return mList.size();
        }

    }

    public interface OnItemClickListener{
        void onItemClick(ForumChat mItem);
    }

}