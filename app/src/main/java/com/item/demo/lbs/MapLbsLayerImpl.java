package com.item.demo.lbs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzongjie on 2017/10/27.
 * 高德地图的实现类
 */

public class MapLbsLayerImpl implements ILbsLayer {
    private static final String TAG = "jiejie";
    private static final String KEY_MY_MARKERE = "1000";
    private Context mContext; // 上下文对象
    private MapView mapView; // 地图视图对象
    private AMap aMap; // 地图管理对象
    // 定位方面
    private AMapLocationClient mLocationClient; // 声明mLocationClient对象
    private AMapLocationClientOption mLocationOption = null; // 声明mLocationOption对象
    // 地图位置变化回调对象
    private LocationSource.OnLocationChangedListener mMapLocationChangeListener;
    private boolean firstLocation = true; // 第一次定位
    private CommonLocationChangeListener mLocationChangeListener;
    // 当前的城市
    private String mCity;
    // 管理地图标记的集合
    private Map<String, Marker> markerMap = new HashMap<>();

    public MapLbsLayerImpl(Context context) {
        this.mContext = context;
        // 创建视图对象
        mapView = new MapView(mContext);
        // 获取地图管理器
        aMap = mapView.getMap();
    }

    /**
     * 获取地图对象
     */
    @Override
    public View getMapView() {
        return mapView;
    }

    /**
     * 获取当前城市
     */
    @Override
    public String getCity() {
        return mCity;
    }

    /**
     * 开始定位
     */
    @Override
    public void startLocationMap() {
        // 设置定位
        if (mLocationClient == null) {
            // 创建地图定位对象
            mLocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(6000);
            // mLocationOption.setOnceLocation(true); // 单次定位
            mLocationClient.setLocationOption(mLocationOption); // 设置定位参数
        }
        setUpMap();
    }

    @Override
    public void setLocationChangeListener(CommonLocationChangeListener locationChangeListener) {
        mLocationChangeListener = locationChangeListener;
    }

    private void setUpMap() {
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                mMapLocationChangeListener = onLocationChangedListener;
            }

