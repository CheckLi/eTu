package com.yitu.etu.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yitu.etu.R;
import com.yitu.etu.ui.adapter.PoiSearchAdapter;
import com.yitu.etu.widget.MapContainer;

import java.util.ArrayList;

public class ShareMyLocationActivity extends BaseActivity {
    LatLonPoint point;
    private MapsFragment2 mapsFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_map_select;
    }

    @Override
    public void initActionBar() {
        setTitle("地址选择");
        setRightText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });
    }


    public void getAddress() {
        if (point != null) {
            GeocodeSearch geocoderSearch = new GeocodeSearch(ShareMyLocationActivity.this);
            geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                    Intent intent = new Intent();
                    intent.putExtra("address", regeocodeResult.getRegeocodeAddress().getFormatAddress());
                    LatLng latLng = new LatLng(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude(), regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
                    intent.putExtra("latLng", latLng);
                    intent.putExtra("image", getMapUrl(latLng.latitude, latLng.longitude));
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                }
            });
            RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    @Override
    public void initView() {
        mapsFragment = new MapsFragment2();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", (LatLng) getIntent().getParcelableExtra("data"));
        mapsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.rl_content, mapsFragment).commit();
    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    @SuppressLint("ValidFragment")
    public class MapsFragment2 extends SupportMapFragment implements AMapLocationListener, LocationSource {
        // 我的位置监听器
        private OnLocationChangedListener mLocationChangeListener = null;

        private ScrollView mRoot;
        private MapView mMapView;
        private AMap mAmap;
        private AMapLocationClient mLocationClient;
        private AMapLocationClientOption mLocationOption;
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

        private Marker marker;
        private PoiSearchAdapter poiSearchAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mRoot = (ScrollView)inflater.inflate(R.layout.activity_share_my_location, container,
                    false);
            ListView listView = (ListView) mRoot.findViewById(R.id.listView);
            MapContainer head = (MapContainer) mRoot.findViewById(R.id.mapContainer);
            head.scrollView = mRoot;
            poiSearchAdapter = new PoiSearchAdapter(getContext());
            listView.setAdapter(poiSearchAdapter);
            mMapView = (MapView) mRoot.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mAmap = mMapView.getMap();

            mAmap.getUiSettings().setMyLocationButtonEnabled(false);
            mAmap.getUiSettings().setLogoPosition(
                    AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// logo位置
            mAmap.getUiSettings().setScaleControlsEnabled(false);// 标尺开关
            mAmap.getUiSettings().setCompassEnabled(false);// 指南针开关
            mAmap.getUiSettings().setZoomControlsEnabled(false);
            Bundle bundle = getArguments();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PoiItem data = poiSearchAdapter.getItem(position);
                    if (data != null) {
                        mRoot.scrollTo(0,0);
                        mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
                        LatLng latLng = new LatLng(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude());
                        mAmap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

                    }
                }
            });
            if (bundle.getParcelable("data") != null) {
                MarkerOptions markerOption = new MarkerOptions();
                mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
                LatLng latLng = (LatLng) bundle.getParcelable("data");
                mAmap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                markerOption.position(latLng);
                markerOption.draggable(false);//设置Marker可拖动
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.icon0)));
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.setFlat(true);//设置marker平贴地图效果
                mAmap.addMarker(markerOption);
            } else {
                mAmap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        if (marker != null) {
                            point = new LatLonPoint(mAmap.getCameraPosition().target.latitude, mAmap.getCameraPosition().target.longitude);
                            marker.setPosition(mAmap.getCameraPosition().target);
                        }
                    }

                    @Override
                    public void onCameraChangeFinish(CameraPosition cameraPosition) {
                        if (marker != null) {
                            GeocodeSearch geocoderSearch = new GeocodeSearch(getContext());
                            geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                                @Override
                                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                                    PoiSearch.Query query = new PoiSearch.Query("", "", regeocodeResult.getRegeocodeAddress().getCityCode() + "");
                                    PoiSearch poiSearch = new PoiSearch(getContext(), query);
                                    query.setPageNum(1);
                                    query.setPageSize(20);
                                    poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude(), regeocodeResult.getRegeocodeQuery().getPoint().getLongitude()), 1000));
                                    poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                                        @Override
                                        public void onPoiSearched(PoiResult poiResult, int i) {
                                            if (i == 1000) {
                                                ArrayList<PoiItem> aa = poiResult.getPois();
                                                Log.e("1", "dsa");
                                                poiSearchAdapter.addData(aa);
                                            }
                                            Log.e("1", "dsa");
                                        }

                                        @Override
                                        public void onPoiItemSearched(PoiItem poiItem, int i) {

                                            Log.e("1", "dsa");
                                        }
                                    });
                                    poiSearch.searchPOIAsyn();

                                }

                                @Override
                                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                                }
                            });
                            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude), 200, GeocodeSearch.AMAP);
                            geocoderSearch.getFromLocationAsyn(query);

                        }
                    }
                });
                initMyLocation();
            }
            return mRoot;
        }


        /**
         * 初始化定位服务
         */
        private void initLocation() {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationClient.setLocationListener(this);
            // 初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            // 设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            // 设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
            // 设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);
            // 设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);
            // 给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 启动定位
            mLocationClient.startLocation();
        }

        /**
         * 初始化我的定位
         */
        private void initMyLocation() {
            mAmap.setLocationSource(this);
            mAmap.setMyLocationEnabled(true);
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
            mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
            mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            mMapView.onPause();
            if (mLocationClient != null) {
                mLocationClient.stopLocation();
            }
//        deactivate();
        }

        @Override
        public void onResume() {
            // TODO Auto-generated method stub
            super.onResume();
            mMapView.onResume();
            if (mLocationClient != null) {
                mLocationClient.startLocation();
            }
        }

        @Override
        public void onDestroy() {
            // TODO Auto-generated method stub
            super.onDestroy();
        }


        @Override
        public void onSaveInstanceState(Bundle arg0) {
            // TODO Auto-generated method stub
            super.onSaveInstanceState(arg0);
            mMapView.onSaveInstanceState(arg0);
        }


        @Override
        public void onDestroyView() {
            // TODO Auto-generated method stub
            super.onDestroyView();
            if (mLocationClient != null) {
                mLocationClient.onDestroy();
            }
            mMapView.onDestroy();
        }

        @Override
        public void deactivate() {
            mLocationChangeListener = null;
            if (mLocationClient != null) {

                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
                mLocationClient = null;
            }
        }

        @Override
        public void activate(OnLocationChangedListener listener) {
            mLocationChangeListener = listener;
            if (mLocationClient == null) {
                initLocation();
            }
        }

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0 && marker == null) {
                    mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
                    mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    MarkerOptions markerOption = new MarkerOptions();
                    point = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    markerOption.position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                    markerOption.draggable(true);//设置Marker可拖动
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.icon124)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(true);//设置marker平贴地图效果
                    marker = mAmap.addMarker(markerOption);
                }
            }
        }
    }

    private Uri getMapUrl(double x, double y) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" + y + "," + x +
                "&zoom=17&scale=2&size=600*400&markers=mid,,A:" + y + ","
                + x + "&key=" + "be40ebd66fbbe4358c331c58d69ae086";
        return Uri.parse(url);
    }
}
