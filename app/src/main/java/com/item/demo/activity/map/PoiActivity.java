package com.item.demo.activity.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.item.demo.R;
import com.item.demo.adapter.PoiAdapter;
import com.item.demo.lbs.ILbsLayer;
import com.item.demo.lbs.LocationInfo;
import com.item.demo.lbs.MapLbsLayerImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索界面
 */
public class PoiActivity extends AppCompatActivity {
    @BindView(R.id.ll_search_cancel)
    LinearLayout llCancel; // 取消
    @BindView(R.id.edt_search_where)
    EditText edtSearch; // 搜索的内容
    @BindView(R.id.lv_search)
    ListView mListView; // ListView控件
    private PoiAdapter mAdapter; // 适配器
    private ILbsLayer mLbsLayer;
    private List<LocationInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        LocationInfo info = (LocationInfo) intent.getSerializableExtra("position");
        mLbsLayer = new MapLbsLayerImpl(this);
        if (info != null) {
            mLbsLayer.poiBoundSearch(info, new ILbsLayer.OnSearchedListener() {
                @Override
                public void onSearched(List<LocationInfo> results) {
                    mData = results;
                    updatePoiList(results);
                }
            });
        }
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString().trim())) {
                    // 关键字搜索推荐地点
                    mLbsLayer.poiSearch(editable.toString().trim(), new ILbsLayer.OnSearchedListener() {
                        @Override
                        public void onSearched(List<LocationInfo> results) {
                            updatePoiList(results);
                        }
                    });
                } else {
                    if (mAdapter != null) mAdapter.setData(mData);
                }
            }
        });
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                PoiActivity.this.overridePendingTransition(0, R.anim.slide_out_down);
            }
        });
    }

    private void updatePoiList(List<LocationInfo> results) {
        if (mAdapter == null) {
            mAdapter = new PoiAdapter(getApplicationContext(), results);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(results);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocationInfo info = (LocationInfo) mAdapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("data", info);
                setResult(2, intent);
                finish();
                PoiActivity.this.overridePendingTransition(0, R.anim.slide_out_down);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, R.anim.slide_out_down);
    }
}
