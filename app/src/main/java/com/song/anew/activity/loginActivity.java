package com.song.anew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.song.anew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                Intent intent = new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,Mainactivity.class);
                startActivity(intent);
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
}


