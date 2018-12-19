package com.song.anew.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.song.anew.Bean.MessageBean;
import com.song.anew.R;
import com.song.anew.adapter.GlideImageLoader;
import com.song.anew.adapter.MessageAdapter;
import com.song.anew.adapter.SampleAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    public static final String TAG = "*********";
    private final String title;
    private final int layout;


    private ListView listView;
    private MessageAdapter adapter;
    private List<MessageBean> list = new ArrayList<>();
    private Banner banner;
    private SmartRefreshLayout mRefreshLayout;
    public String getTitle() {
        return title;
    }

    public int getLayout() {
        return layout;
    }

    @SuppressLint("ValidFragment")
    public MyFragment(String title, int layout) {
        super();
        this.title = title;
        this.layout = layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(layout, null);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;//获取屏幕高度


        mRefreshLayout = inflate.findViewById(R.id.refreshLayout);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header, null);
        banner = header.findViewById(R.id.banner);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/4));
        listView=inflate.findViewById(R.id.listView);



        Refresh();//刷新
        setBanner();
        getInfos();

        return inflate;
    }

    private void Refresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh();
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                mRefreshLayout.finishLoadMore();
                //refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
            }
        });
    }

    private void setBanner() {
        List<String> bannerList = new ArrayList<>();
        bannerList.add("http://pic33.photophoto.cn/20141022/0019032438899352_b.jpg");
        bannerList.add("http://t1.mmonly.cc/uploads/allimg/tuku/1613554605-0.jpg");
        bannerList.add("http://imgsrc.baidu.com/imgad/pic/item/d0c8a786c9177f3edb843c207bcf3bc79f3d566f.jpg");
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(bannerList);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        List<String> arr = new ArrayList<>();
        arr.add("1111111111111");
        arr.add("2222222222222");
        arr.add("3333333333333");
        banner.setBannerTitles(arr);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new MyOnBannerListener());
        banner.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    private void initRv() {

        listView.addHeaderView(banner);

        listView.setAdapter(new SampleAdapter(list, getContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getInfos() {
        list.clear();
        for(int i = 0;i<=30;i++){
            list.add(new MessageBean("author_name"+i, "category"+i, "date"+i, "thumbnail_pic_s"+i, "title"+i, "uniquekey"+i, "url"+i));
        }
        initRv();
    }

    class MyOnBannerListener implements OnBannerListener {
        @Override
        public void OnBannerClick(int position) {

        }
    }
}

