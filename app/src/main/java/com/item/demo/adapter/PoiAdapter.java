package com.item.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.item.demo.R;
import com.item.demo.lbs.LocationInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuzongjie on 2017/10/27.
 * 搜索界面的适配器
 */

public class PoiAdapter extends BaseAdapter {
    private Context mContext;
    private List<LocationInfo> data;

    public PoiAdapter(Context context, List<LocationInfo> data) {
        this.mContext = context;
        this.data = data;
    }

    public void setData(List<LocationInfo> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_poi_layout, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvTitle.setText(data.get(i).getName());
        holder.tvSnippet.setText(data.get(i).getDistrict());
        return view;
    }

     static class ViewHolder {
        @BindView(R.id.item_search_title)
        TextView tvTitle;
        @BindView(R.id.item_search_snippet)
        TextView tvSnippet;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
