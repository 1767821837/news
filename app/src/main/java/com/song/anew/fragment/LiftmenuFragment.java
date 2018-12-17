package com.song.anew.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.song.anew.BaseFragment;
import com.song.anew.Bean.HomePageBean;

import java.util.List;

/**
 * 左侧菜单
 */
public class LiftmenuFragment extends BaseFragment {
    private TextView textView;
private List<HomePageBean.DataBean> data;
    @Override
    public View initview() {
        textView = new TextView(context);

        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initdata() {
        super.initdata();
        textView.setText("asdfasdfasd");
    }

    public void setData(List<HomePageBean.DataBean> data) {
        this.data = data;


    }
}
