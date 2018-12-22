package com.song.anew.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.song.anew.util.StatusBarUtil;
import com.song.anew.view.RoundImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    @ViewInject(R.id.et_user)
    private EditText etUser;
    @ViewInject(R.id.et_psd)
    private EditText etPsd;
    @ViewInject(R.id.et_tel)
    private EditText etTel;
    @ViewInject(R.id.et_code)
    private EditText etCode;
    @ViewInject(R.id.regist_back)
    private ImageView registback;
    @ViewInject(R.id.tv_get_code)
    private TextView tvGetCode;
    @ViewInject(R.id.roundiv)
    private RoundImageView roundImageView;
    private int i;

    private boolean flag = false;
    private User user = new User();
    private String username;
    private String password;
    private String phone;
    private String code;
    private String msgcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        StatusBarCompat.setStatusBarColor(this, Color.argb(255, 255, 69, 69), true);
    }

    @Event(value = {R.id.tv_register, R.id.regist_back, R.id.tv_get_code,R.id.roundiv})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                regist();
                break;
            case R.id.roundiv:

                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
                break;
            case R.id.regist_back:
                finish();
                break;
            case R.id.tv_get_code:
                getInfo();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
                    getCode();
                    disabled();
                } else {
                    Toast.makeText(this, "不能为空！！！", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void regist() {
        getInfo();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)) {
            ////////验证码************************

            if (msgcode.equals(code)) {
                flag = true;
            }

            if (flag) {

                user.setName(username);
                user.setPassword(password);
                user.setTel(phone);
                OkHttpUtils.postString()
                        .url("http://134.175.154.154/new/api/news/regist")
                        .content(new Gson().toJson(user))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                Gson gson = new Gson();
                                User user = gson.fromJson(response, User.class);
                                Log.i("8888", "onResponse: " + user);
                                if (!TextUtils.isEmpty(user.getName())) {
                                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                                    int resultcode = 3;
                                    Intent data = new Intent();
                                    String result = username + "@" + password;
                                    data.putExtra("namePass", result);
                                    setResult(resultcode, data);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "账号存在", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "输入内容不能为空", Toast.LENGTH_SHORT).show();
        }


    }


    private void disabled() {
        i = 60;
        tvGetCode.setClickable(false);
        //tvGetCode.setBackgroundColor(Color.argb(255, 250, 250, 250));
        tvGetCode.setTextColor(Color.argb(255, 180, 180, 180));
        tvGetCode.setText("请稍等（" + i + "）");

        new Thread() {
            @Override
            public void run() {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (i == 0) {
                                    timer.cancel();
                                    tvGetCode.setText("获取验证码");
                                } else
                                    tvGetCode.setText("请稍等（" + i-- + "）");
                            }
                        });
                    }
                }, 0, 1000);
            }
        }.start();
        Timer nTimer = new Timer();
        nTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvGetCode.setClickable(true);
                    //tvGetCode.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    tvGetCode.setTextColor(Color.argb(255, 254, 69, 66));
                }
            });
        }
    }, i * 1000);
}


    private void getCode() {
        msgcode = "" + (int) ((Math.random()) * 1000000);
        //Toast.makeText(this, ""+msgcode, Toast.LENGTH_SHORT).show();
        final String ss = "2";
        OkGo.post("http://v.juhe.cn/sms/send")
                .params("mobile", phone)
                .params("tpl_id", "116524")
                .params("key", "4159ebdf6b366bbf5fdbae6472c216b2")
                .params("tpl_value", "%23code%23%3D" + msgcode)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            if ((jo.getInt("error_code") + "").equals("0")) {
                                Toast.makeText(getApplicationContext(), "验证码发送成功！！！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getInfo() {
        username = etUser.getText().toString();
        password = etPsd.getText().toString();
        phone = etTel.getText().toString();
        code = etCode.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK&&requestCode==20){
            Uri uri = data.getData();
            final String scheme = uri.getScheme();
            String photoURI = null;
            if (scheme == null) {
                photoURI = uri.getPath();
            } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                photoURI = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
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

            user.setPhoto(photoURI+"");
        }


    }
}
