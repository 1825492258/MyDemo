package com.item.demo.activity.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.item.demo.R;
import com.item.demo.lbs.ILbsLayer;
import com.item.demo.lbs.LocationInfo;
import com.item.demo.lbs.MapLbsLayerImpl;
import com.item.demo.lbs.RouteInfo;
import com.item.demo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMapActivity extends AppCompatActivity {
    private ILbsLayer mLbsLayer; // 地图接口
    private LocationInfo mStartLocation; // 开始坐标
    private LocationInfo mEndLocation; // 结束坐标
    private Bitmap mStartBit; // 开始的图标
    private Bitmap mEndBit; // 结束的图标
    private Bitmap mLocationBit; // 定位的图标
    private Bitmap mPositionBit; // 地图中心的图标
    private Bitmap mDriverBit; // 车辆的图标
    private LocationInfo mPositionLocation;
    private boolean isMapLoad;
    private boolean isFirst;
    @BindView(R.id.tv_city)
    TextView tvCity; // 定位的城市
    @BindView(R.id.tv_start)
    TextView tvStart; // 开始的位置控件
    @BindView(R.id.tv_end)
    TextView tvEnd; // 结束位置的控件
    @BindView(R.id.optArea)
    LinearLayout mOptArea; // 操作区
    @BindView(R.id.tips_info)
    TextView tvTipInfo; // 信息
    @BindView(R.id.btn_call_driver)
    Button btnCallDriver; // 呼叫快车
    @BindView(R.id.btn_cancel)
    Button btnCancel; // 取消

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        ButterKnife.bind(this);
        mLbsLayer = new MapLbsLayerImpl(this);
        mLbsLayer.onCreate(savedInstanceState);

        if (ToastUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            mLbsLayer.startLocationMap(); // 开始定位 这里要加个动态权限
        } else {
            ToastUtils.requestPermission(this, 0x01, Manifest.permission.READ_PHONE_STATE);
        }
        // 定位改变
        mLbsLayer.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
            @Override
            public void onLocationChanged(LocationInfo locationInfo) {

            }

            @Override
            public void onLocation(LocationInfo locationInfo) { // 地图定位成功
                // 记录起点
                mStartLocation = locationInfo;
                // 设置城市
                tvCity.setText(mLbsLayer.getCity());
                tvStart.setText(locationInfo.getName());
                // 首次定位，添加当前坐标的标记
                addLocationMarker();
               // mPositionLocation = new LocationInfo("0000", locationInfo.getLatitude(), locationInfo.getLongitude());
            }
        });
        // 地图加载完成
        mLbsLayer.onMapLoaded(new ILbsLayer.OnMapLoadDone() {
            @Override
            public void onMapDone() {
                Log.d("jiejie", "地图加载完成的回调");
                // 这个接口 好像用不上了
//                if(mPositionLocation!=null){
//                    getNearDrivers(mPositionLocation.getLatitude(), mPositionLocation.getLongitude());
//                    addMapDoneMarker();
//                }
            }
        });
        // 地图中心发生了改变
        mLbsLayer.onMapChange(new ILbsLayer.OnMapChangeListener() {
            @Override
            public void onChangeFinish(LocationInfo result) {
                // 地图中心改变完成
                if (!isFirst) {
                    isFirst = true;
                    mPositionLocation = result;
                    getNearDrivers(mPositionLocation.getLatitude(), mPositionLocation.getLongitude());
                    addMapDoneMarker(); // 给地图添加中心点Marker
                    mLbsLayer.startJumpAnimation();
                }
                if (isMapLoad) return;

                float distance = mLbsLayer.getTwoDistance(mPositionLocation, result);
                if (distance > 5000) { // 如果2点移动的距离大于3000的话
                    Log.d("jiejie", "距离----" + distance);
                    mPositionLocation = result;
                    mLbsLayer.clearAllMarkers();
                    addLocationMarker();
                    getNearDrivers(mPositionLocation.getLatitude(), mPositionLocation.getLongitude());
                    addMapDoneMarker();
                    mLbsLayer.startJumpAnimation();
                }
            }

            @Override
            public void onChange() {
                // 地图中心改变中
            }
        });
        // 添加地图到容器里
        FrameLayout mapViewContainer = (FrameLayout) findViewById(R.id.map_container);
        mapViewContainer.addView(mLbsLayer.getMapView());
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击去哪跳转到搜索的界面
                Intent intent = new Intent(MyMapActivity.this, PoiActivity.class);
                intent.putExtra("position", mStartLocation);
                startActivityForResult(intent, 1);
                MyMapActivity.this.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_no);
            }
        });
        btnCallDriver.setOnClickListener(null); // 点击呼叫快车
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击取消
                restoreUI();
            }
        });
    }

    List<LocationInfo> mData = new ArrayList<>();

    /**
     * 获取附近的车辆
     *
     * @param latitude  精度
     * @param longitude 维度
     */
    private void getNearDrivers(double latitude, double longitude) {
        mData.clear();
        mData.add(new LocationInfo("001", latitude - new Random().nextInt(20) * 0.0008, longitude + new Random().nextInt(50) * 0.0008, 50f));
        mData.add(new LocationInfo("002", latitude - new Random().nextInt(20) * 0.0008, longitude + new Random().nextInt(50) * 0.0008, 30f));
        mData.add(new LocationInfo("003", latitude - new Random().nextInt(20) * 0.0008, longitude - new Random().nextInt(50) * 0.0008, 80f));
        mData.add(new LocationInfo("004", latitude - new Random().nextInt(20) * 0.0008, longitude + new Random().nextInt(60) * 0.0008, 60f));
        mData.add(new LocationInfo("005", latitude - new Random().nextInt(20) * 0.0008, longitude - new Random().nextInt(70) * 0.0008, 300f));
        mData.add(new LocationInfo("006", latitude + new Random().nextInt(20) * 0.0008, longitude + new Random().nextInt(80) * 0.0008, 70f));
        mData.add(new LocationInfo("007", latitude + new Random().nextInt(20) * 0.0008, longitude - new Random().nextInt(50) * 0.0008, 1f));
        mData.add(new LocationInfo("008", latitude + new Random().nextInt(20) * 0.0008, longitude + new Random().nextInt(60) * 0.0008, 200f));
        for (LocationInfo location : mData) {
            showLocationChange(location);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 2 && data != null) {
                    isMapLoad = true;
                    LocationInfo info = (LocationInfo) data.getSerializableExtra("data");
                    tvEnd.setText(info.getName());
                    mEndLocation = info;
                    showRoute(mStartLocation, mEndLocation);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x01: // 请求定位权限的返回
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mLbsLayer.startLocationMap();
                }
                break;
        }
    }

    /**
     * 绘制起点 终点路径
     *
     * @param mStartLocation 起点
     * @param mEndLocation   终点
     */
    private void showRoute(final LocationInfo mStartLocation, final LocationInfo mEndLocation) {
        mLbsLayer.clearAllMarkers();
        addStartMarker(); // 添加起点Marker
        addEndMarker(); // 添加终点Marker
        mLbsLayer.driverRoute(mStartLocation,
                mEndLocation,
                Color.GREEN,
                new ILbsLayer.OnRouteCompleteListener() {
                    @Override
                    public void onComplete(RouteInfo result) {
                        Log.d("jiejie", result.getTaxiCost() + " " + result.getDistance() + "  " + result.getDuration());
                        mLbsLayer.moveCameraTwo(mStartLocation, mEndLocation);
                        mOptArea.setVisibility(View.VISIBLE);
                        tvTipInfo.setText("全程" + result.getDistance() + "公里,  预计" + result.getTaxiCost() + "元，" + result.getDuration() + "分钟到达");
                    }
                });
    }

    /**
     * 恢复界面
     */
    private void restoreUI() {
        // 清楚地图上所有的标记
        mLbsLayer.clearAllMarkers();
        // 添加定位标记
        addLocationMarker();
        // 恢复地图视野
        mLbsLayer.moveCameraToPoint(mPositionLocation, 14);
        // 获取附近司机
        getNearDrivers(mPositionLocation.getLatitude(), mPositionLocation.getLongitude());
        // 添加中心的Marker
        addMapDoneMarker();
        isMapLoad = false;
        // 隐藏操作栏
        mOptArea.setVisibility(View.GONE);
    }

    /**
     * 添加定位点
     */
    private void addLocationMarker() {
        if (mLocationBit == null || mLocationBit.isRecycled()) {
            mLocationBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.navi_map_gps_locked);
        }
        mLbsLayer.addOnUpdateMarker(mStartLocation, mLocationBit);
    }

    /**
     * 添加地图中心点
     */
    private void addMapDoneMarker() {
        if (mPositionBit == null || mPositionBit.isRecycled()) {
            mPositionBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_position);
        }
        mLbsLayer.addMarkerCenter(mPositionBit);
        // mLbsLayer.addOnMydateMarker(mPositionLocation, mPositionBit);
    }

    /**
     * 显示司机的标记
     *
     * @param locationInfo
     */
    public void showLocationChange(LocationInfo locationInfo) {
        if (mDriverBit == null || mDriverBit.isRecycled()) {
            mDriverBit = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        }
        mLbsLayer.addOnUpdateMarker(locationInfo, mDriverBit);
    }

    /**
     * 添加开始坐标
     */
    private void addStartMarker() {
        if (mStartBit == null || mStartBit.isRecycled()) {
            mStartBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.start);
        }
        mLbsLayer.addOnUpdateMarker(mStartLocation, mStartBit);
    }

    /**
     * 添加结束坐标
     */
    private void addEndMarker() {
        if (mEndBit == null || mEndBit.isRecycled()) {
            mEndBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.end);
        }
        mLbsLayer.addOnUpdateMarker(mEndLocation, mEndBit);
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
