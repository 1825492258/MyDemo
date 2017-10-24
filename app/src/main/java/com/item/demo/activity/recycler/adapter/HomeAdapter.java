package com.item.demo.activity.recycler.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.item.demo.R;

import java.util.List;

/**
 * Created by wuzongjie on 2017/10/23.
 */

public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    public HomeAdapter(List<String> data) {
        super(R.layout.adapter_home_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text, item);
    }
}
