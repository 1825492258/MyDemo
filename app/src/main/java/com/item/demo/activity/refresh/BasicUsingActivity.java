package com.item.demo.activity.refresh;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.item.demo.R;
import com.item.demo.activity.base.BaseActivity;
import com.item.demo.adapter.BaseRecyclerAdapter;
import com.item.demo.adapter.SmartViewHolder;
import com.item.demo.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.R.layout.simple_list_item_2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * 基本的功能使用
 */
public class BasicUsingActivity extends BaseActivity {

    private BaseRecyclerAdapter<Void> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_using);
        setBackBtn();
        setTitle("基本使用");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(simple_list_item_2) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "第%02d条数据", position));
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadmore(true); // 开启自动加载的功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("jiejie", "----------onRefresh");
                        mAdapter.refresh(initData());
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("jiejie", "---------- onLoadmore");
                        mAdapter.loadmore(initData());
                        refreshlayout.finishLoadmore();
                        if (mAdapter.getItemCount() > 60) {
                            refreshlayout.setLoadmoreFinished(true); // 将不会再次触发加载更多事件
                        }
                    }
                }, 1500);
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setHeaderHeight(60);
        // 触发自动刷新
        refreshLayout.autoRefresh();
    }


    private Collection<Void> initData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public static class ClassicsHeader extends LinearLayout implements RefreshHeader {

        private TextView mHeaderText;//标题文本
        private PathsView mArrowView;//下拉箭头
        private ImageView mProgressView;//刷新动画视图
        private ProgressDrawable mProgressDrawable;//刷新动画

        public ClassicsHeader(Context context) {
            super(context);
            initView(context);
        }

        public ClassicsHeader(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.initView(context);
        }

        public ClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.initView(context);
        }

        private void initView(Context context) {
            setGravity(Gravity.CENTER);
            mHeaderText = new TextView(context);
            mProgressDrawable = new ProgressDrawable();
            mArrowView = new PathsView(context);
            mProgressView = new ImageView(context);
            mProgressView.setImageDrawable(mProgressDrawable);
            mArrowView.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
            addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
            addView(mArrowView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
            addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
            addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            setMinimumHeight(DensityUtil.dp2px(60));
        }

        @NonNull
        public View getView() {
            return this;//真实的视图就是自己，不能返回null
        }

        @Override
        public SpinnerStyle getSpinnerStyle() {
            return SpinnerStyle.Translate;//指定为平移，不能null
        }

        @Override
        public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
            mProgressDrawable.start();//开始动画
        }

        @Override
        public int onFinish(RefreshLayout layout, boolean success) {
            mProgressDrawable.stop();//停止动画
            if (success) {
                mHeaderText.setText("刷新完成");
            } else {
                mHeaderText.setText("刷新失败");
            }
            return 500;//延迟500毫秒之后再弹回
        }

        @Override
        public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
            switch (newState) {
                case None:
                case PullDownToRefresh:
                    mHeaderText.setText("下拉开始刷新");
                    mArrowView.setVisibility(VISIBLE);//显示下拉箭头
                    mProgressView.setVisibility(GONE);//隐藏动画
                    mArrowView.animate().rotation(0);//还原箭头方向
                    break;
                case Refreshing:
                    mHeaderText.setText("正在刷新");
                    mProgressView.setVisibility(VISIBLE);//显示加载动画
                    mArrowView.setVisibility(GONE);//隐藏箭头
                    break;
                case ReleaseToRefresh:
                    mHeaderText.setText("释放立即刷新");
                    mArrowView.animate().rotation(180);//显示箭头改为朝上
                    break;
            }
        }

        @Override
        public boolean isSupportHorizontalDrag() {
            return false;
        }

        @Override
        public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        }

        @Override
        public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
        }

        @Override
        public void onPullingDown(float percent, int offset, int headHeight, int extendHeight) {
        }

        @Override
        public void onReleasing(float percent, int offset, int headHeight, int extendHeight) {
        }

        @Override
        public void setPrimaryColors(@ColorInt int... colors) {
        }
    }
}