            @Override
            public void deactivate() {
                if (mLocationClient != null) {
                    mLocationClient.stopLocation();
                    mLocationClient.onDestroy();
                }
                mLocationClient = null;
            }
        });
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
    }

    private void setUpLocation() {
        // 设置监听器
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.d(TAG, "定位--------" + aMapLocation.getLatitude());
                // 定位变化位置
                if (mMapLocationChangeListener != null) {
                    // 当前城市
                    mCity = aMapLocation.getCity();
                    LocationInfo locationInfo = new LocationInfo(aMapLocation.getLatitude(),
                            aMapLocation.getLongitude());
                    locationInfo.setName(aMapLocation.getPoiName());
                    locationInfo.setKey(KEY_MY_MARKERE);
                    if (firstLocation) {
                        firstLocation = false;
                        moveCameraToPoint(locationInfo, 14);
                        if (mLocationChangeListener != null) {
                            mLocationChangeListener.onLocation(locationInfo);
                        }
                    }
                    if (mLocationChangeListener != null) {
                        mLocationChangeListener.onLocationChanged(locationInfo);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    /**
     * 搜索 输入内容自动提示
     */
    @Override
    public void poiSearch(String key, final OnSearchedListener listener) {
        // 1.构造 InputtipsQuery 对象 设置搜索条件
        // 第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputQuery = new InputtipsQuery(key, null);

        // 2. 构造 Inputtips 对象，并设置监听
        Inputtips inputTips = new Inputtips(mContext, inputQuery);

        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> tipList, int rCode) {
                Log.d("jiejie", "---code-" + rCode + "  " + tipList.size() + tipList.get(0).getAddress());
                // 4.通过回调接口 onGetInputtips 解析返回的结果，获取输入提示返回的信息
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    // 正确返回解析结果
                    List<LocationInfo> locationInfos = new ArrayList<LocationInfo>();
                    for (int i = 0; i < tipList.size(); i++) {
                        Tip tip = tipList.get(i);
                        LocationInfo locationInfo = new LocationInfo(tip.getPoint().getLatitude()
                                , tip.getPoint().getLongitude());
                        locationInfo.setName(tip.getName());
                        locationInfo.setDistrict(tip.getAddress());
                        locationInfos.add(locationInfo);
                    }
                    listener.onSearched(locationInfos);
                }
            }
        });
        // 3.开始异步搜索
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void poiBoundSearch(LocationInfo info, final OnSearchedListener listener) {
        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        query.setPageSize(10);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(info.getLatitude(), info.getLongitude()), 1000));
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int code) {
                if (code == AMapException.CODE_AMAP_SUCCESS) {
                    // 正确返回解析结果
                    List<LocationInfo> locationInfos = new ArrayList<LocationInfo>();
                    for (int i = 0; i < poiResult.getPois().size(); i++) {
                        PoiItem item = poiResult.getPois().get(i);
                        LocationInfo locationInfo = new LocationInfo(item.getLatLonPoint().getLatitude(),
                                item.getLatLonPoint().getLongitude());
                        locationInfo.setName(item.getTitle());
                        locationInfo.setDistrict(item.getSnippet());
                        locationInfos.add(locationInfo);
                    }
                    listener.onSearched(locationInfos);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    /**
     * 在地图上添加更新Marker
     */
    @Override
    public void addOnUpdateMarker(LocationInfo locationInfo, Bitmap bitmap) {
        Marker storedMarker = markerMap.get(locationInfo.getKey());
        LatLng latLng = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
        if (storedMarker != null) {
            // 如果已经存在则更新角度，位置
            storedMarker.setPosition(latLng);
            storedMarker.setRotateAngle(locationInfo.getRotation());
        } else {
            // 如果不存在则创建
            MarkerOptions options = new MarkerOptions();
            BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bitmap);
            options.icon(des);
            options.anchor(0.5f, 0.5f);
            options.position(latLng);
            Marker marker = aMap.addMarker(options);
            marker.setRotateAngle(locationInfo.getRotation());
            markerMap.put(locationInfo.getKey(), marker);
        }
    }

    /**
     * 缩放相机到地图的某个点
     */
    @Override
    public void moveCameraToPoint(LocationInfo locationInfo, int scale) {
        LatLng latLng = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
        /*
         *CameraPosition(LatLng target, float zoom, float tilt, float bearing)
         * LatLng 目标位置的屏幕中心点经纬度坐标。
         * zoom 目标可视区域的缩放级别。
         * tilt 目标可视区域的倾斜度，以角度为单位。
         * bearing 可视区域指向的方向，以角度为单位，从正北向逆时针方向计算，从0 度到360 度。
         */
        CameraUpdate up = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latLng, scale, 0, 0
        ));
        aMap.moveCamera(up);
    }

    /**
     * 将地图移动到2点之间的视野范围
     */
    @Override
    public void moveCameraTwo(LocationInfo locationInfo1, LocationInfo locationInfo2) {
        LatLng latLng1 = new LatLng(locationInfo1.getLatitude(), locationInfo1.getLongitude());
        LatLng latLng2 = new LatLng(locationInfo2.getLatitude(), locationInfo2.getLongitude());
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(latLng1);
        b.include(latLng2);
        LatLngBounds latLngBounds = b.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100));
    }

    @Override
    public void onCreate(Bundle state) {
        mapView.onCreate(state);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        if (mLocationClient != null) setUpLocation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        mapView.onPause();
        if (mLocationClient != null) mLocationClient.stopLocation();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        if (mLocationClient != null) mLocationClient.onDestroy();
    }

    /**
     * 清除地图的marker
     */
    @Override
    public void clearAllMarkers() {
        aMap.clear();
        markerMap.clear();
    }
}
