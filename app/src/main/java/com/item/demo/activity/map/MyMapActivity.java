package com.item.demo.activity.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.item.demo.R;
import com.item.demo.lbs.ILbsLayer;
import com.item.demo.lbs.LocationInfo;
import com.item.demo.lbs.MapLbsLayerImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMapActivity extends AppCompatActivity {
    private ILbsLayer mLbsLayer; // 地图接口
    private LocationInfo mStartLocation; // 开始坐标
    private LocationInfo mEndLocation; // 结束坐标
    private Bitmap mStartBit; // 开始的图标
    private Bitmap mEndBit; // 结束的图标
    private Bitmap mLocationBit; // 定位的图标
    @BindView(R.id.tv_city)
    TextView tvCity; // 定位的城市
    @BindView(R.id.tv_start)
    TextView tvStart; // 开始的位置控件
    @BindView(R.id.tv_end)
    TextView tvEnd; // 结束位置的控件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        ButterKnife.bind(this);
        mLbsLayer = new MapLbsLayerImpl(this);
        mLbsLayer.onCreate(savedInstanceState);

        // TODO: 2017/10/27  加个动态权限
        mLbsLayer.startLocationMap(); // 开始定位 这里要加个动态权限
        mLbsLayer.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
            @Override
            public void onLocationChanged(LocationInfo locationInfo) {

            }

            @Override
            public void onLocation(LocationInfo locationInfo) {
                // 记录起点
                mStartLocation = locationInfo;
                // 设置城市
                tvCity.setText(mLbsLayer.getCity());
                tvStart.setText(locationInfo.getName());
                // 首次定位，添加当前坐标的标记
                addLocationMarker();
            }
        });
        // 添加地图到容器里
        FrameLayout mapViewContainer = (FrameLayout)findViewById(R.id.map_container);
        mapViewContainer.addView(mLbsLayer.getMapView());
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击去哪跳转到搜索的界面
               Intent intent = new Intent(MyMapActivity.this,PoiActivity.class);
                intent.putExtra("position",mStartLocation);
                startActivityForResult(intent,1);
                MyMapActivity.this.overridePendingTransition(R.anim.slide_in_up,R.anim.slide_no);
            }
        });

    }

    private void addLocationMarker() {
        if(mLocationBit == null || mLocationBit.isRecycled()){
            mLocationBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.navi_map_gps_locked);
        }
        mLbsLayer.addOnUpdateMarker(mStartLocation,mLocationBit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLbsLayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLbsLayer.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLbsLayer.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLbsLayer.onDestroy();
    }
}
