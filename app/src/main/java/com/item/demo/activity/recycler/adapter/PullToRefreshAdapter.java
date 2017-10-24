package com.item.demo.activity.recycler.adapter;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.item.demo.R;
import com.item.demo.activity.recycler.entity.MyStatus;
import com.item.demo.utils.ToastUtils;
import com.item.demo.utils.Utils;


/**
 * 适配器
 * Created by wuzongjie on 2017/10/23.
 */

public class PullToRefreshAdapter extends BaseQuickAdapter<MyStatus, BaseViewHolder> {


    public PullToRefreshAdapter() {
        super(R.layout.layout_animation, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyStatus item) {
        // viewHolder.getLayoutPosition 获取当前item的position
        switch (helper.getLayoutPosition() % 3) {
            case 0:
                helper.setImageResource(R.id.img, R.drawable.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.drawable.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.drawable.animation_img3);
                break;
        }
        helper.setText(R.id.tweetName, item.getText());
        String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        ((TextView) helper.getView(R.id.tweetText)).setText("landscapes and nedes");
        ((TextView) helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            // ToastUtils.showShortToast("事件触发了 landscapes and nedes");
            ToastUtils.showToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);
        }
    };
}
