package com.song.anew.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.song.anew.activity.WebActivity;
import com.song.anew.adapter.GlideImageLoader;
import com.song.anew.adapter.MessageAdapter;
import com.song.anew.adapter.SampleAdapter;
import com.song.anew.util.Constants;
import com.song.anew.util.Sputil;
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
    private static final String NEWSIER = "NEWSIER" ;
    Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        Log.i(TAG, "handleMessage: "+"我想要刷新banner");
        if(msg.what==1){
            banner.start();

        }else if(msg.what==2){
            sampleAdapter.notifyDataSetChanged();
            setonclick();
        }
    }
};
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
    private List<String> bannerList;
    private List<String> arr;
    private SampleAdapter sampleAdapter;

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
        bannerList = new ArrayList<>();
        arr = new ArrayList<>();
        String newsitem =Sputil.getString(context,NEWSIER,"");
        if(!TextUtils.isEmpty(newsitem)){
            messageBeans  = new Gson().fromJson(newsitem, MessageBean.class);
            bannerList.add(messageBeans.getData().getNews().get(0).getListimage());
            bannerList.add(messageBeans.getData().getNews().get(1).getListimage());
            bannerList.add(messageBeans.getData().getNews().get(2).getListimage());
            arr.add(messageBeans.getData().getNews().get(0).getTitle());
            arr.add(messageBeans.getData().getNews().get(1).getTitle());
            arr.add(messageBeans.getData().getNews().get(2).getTitle());


        }else{

            bannerList.add("");
            bannerList.add("");
            bannerList.add("");
            arr.add("");
            arr.add("");
            arr.add("");
        }



        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Mainactivity mainactivity = (Mainactivity) context;
                if (mainactivity.getHomePageBean() != null) {
                    childrenBeans = mainactivity.getHomePageBean().getData().get(0).getChildren().get(position);
                    Log.i(TAG, "run: " + childrenBeans.getUrl());
                    getMessageBean(childrenBeans.getUrl());
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

        getInfos();
        setBanner();
        initRv();
        return inflate;
    }

    private void Refresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh();
                list.clear();
                OkHttpUtils.get()
                        .url(Constants.ROOTURL + messageBeans.getData().getMore())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }
                            @Override
                            public void onResponse(String response, int id) {
                                Sputil.setString(context,NEWSIER,response);
                                final MessageBean[] messageBean = {new MessageBean()};
                                messageBeans  = new Gson().fromJson(response, MessageBean.class);
                                getInfos();
                                Message mes = new Message();
                                mes.what = 2;
                                handler.sendMessage(mes);

                            }
                        });
                mRefreshLayout.finishRefresh();

                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                OkHttpUtils.get()
                        .url(Constants.ROOTURL + messageBeans.getData().getMore())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }
                            @Override
                            public void onResponse(String response, int id) {
                                final MessageBean[] messageBean = {new MessageBean()};
                                messageBeans  = new Gson().fromJson(response, MessageBean.class);
                                getInfos();
                                Message mes = new Message();
                                mes.what = 2;
                                handler.sendMessage(mes);
                                Log.i(TAG, "onResponse: "+list.size());
                            }
                        });


                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                mRefreshLayout.finishLoadMore();
                //refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
            }
        });
    }

    private void setBanner() {

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(bannerList);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);


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
        this.sampleAdapter = new SampleAdapter(list, getContext());
        SampleAdapter sampleAdapter = this.sampleAdapter;
        listView.setAdapter(sampleAdapter);
        setonclick();

    }

    private void setonclick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: "+position+TAG);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", Constants.ROOTURL + messageBeans.getData().getNews().get(position + 2).getUrl());
                startActivity(intent);
            }
        });
    }

    private void getInfos() {


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
        if (messageBeans != null) {
            Log.i(TAG, "MYFragment: "+messageBeans.getData().getNews().size());
            bannerList.clear();
            bannerList.add(Constants.ROOTURL+messageBeans.getData().getNews().get(0).getListimage());
            bannerList.add(Constants.ROOTURL+messageBeans.getData().getNews().get(1).getListimage());
            bannerList.add(Constants.ROOTURL+messageBeans.getData().getNews().get(2).getListimage());
            banner.setImageLoader(new GlideImageLoader());
            arr.clear();
            arr.add(messageBeans.getData().getNews().get(0).getTitle());
            arr.add(messageBeans.getData().getNews().get(1).getTitle());
            arr.add(messageBeans.getData().getNews().get(2).getTitle());
            Message msg = new Message();
            msg.what = 1;
handler.sendMessage(msg);

        for (int i = 3 ;i<messageBeans.getData().getNews().size();i++)
                list.add(messageBeans.getData().getNews().get(i));

            timer.cancel();
        }
            }
        }, 1000, 1000);
    }

    class MyOnBannerListener implements OnBannerListener {
        @Override
        public void OnBannerClick(int position) {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra("url", Constants.ROOTURL + messageBeans.getData().getNews().get(position).getUrl());
            startActivity(intent);
        }
    }


    public void getMessageBean(String string) {
        Log.i(TAG, "getMessageBean: "+string);
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
                     /*   for (int i = 0; i < messageBean[0].getData().getNews().size(); i++) {
                            Log.i(TAG, "onResponse: " + messageBean[0].getData().getNews().get(i).getTitle());
                        }*/
                        messageBeans = messageBean[0];
                    }
                });


    }
}

