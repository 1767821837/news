package com.song.anew.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.song.anew.R;
import com.song.anew.util.Sputil;

public class SplashActivity extends AppCompatActivity {
    public static final String ENTYHOMEKEY = "ENTYHOME";
    private Handler mhander ;
private ImageView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalace);
        mhander = new Handler();

        lv = findViewById(R.id.lv_splash);
       initanm();

    }

    private void initanm() {
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(1000);
        aa.setFillAfter(true);
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(1000);
        aa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_PARENT,0.5f,RotateAnimation.RELATIVE_TO_PARENT,0.5f);
        ra.setDuration(1000);
        ra.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(ra);
        set.addAnimation(sa);
        lv.setAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean  bo = new Sputil().getBoolean(getApplicationContext(), ENTYHOMEKEY,false);
                if(bo){
                    aoutjump(Mainactivity.class,1000);
                }else{
                    Intent intent = new Intent(getApplicationContext(),GuideActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void aoutjump(final Class<? extends Activity> mainactivityClass, int time) {
        mhander.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),mainactivityClass);
                startActivity(intent);
                finish();
            }
        }, time);
    }

}
