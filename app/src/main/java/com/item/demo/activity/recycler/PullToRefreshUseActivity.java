package com.item.demo.activity.recycler;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.item.demo.R;
import com.item.demo.activity.base.BaseActivity;
import com.item.demo.activity.recycler.adapter.PullToRefreshAdapter;
import com.item.demo.activity.recycler.entity.MyStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/app/src/main/java/com/chad/baserecyclerviewadapterhelper/PullToRefreshUseActivity.java
 */
public class PullToRefreshUseActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullToRefreshAdapter mAdapter;
    private List<MyStatus> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_use);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 233, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setTitle("Pull To Refresh Use");
        setBackBtn();
        initAdapter();
        addHeadView();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

    private void initAdapter() {
        mAdapter = new PullToRefreshAdapter();

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("jiejie","----------- onLoadMore" );
                        if (mData.size() >= 100) {
                            // 数据全部加载完毕
                            mAdapter.loadMoreEnd();
                        } else {
                            if (false) {
                                // 成功获取更多数据

                                mAdapter.addData(mData);
                                mAdapter.loadMoreComplete();
                            } else {
                                // 获取更多数据失败
                                mAdapter.loadMoreFail();
                            }
                        }
                    }
                }, 2000);
            }
        }, mRecyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    /**
     * RecyclerView 添加头布局
     */
    private void addHeadView() {
        View headView = View.inflate(this, R.layout.head_view, null);
        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv)).setText("change load view");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mAdapter.addHeaderView(headView);
    }

    private void refresh() {
        mAdapter.setEnableLoadMore(false); // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 下拉刷新
                mData.clear();
                for (int i = 0; i < 3; i++) {
                    MyStatus status = new MyStatus();
                    status.setRetweet(true);
                    status.setText("ni hao " + i);
                    status.setUserName("wuzongjie" + i);
                    status.setUserAvatar("ni hao " + i);
                    status.setCreatedAt("ddd");
                    mData.add(status);
                }
                Log.d("jiejie","--------onRefresh");
                mAdapter.setNewData(mData);
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
                //mAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }
}
