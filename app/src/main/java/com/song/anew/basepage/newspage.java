package com.song.anew.basepage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.song.anew.BasePage;
import com.song.anew.Bean.VideoBean;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.view.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class newspage extends BasePage {
    private List<VideoView> videoViews;
    private VideoBean videoBean;
    private int width;
    private int height;
    private boolean likeboolean = false;
    private  int recy_postion;
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
                super.handleMessage(msg);
            }
        }
    };
    private MyAdapter adapter;

    public newspage(Context context) {
        super(context);

    }

    @Override
    public void initData() {
        Mainactivity mainactivity = (Mainactivity) context;
        mainactivity.setNohua(1);
        ll_news_item.setVisibility(View.GONE);
        rl_vido_item.setVisibility(View.VISIBLE);
        super.initData();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (bean != null) {
//                    设置数据
                    videoBean = bean;
                    Log.i("***", "run: " + bean.getData().size());
                    Message mes = new Message();
                    mes.what = 1;
                    handler.sendMessage(mes);
                    timer.cancel();
                } else {
                    Log.i("次要数据", "run: " + "我没有获取到数据");
                }
            }
        }, 1000, 1000);
        videoBean = new VideoBean();
        List<VideoBean.DataBean> beandabeans = new ArrayList<VideoBean.DataBean>();
        VideoBean.DataBean beandabean = new VideoBean.DataBean();
        beandabeans.add(beandabean);
        videoBean.setData(beandabeans);
        videoBean.getData().get(0).setCommentCount(123);
        videoBean.getData().get(0).setLikeCount(123);
        videoBean.getData().get(0).setDescription("默认");
        videoBean.getData().get(0).setPosterScreenName("默认");
        List<String> list = new ArrayList<String>();
        list.add("http://txmov2.a.yximgs.com/upic/2018/12/21/04/BMjAxODEyMjEwNDE4MjhfMjE0NDg0NzUyXzk0NjY2OTk3NzZfMV8z_b_B34815be6c605c20fae6905208b2baf24.mp4?tag=1 1545573477 h 0 6xpmwdsjma 53f9570941e1e871");
        videoBean.getData().get(0).setVideoUrls(list);
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(context));
        adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private int index = 0;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
//            Log.i("++++++", "onBindViewHolder: "+videoBean.getData().get(position).getLikeCount());
            holder.tv_like.setText(videoBean.getData().get(index).getLikeCount() + "");
            holder.tv_commentcount.setText(videoBean.getData().get(index).getCommentCount() + "");
            holder.tv_description.setText(videoBean.getData().get(index).getDescription() + "");
            holder.tv_posterscreenname.setText(videoBean.getData().get(index).getPosterScreenName() + "");
            holder.videoView.setMediaController(new MediaController(context,null));
            holder.videoView.setVideoURI(Uri.parse(videoBean.getData().get(index).getVideoUrls().get(0) + ""));
            holder.videoView.requestFocus();
            holder.tv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeboolean = (likeboolean) ? false : true;
                    Log.i("likeboolean", "onClick: " + likeboolean);
                    if (likeboolean) {
                        Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_home_like_after);
                        holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                    } else {
                        Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_home_like_before);
                        holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                    }
                }
            });

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.videoView.isPlaying()) {
                        holder.videoView.pause();
                        holder.button.setBackgroundResource(R.mipmap.ic_live_profile_paly);
                        holder.button.setVisibility(View.VISIBLE);
                    } else {
                        holder.videoView.start();
                        holder.button.setBackground(null);

                    }
                }
            });

            recy_postion = index;
            index++;
            if (index >= videoBean.getData().size()) {
                index = 0;
            }
        }

        @Override
        public int getItemCount() {
            return 88;
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            Log.i("++++++", "onViewAttachedToWindow: "+"我进入了这个页面");
            if (recy_postion>0) {
                holder.videoView.start();
                holder.button.setBackground(null);
            }
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.videoView.pause();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            VerticalViewPager videoView;
            RelativeLayout rootView;
            Button button;
            TextView tv_like;
            TextView tv_commentcount;
            TextView tv_posterscreenname;
            TextView tv_description;

            public ViewHolder(View itemView) {
                super(itemView);
                videoView = itemView.findViewById(R.id.video_view);
                button = itemView.findViewById(R.id.video_but);
                rootView = itemView.findViewById(R.id.root_view);
                tv_like = itemView.findViewById(R.id.tv_like);
                tv_commentcount = itemView.findViewById(R.id.tv_commentcount);
                tv_posterscreenname = itemView.findViewById(R.id.tv_posterscreenname);
                tv_description = itemView.findViewById(R.id.tv_description);
            }
        }
    }
}
