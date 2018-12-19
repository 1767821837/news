package com.song.anew.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.song.anew.Bean.MessageBean;
import com.song.anew.R;

import java.util.ArrayList;
import java.util.List;


public class SampleAdapter extends BaseAdapter {

    private List<MessageBean> list;
    private Context context;

    public SampleAdapter(List<MessageBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_message, null);
        //TextView textView = convertView.findViewById(R.id.text);
        //textView.setText(mDataSet[position]);

        TextView title=convertView.findViewById(R.id.title);
        TextView date=convertView.findViewById(R.id.date);

        title.setText("1111");
        date.setText("22222");

        return convertView;
    }

}
