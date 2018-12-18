package com.song.anew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.song.anew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class loginActivity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void login(View view) {
        startActivity(new Intent(this, Mainactivity.class));
    }

    @OnClick({R.id.tv_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent();
                startActivity(intent);
                break;
            case R.id.btn_login:




                Intent intent1 = new Intent(this,Mainactivity.class);
                startActivity(intent1);
                break;
        }
    }
}
