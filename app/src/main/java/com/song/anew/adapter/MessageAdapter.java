package com.song.anew.adapter;


import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.song.anew.Bean.MessageBean;
import com.song.anew.R;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public MessageAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        //Log.e(TAG, "onSuccess: dsafsdfa" );
        helper.setText(R.id.title, "测试");
        helper.setText(R.id.date, "阿斯顿发送到");
    }
}