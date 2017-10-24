package com.item.demo.activity.refresh;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.item.demo.R;
import com.item.demo.activity.base.BaseActivity;
import com.item.demo.activity.recycler.adapter.HomeAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/scwang90/SmartRefreshLayout
 */
public class TextRefreshActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_refresh);
        setBackBtn();
        setTitle("基本使用");
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("jiejie","onRefresh");
                        mData.clear();
                        for(int i= 0; i < 5; i++){
                            mData.add("ni hao a---- " +  i);
                        }
                        mAdapter.setNewData(mData);
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                },2000);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.d("jiejie","onLoadmore");
                for(int i= 0; i < 10; i++){
                    mData.add("ni hao a--------- " +  i);
                }
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
                if(mAdapter.getItemCount() > 30){
                    refreshlayout.setLoadmoreFinished(true);
                   // mAdapter.loadMoreEnd();
                }
            }
        });
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new HomeAdapter(mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(mAdapter);
        refreshLayout.autoRefresh(); // 触发自动刷新
    }
}
