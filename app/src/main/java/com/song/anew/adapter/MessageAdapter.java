package com.song.anew.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.song.anew.Bean.MessageBean;

import java.util.List;

class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public MessageAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
//        helper.setText(R.id.title, item.getTitle());
//        helper.setText(R.id.date, item.getDate());
    }
}