package com.song.anew.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.song.anew.R;
import com.song.anew.util.DensityUtil;
import com.song.anew.util.Sputil;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    public static final String ENTYHOMEKEY = "ENTYHOME";
    private Button btn_ent_main;
    private LinearLayout ll_point_group;
    private int[] idsimg;
    private ArrayList<ImageView> imagesid;
    private ImageView iv_red_point;
    private int leftmax;
    private int widths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = findViewById(R.id.viewPager);
        btn_ent_main = findViewById(R.id.btn_ent_main);
        ll_point_group = findViewById(R.id.ll_point_group);
        iv_red_point = findViewById(R.id.iv_red_point);
        initData();
        //设置viewpager适配器
        viewPager.setAdapter(new ViewPagerAdapter());
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnVlobalLayoutListen());

        btn_ent_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sputil.setBoolean(getApplicationContext(), ENTYHOMEKEY, true);
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });
    }

    class MyOnVlobalLayoutListen implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
//        得到屏幕滑动的百分比
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 页面滚动会回调
         *
         * @param i  当前滑动页面的位置
         * @param v  页面滑动的百分比
         * @param i1 滑动的像素
         */
        @Override
        public void onPageScrolled(int i, float v, int i1) {

            int leftmargin = (int) (v * leftmax);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin + leftmax * i;
            iv_red_point.setLayoutParams(params);

        }

        /**
         * 当页面被选中的回调方法
         *
         * @param i 被选中页面的位置
         */
        @Override
        public void onPageSelected(int i) {
            Log.i("*****", "onPageSelected: " + i + "*****" + imagesid.size());
            if (i == imagesid.size() - 1) {
                btn_ent_main.setVisibility(View.VISIBLE);
            } else {
                btn_ent_main.setVisibility(View.GONE);


            }
        }

        /**
         * 当viewpager 页面滑动发生状态变化的时候
         *
         * @param i
         */
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private void initData() {
//        初始化数据
        widths = new DensityUtil().dp2px(getApplicationContext(), 10);
        idsimg = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        imagesid = new ArrayList<ImageView>();
        for (int i = 0; i < idsimg.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(idsimg[i]);
            imagesid.add(imageView);
            //创建点
//            添加到线性布局里面
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.pointnormal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widths, widths);
            if (i != 0) {
                params.leftMargin = widths;
            }
            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }
    }


    class ViewPagerAdapter extends PagerAdapter {
        /**
         * @return 集合个数
         */
        @Override
        public int getCount() {
            return imagesid.size();
        }

        /**
         * @param view 当前创建的视图
         * @param o    instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//            return view==imagesid.get(Integer.parseInt((String)o));
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imagesid.get(position);
            //添加到容器中
            container.addView(imageView);
//            return  position;
            return imageView;
        }

        /**
         * 销毁 页面
         *
         * @param container viewpager
         * @param position  要销毁的位置
         * @param object    要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
