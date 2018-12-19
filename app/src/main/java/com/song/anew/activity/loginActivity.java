package com.song.anew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.song.anew.Bean.User;
import com.song.anew.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class loginActivity extends AppCompatActivity {

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

        initView();
        initlisten();
    }

    private void initlisten() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    Gson gson = new Gson();
                                    User user = gson.fromJson(response, User.class);
                                    Log.i("8888","onResponse: " + user);
                                    if (user == null) {
                                        Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(user.getName())) {
                                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(loginActivity.this, Mainactivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "输入的内容不能为空", Toast.LENGTH_SHORT).show();
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


