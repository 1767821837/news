package com.song.anew.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.song.anew.BaseFragment;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.util.DensityUtil;

import java.util.List;

/**
 * 左侧菜单
 */
public class LiftmenuFragment extends BaseFragment {
    TextView tv_user_name;
    TextView tv_user;
    TextView tv_file;
    TextView tv_photo;
    User user;
    private List<HomePageBean.DataBean> data;

    @Override
    public View initview() {
        View view = View.inflate(context, R.layout.leftmenu_item, null);
        tv_user = view.findViewById(R.id.tv_user);
        tv_file = view.findViewById(R.id.tv_file);
        tv_photo = view.findViewById(R.id.tv_photo);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        Mainactivity mainactivit = (Mainactivity) getActivity();
         user =  mainactivit.user;
         tv_user_name.setText(user.getName());
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("**************", "onClick: ");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE); // 如果少了这句，有些机型上面不能正常打开文件管理器，比如金立
                startActivityForResult(intent, 150);

            }
        });

        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Intent.ACTION_PICK);
                i1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
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
