package com.song.anew.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.Bean.MessageBean;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.adapter.GlideImageLoader;
import com.song.anew.adapter.MessageAdapter;
import com.song.anew.adapter.SampleAdapter;
import com.song.anew.util.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    public static final String TAG = "*********";
    private final String title;
    private final int layout;
    private final Context context;
    private ListView listView;
    private MessageAdapter adapter;
    private HomePageBean.DataBean.ChildrenBean childrenBeans;
    private List<MessageBean.DataBean.NewsBean> list = new ArrayList<>();
    private Banner banner;
    private SmartRefreshLayout mRefreshLayout;
    private MessageBean messageBeans;

    public String getTitle() {
        return title;
    }

    public int getLayout() {
        return layout;
    }

    @SuppressLint("ValidFragment")
    public MyFragment(String title, int layout, final int position, final Context context) {
        super();
        this.title = title;
        this.layout = layout;
        this.context = context;


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Mainactivity mainactivity = (Mainactivity) context;
                if (mainactivity.getHomePageBean() != null) {
                    childrenBeans = mainactivity.getHomePageBean().getData().get(0).getChildren().get(position);
                    Log.i(TAG, "run: " + childrenBeans.getUrl());
                    getMessageBean();
                    timer.cancel();
                } else {
                    Log.i(TAG, "run: " + "我没有获取到数据");
                }
            }
        }, 1000, 1000);
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
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 4));
        listView = inflate.findViewById(R.id.listView);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setDividerHeight(0);//设置分割线高度为0
        //设置按下listView的item不变色
        listView.setSelector(android.R.color.transparent);


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
        initRv();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
        if (messageBeans != null) {
            Log.i(TAG, "MYFragment: "+messageBeans.getData().getNews().size());
        for (int i = 0 ;i<messageBeans.getData().getNews().size();i++)
                list.add(messageBeans.getData().getNews().get(i));


            timer.cancel();
        }
            }
        }, 1000, 1000);
    }

    class MyOnBannerListener implements OnBannerListener {
        @Override
        public void OnBannerClick(int position) {

        }
    }


    public void getMessageBean() {
        final MessageBean[] messageBean = {new MessageBean()};
        OkHttpUtils.get()
                .url(Constants.ROOTURL + childrenBeans.getUrl())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(String response, int id) {
                        messageBean[0] = new Gson().fromJson(response, MessageBean.class);
                        for (int i = 0; i < messageBean[0].getData().getNews().size(); i++) {
                            Log.i(TAG, "onResponse: " + messageBean[0].getData().getNews().get(i).getTitle());
                        }

                        messageBeans = messageBean[0];
                    }
                });


    }
}

