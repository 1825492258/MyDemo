package com.item.demo.activity.refresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.item.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现ListView悬浮头部展示的效果
 * 参考：http://www.cnblogs.com/xqxacm/p/5642063.html
 */
public class MyListActivity extends AppCompatActivity {

    /**
     * 我们先分析要解决的问题
     * 1.如何实现列表ListView顶部视图跟随ListView一起滑动
     * 2.如何实现滑动过程需要停留在顶部的视图
     * <p>
     * 解决
     * 第一个问题：实现ListView与顶部视图一起滑动addHeadView(View); 意思就是在
     * ListView顶部添加一个View那么这个View就能和ListView一起滑动了
     * 第二个问题：怎么保证界面中间的某一部分滑动到顶部的时候停留在顶部呢？
     */

    private LinearLayout llTop; // ListView 滑动时的展示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        ListView listView = (ListView) findViewById(R.id.lv);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("测试数据----" + i);
        }
        View header = View.inflate(this, R.layout.item_top_layout, null);
        // 头部内容，会隐藏的部分
        listView.addHeaderView(header);
        // 头部内容，一直显示的部分
        listView.addHeaderView(View.inflate(this, R.layout.item_tops_layout, null));
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, data));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int i2) {
                Log.d("jiejie", "firstItem" + firstVisibleItem + "    visible" + visibleItemCount + "    count" + i2);
                if (firstVisibleItem >= 1) {
                    llTop.setVisibility(View.VISIBLE);
                } else {
                    llTop.setVisibility(View.GONE);
                }
            }
        });
    }
}
