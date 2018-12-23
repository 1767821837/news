package com.song.anew.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.song.anew.BaseFragment;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.activity.loginActivity;
import com.song.anew.util.File_upload;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 左侧菜单
 */
public class LiftmenuFragment extends BaseFragment {
    TextView tv_user_name;
    LinearLayout tv_user;
    LinearLayout tv_file;
    LinearLayout tv_photo;
    User user;
    com.song.anew.view.RoundImageView iv;
    private LinearLayout exit;

    private List<HomePageBean.DataBean> data;
    private String photoURI;

    @Override
    public View initview() {
        View view = View.inflate(context, R.layout.leftmenu_item, null);
        tv_user = view.findViewById(R.id.tv_user);
        tv_file = view.findViewById(R.id.tv_file);
        tv_photo = view.findViewById(R.id.tv_photo);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        iv = view.findViewById(R.id.roundiv);
        exit = view.findViewById(R.id.exit);

        final Mainactivity mainactivit = (Mainactivity) getActivity();
        user = mainactivit.user;
        if (!TextUtils.isEmpty(user.getPhoto())) {
            try {
                Glide.with(context).load((String) user.getPhoto())
                        .signature(new StringSignature(SystemClock.currentThreadTimeMillis() + ""))
                        .into(iv);


            } catch (Exception e) {

            }
        }
        user = mainactivit.user;
        tv_user_name.setText("用户名：" + user.getName());
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {//退出
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), loginActivity.class);
                startActivity(intent);
                getActivity().finish();
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
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
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


        if (resultCode == Activity.RESULT_OK && requestCode == 20) {
            Uri uri = data.getData();
            final String scheme = uri.getScheme();
            photoURI = null;
            if (scheme == null) {
                photoURI = uri.getPath();
            } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                photoURI = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            photoURI = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
            user.setPhoto("https://songtell-1251684550.cos.ap-chengdu.myqcloud.com/news/" + user.getName() + "Photo.jpg");
            File_upload file_upload = new File_upload(context, photoURI, user);
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
                            if (!TextUtils.isEmpty(users.getName())) {
                                Mainactivity mainactivity = (Mainactivity) getActivity();
                                mainactivity.user = users;
                                iv.setImageBitmap(BitmapFactory.decodeFile(photoURI));


                            } else {
                                Toast.makeText(context, "头像更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }


}
