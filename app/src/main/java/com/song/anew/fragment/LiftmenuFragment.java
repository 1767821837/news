package com.song.anew.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.song.anew.BaseFragment;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.R;

import java.util.List;

/**
 * 左侧菜单
 */
public class LiftmenuFragment extends BaseFragment {

private List<HomePageBean.DataBean> data;
    @Override
    public View initview() {
      View view = View.inflate(context, R.layout.leftmenu_item,null);
        return view;
    }

    @Override
    public void initdata() {
        super.initdata();

    }

    public void setData(List<HomePageBean.DataBean> data) {
        this.data = data;


    }
}
