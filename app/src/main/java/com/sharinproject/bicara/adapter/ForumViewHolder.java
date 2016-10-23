package com.sharinproject.bicara.adapter;

/**
 * Created by Hermansyah on 12/26/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharinproject.bicara.MyApplication;
import com.sharinproject.bicara.database.ForumChat;
import com.sharinproject.bicara.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.itemforumchat_tv_profilename)
    TextView textViewAlias;
    @BindView(R.id.itemforumchat_tv_message)
    TextView textViewMessage;
    private ForumMessageAdapter.OnItemClickListener mOnItemClickListener;
    private ForumChat mItem;
    private Context mContext;

    public ForumViewHolder(View itemView, ForumMessageAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(this);
        mContext=itemView.getContext();
    }

    public void setData(final ForumChat item) {
        mItem = item;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)textViewMessage.getLayoutParams();
        RelativeLayout.LayoutParams paramsalias = (RelativeLayout.LayoutParams)textViewAlias.getLayoutParams();
        textViewAlias.setText(item.getChat_alias() + "\nReceive at " + item.getChat_retievetime());
        if (item.getChat_uuid() != null){
            if (item.getChat_uuid().equals(MyApplication.getInstance().getGoogleID())){
                textViewMessage.setText(item.getChat_message());
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                paramsalias.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                textViewMessage.setBackground(mContext.getResources().getDrawable(R.drawable.in_message_bg));
            }else {
                textViewMessage.setText(item.getChat_message());
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                paramsalias.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                textViewMessage.setBackground(mContext.getResources().getDrawable(R.drawable.out_message_bg));
            }
        }else {
            Log.e("Realm", "Null data");
        }
    }

    @Override
    public void onClick(View view) {
        mOnItemClickListener.onItemClick(mItem);
    }


}