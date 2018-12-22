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
TextView tv_user;
TextView tv_file;
TextView tv_photo;

private List<HomePageBean.DataBean> data;
    @Override
    public View initview() {
      View view = View.inflate(context, R.layout.leftmenu_item,null);
      tv_user = view.findViewById(R.id.tv_user);
      tv_file = view.findViewById(R.id.tv_file);
      tv_photo = view.findViewById(R.id.tv_photo);
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





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
