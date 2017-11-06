package com.item.demo.activity.ult;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;

import com.item.demo.R;
import com.item.demo.utils.ToastUtils;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

/**
 * UltraViewPager 的使用
 * https://github.com/alibaba/UltraViewPager
 */
public class UltActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ult);
        setUlt();

    }

    private void setUlt() {
        UltraViewPager ultraViewPager = (UltraViewPager)findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        // UltraPagerAdapter 绑定子View到UltraViewPager
        PagerAdapter adapter = new UltraPagerAdapter(false);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.setMultiScreen(0.8f);
        ultraViewPager.setItemRatio(1f);
        ultraViewPager.setPageTransformer(false,new UltraScaleTransformer());
       // ultraViewPager.setPageTransformer(false,new UltraDepthScaleTransformer());
        // 内置indicator初始化
        ultraViewPager.initIndicator();
        // 设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.RED)
                .setMargin(0,0,0, ToastUtils.dp2px(this,10))
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        // 设置indicator对齐方式
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        // 构造indicator, 绑定UltraViewPager
        ultraViewPager.getIndicator().build();
        // 设置页面循环播放
        ultraViewPager.setInfiniteLoop(true);
        // 设定页面自动切换， 间隔3秒
        ultraViewPager.setAutoScroll(3000);
    }
}
