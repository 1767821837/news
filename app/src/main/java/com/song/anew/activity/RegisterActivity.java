package com.song.anew.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.song.anew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_er)
    EditText etPsdEr;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {

    }
}
