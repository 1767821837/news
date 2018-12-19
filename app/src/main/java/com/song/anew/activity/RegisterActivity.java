package com.song.anew.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
    @ViewInject(R.id.tv_register)
    private Button tvRegister;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        //ButterKnife.bind(this);
        x.view().inject(this);
    }

    @Event(value = {R.id.tv_register, R.id.regist_back, R.id.tv_get_code})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                regist();
                break;
            case R.id.regist_back:
                finish();
                break;
            case R.id.tv_get_code:
                disabled();
                break;
        }

    }

    private void regist() {
        String name = etUser.getText().toString();
        String password = etPsd.getText().toString();
        String tel = etTel.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(tel)) {

            ////////验证码************************
            if (true) {
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                user.setTel(tel);
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
                                } else {

                                }
                                Toast.makeText(getApplicationContext(), "账号存在", Toast.LENGTH_SHORT).show();
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

}
