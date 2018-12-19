package com.song.anew.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.song.anew.adapter.*;
import com.song.anew.Bean.MessageBean;
import com.song.anew.R;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    public static final String TAG ="*********";
    private final String title;
    private final int layout;


    private RecyclerView recyclerView;
    //private MessageAdapter adapter;
    private List<MessageBean> list = new ArrayList<>();

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
        //TextView tv = inflate.findViewById(R.id.tv);
        //tv.setText(title);
        setView(inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    public void setView(View view) {
        recyclerView=view.findViewById(R.id.recyclerview);

    }
}
