package com.item.demo.activity.refresh;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.item.demo.R;
import com.item.demo.utils.DensityUtils;
import com.item.demo.utils.StatusBarUtil;
import com.item.demo.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

public class WeiboPracticeActivity extends AppCompatActivity {
    private int mOffset = 0;
    private int mScrollY= 0;
    private NestedScrollView mScrollView;
    private Toolbar mToolbar;
    private View buttonBar;
    private ImageView mImageView;
    private RefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_practice);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mScrollView = (NestedScrollView)findViewById(R.id.scrollView);
        buttonBar = findViewById(R.id.buttonBarLayout);
        mRefreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        mImageView = (ImageView)findViewById(R.id.parallax);
        // 状态栏透明
        StatusBarUtil.immersive(this);
        // 状态栏间距处理
        StatusBarUtil.setPaddingSmart(this,mToolbar);
        buttonBar.setAlpha(0);
        mToolbar.setBackgroundColor(0);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                Log.d("jiejie","onHeaderPulling"  + "  percent" + percent + "  offset" + offset + " headerHeight" + headerHeight + " extendHeight" + extendHeight);
                mOffset = offset / 2;
                mImageView.setTranslationY(mOffset - mScrollY);
                mToolbar.setAlpha(1-Math.min(percent,1));
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                Log.d("jiejie","------onHeaderReleasing" + "  percent" + percent + "  offset" + offset + " footerHeight" + footerHeight + " extendHeight" + extendHeight);
                mOffset = offset / 2;
                mImageView.setTranslationY(mOffset - mScrollY);
                mToolbar.setAlpha(1-Math.min(percent,1));
            }
        });
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtils.dp2px(170);
            private int color = ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
               if(lastScrollY < h){
                   scrollY = Math.min(h,scrollY);
                   mScrollY = scrollY > h ? h : scrollY;
                   buttonBar.setAlpha(1f * mScrollY / h);
                   mToolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                   mImageView.setTranslationY(mOffset - mScrollY);
               }
               lastScrollY = scrollY;
            }
        });

    }
}
