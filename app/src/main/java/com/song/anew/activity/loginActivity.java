package com.song.anew.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.song.anew.util.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.view.annotation.ContentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class loginActivity extends Activity {

    TextView tvRegister;
    EditText etUser;
    EditText etPsd;
    EditText etTel;
    EditText etCode;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StatusBarCompat.setStatusBarColor(this, Color.argb(255, 255, 69, 69), true);

        initView();
        initlisten();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 3 && requestCode == 2) {
            String namePass = data.getStringExtra("namePass");
            //Toast.makeText(this, "1111111111111"+namePass, Toast.LENGTH_LONG).show();
            String[] split = namePass.split("@");
            etUser.setText(split[0]);
            etPsd.setText(split[1]);
        } else {
            etUser.setText("");
            etPsd.setText("");
        }


    }

    private void initlisten() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 2);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog show = ProgressDialog.show(loginActivity.this, "登录中", "加载中……");
                //ProgressBar


                String name = etUser.getText().toString();
                String password = etPsd.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    User user = new User();
                    user.setName(name);
                    user.setPassword(password);
                    OkHttpUtils.postString()
                            .url("http://134.175.154.154/new/api/news/login")
                            .content(new Gson().toJson(user))
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(loginActivity.this, "请求出错！！！", Toast.LENGTH_SHORT).show();
                                    show.dismiss();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Gson gson = new Gson();
                                    User user = gson.fromJson(response, User.class);
                                    Log.i("8888", "onResponse: " + user);
                                    if (user == null) {
                                        Toast.makeText(loginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                        show.dismiss();
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(user.getName())) {

                                        Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(loginActivity.this, Mainactivity.class);
                                        startActivity(intent);
                                        show.dismiss();
                                        finish();
                                    } else {
                                        Toast.makeText(loginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                        show.dismiss();
                                        return;
                                    }
                                }
                            });
                } else {
                    show.dismiss();
                    Toast.makeText(loginActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {

        tvRegister = findViewById(R.id.tv_register);
        etUser = findViewById(R.id.et_user);
        etPsd = findViewById(R.id.et_psd);
        etTel = findViewById(R.id.et_tel);
        etCode = findViewById(R.id.et_code);
        btnLogin = findViewById(R.id.btn_login);

    }

    public void login(View view) {


    }


}


