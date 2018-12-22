package com.song.anew.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.song.anew.BaseFragment;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.util.DensityUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 左侧菜单
 */
public class LiftmenuFragment extends BaseFragment {
    TextView tv_user_name;
    TextView tv_user;
    TextView tv_file;
    TextView tv_photo;
    User user;
    com.song.anew.view.RoundImageView iv;
    private List<HomePageBean.DataBean> data;

    @Override
    public View initview() {
        View view = View.inflate(context, R.layout.leftmenu_item, null);
        tv_user = view.findViewById(R.id.tv_user);
        tv_file = view.findViewById(R.id.tv_file);
        tv_photo = view.findViewById(R.id.tv_photo);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        iv = view.findViewById(R.id.roundiv);
        Mainactivity mainactivit = (Mainactivity) getActivity();
        user = mainactivit.user;
        tv_user_name.setText(user.getName());
        if(!TextUtils.isEmpty(user.getPhoto())){

            Uri uri = Uri.parse(user.getPhoto());
            iv.setImageURI(uri);
        }
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
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 150);

            }
        });

        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK&&requestCode==20) {
            Uri uri = data.getData();
            iv.setImageURI(uri);
           user.setPhoto(uri+"");
            Log.i("*****", "onActivityResult: "+user.toString());
            OkHttpUtils.postString()
                    .url("http://134.175.154.154/new/api/news/update")
                    .content(new Gson().toJson(user))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            User users;
                            users = new Gson().fromJson(response, User.class);
                            if (!TextUtils.isEmpty(users.getName()))

                            {
                                Mainactivity mainactivity = (Mainactivity) getActivity();
                                mainactivity.user = users;
                                Toast.makeText(context, "头像更新成功成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "头像更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


}
