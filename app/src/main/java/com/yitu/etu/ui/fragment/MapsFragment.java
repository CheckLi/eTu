package com.yitu.etu.ui.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
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
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yitu.etu.R;
import com.yitu.etu.entity.MapFriendEntity;
import com.yitu.etu.entity.MapOrderSceneEntity;
import com.yitu.etu.entity.MapSceneEntity;
import com.yitu.etu.entity.MerchantBaseEntity;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.CircleFirendActivity;
import com.yitu.etu.ui.activity.MapSearchActivity;
import com.yitu.etu.util.PermissionUtil;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @className:MapsFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月10日 14:18
 */
public class MapsFragment extends SupportMapFragment implements
        AMapLocationListener, LocationSource, AMap.InfoWindowAdapter, View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    private int fragmentId;

    private MapView mMapView;
    private AMap mAmap;
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    // 声明定位回调监听器
    public AMapLocationListener mLocationListener;
    private AMapLocation mMyLocationPoint;
    // 我的位置监听器
    private OnLocationChangedListener mLocationChangeListener = null;

    private View mRoot;
    private ImageView image;
    private ArrayList<Mmark> markers = new ArrayList<Mmark>();
    private Marker markerClick;
    private int a = 0;
    LayoutInflater inflater;

    public static final int type_order_scene = 3;
    public static final int type_friend = 1;
    public static final int type_scene = 2;
    View btn_type_friend;
    int type = type_scene;
    LatLng centerLatLng;
    private Mmark fd_mark;
    private View dialog;
    private ViewGroup menu;
    boolean menuOpen = true;
    private boolean animating;
    private ImageView mButton;
    MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
    private String sex = "";
    private ImageView dialog_image;
    private TextView tv_title;
    private TextView tv_address;
    private TextView tv_yj_num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        mRoot = inflater.inflate(R.layout.fragment_map_layout, container,
                false);
        mMapView = (MapView) mRoot.findViewById(R.id.map);
        btn_type_friend = mRoot.findViewById(R.id.btn_type_friend);
        mRoot.findViewById(R.id.btn_friend).setOnClickListener(this);
        mRoot.findViewById(R.id.btn_order_scene).setOnClickListener(this);
        mRoot.findViewById(R.id.btn_scene).setOnClickListener(this);
        mRoot.findViewById(R.id.btn_province).setOnClickListener(this);
        btn_type_friend.setOnClickListener(this);
        mButton = (ImageView) mRoot.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mRoot.findViewById(R.id.btn_location).setOnClickListener(this);
        mRoot.findViewById(R.id.btn_nai).setOnClickListener(this);


        menu = (ViewGroup) mRoot.findViewById(R.id.menu);

        dialog = mRoot.findViewById(R.id.dialog);
        dialog_image = (ImageView) mRoot.findViewById(R.id.dialog_image);
        tv_title = (TextView) mRoot.findViewById(R.id.tv_title);
        tv_address = (TextView) mRoot.findViewById(R.id.tv_address);
        tv_yj_num = (TextView) mRoot.findViewById(R.id.tv_yj_num);
        mMapView.onCreate(savedInstanceState);
        image = (ImageView) mRoot.findViewById(R.id.image);
        if (!PermissionUtil.hasPermissions(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PermissionUtil.requestPermissions(this, 100, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        initMap();
        return mRoot;
    }

    public void search() {
        Intent intent = new Intent(getContext(), MapSearchActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("lat",mAmap.getCameraPosition().target.latitude);

        intent.putExtra("lng",mAmap.getCameraPosition().target.longitude);
        startActivity(intent);
    }

    <T extends MerchantBaseEntity> void showDialog(T merchantEntity, int type) {
        dialog.setVisibility(View.VISIBLE);
        if (type == type_order_scene) {
//            dialog_image.set
            MapOrderSceneEntity data = (MapOrderSceneEntity) merchantEntity;
            tv_title.setText(data.title);
            tv_address.setText(data.address);
            tv_yj_num.setVisibility(View.GONE);
            GlideApp.with(MapsFragment.this)
                    .load(Urls.address + data.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.icon17).into(dialog_image);

        } else if (type == type_scene) {
            tv_yj_num.setVisibility(View.VISIBLE);
            MapSceneEntity data = (MapSceneEntity) merchantEntity;
            tv_title.setText(data.title);
            tv_address.setText(data.address);
            tv_yj_num.setText(data.yjcount + "条游记");
            GlideApp.with(MapsFragment.this)
                    .load(Urls.address + data.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.icon17).into(dialog_image);

        }
    }

    public void hiddenDialog() {
        dialog.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.checkHaveAllPermissions(grantResults);
    }

    private void initMap() {
        if (mAmap == null) {
            mAmap = mMapView.getMap();
            mAmap.setInfoWindowAdapter(this);
            mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
            //监听地图相机移动
            mAmap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    if (markerClick != null) {
                        markerClick.hideInfoWindow();
                        markerClick = null;
                    }
                    if (fd_mark != null) {
                        fd_mark.hiddenFd();
                        fd_mark = null;
                    }
                    centerLatLng = mAmap.getCameraPosition().target;
                    loadInfo();

                }
            });
            mAmap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {


                    if (marker.getObject() == null) {
                        return true;
                    }
                    markerClick = marker;
                    if (fd_mark != null) {
                        fd_mark.hiddenFd();
                        fd_mark = null;
                    }
                    MerchantBaseEntity merchantEntity = (MerchantBaseEntity) marker.getObject();
                    if (merchantEntity.getType() == type_friend) {
                        marker.showInfoWindow();
                    } else {
                        for (Mmark mmark :
                                markers) {
                            if (mmark.getId().equals(merchantEntity.getId())) {
                                fd_mark = mmark;
                                mmark.showFd();
                                if (merchantEntity.getType() == type_order_scene) {
                                    showDialog((MapOrderSceneEntity) marker.getObject(), merchantEntity.getType());
                                } else if (merchantEntity.getType() == type_scene) {
                                    showDialog((MapSceneEntity) marker.getObject(), merchantEntity.getType());
                                }

//                                break;
                            }
                        }

                    }
//                    Log.e("点击", merchantEntity.getId() + "");
                    return true;
                }
            });
            //点击地图的监听
            mAmap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (markerClick != null) {
                        markerClick.hideInfoWindow();
                        markerClick = null;
                    }
                }
            });

            initMyLocation();
//            loadInfo();
        }
    }


    public void clearMapMarker() {
        for (Mmark marker :
                markers) {
            marker.remove();
        }
        markers.clear();
        fd_mark = null;
        markerClick = null;
        mAmap.clear();

    }

    public void loadInfo() {
        hiddenDialog();
        CloudSearch mCloudSearch = new CloudSearch(getContext());// 初始化查询类
        mCloudSearch.setOnCloudSearchListener(new CloudSearch.OnCloudSearchListener() {
            @Override
            public void onCloudSearched(CloudResult cloudResult, int i) {
                Log.e("CloudSearch", "CloudSearch");
                if (cloudResult != null) {
                    ArrayList<CloudItem> cloudItems = cloudResult.getClouds();
                    if (cloudItems != null && cloudItems.size() > 0) {
                        ArrayList<MapFriendEntity> mapFirendEntitys = new ArrayList<>();
                        ArrayList<MapOrderSceneEntity> mapOrderSceneEntitys = new ArrayList<>();
                        ArrayList<MapSceneEntity> mapSceneEntitys = new ArrayList<>();

                        for (CloudItem cloudItem :
                                cloudItems) {
                            HashMap<String, String> filld = cloudItem.getCustomfield();
                            if (type == type_friend) {
                                MapFriendEntity mapFirendEntity = new MapFriendEntity();
                                mapFirendEntity.image = filld.get("image");
                                mapFirendEntity.user_id = filld.get("user_id");
                                mapFirendEntity.sex = filld.get("sex");
                                mapFirendEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapFirendEntity.address = cloudItem.getSnippet();
                                mapFirendEntity.title = cloudItem.getTitle();
                                mapFirendEntity.id = mapFirendEntity.user_id;
                                if (mapFirendEntitys.size() == 0) {
                                    mapFirendEntitys.add(mapFirendEntity);
                                }
                                for (int n = 0; n < mapFirendEntitys.size(); n++) {
                                    if (mapFirendEntitys.get(n).getId().equals(mapFirendEntity.id)) {
                                        break;
                                    }
                                    if (mapFirendEntitys.size() == n + 1) {
                                        mapFirendEntitys.add(mapFirendEntity);
                                    }
                                }
                            } else if (type == type_order_scene) {
                                MapOrderSceneEntity mapOrderSceneEntity = new MapOrderSceneEntity();
                                mapOrderSceneEntity.is_spot = filld.get("is_spot");
                                mapOrderSceneEntity.image = filld.get("image");
                                mapOrderSceneEntity.title = filld.get("title");
                                mapOrderSceneEntity.title_id = filld.get("title_id");
                                mapOrderSceneEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapOrderSceneEntity.address = cloudItem.getSnippet();
                                mapOrderSceneEntity.id = mapOrderSceneEntity.title_id;
                                if (mapOrderSceneEntitys.size() == 0) {
                                    mapOrderSceneEntitys.add(mapOrderSceneEntity);
                                }
                                for (int n = 0; n < mapOrderSceneEntitys.size(); n++) {
                                    if (mapOrderSceneEntitys.get(n).getId().equals(mapOrderSceneEntity.id)) {
                                        break;
                                    }
                                    if (mapOrderSceneEntitys.size() == n + 1) {
                                        mapOrderSceneEntitys.add(mapOrderSceneEntity);
                                    }
                                }

                            } else if (type == type_scene) {
                                MapSceneEntity mapSceneEntity = new MapSceneEntity();
                                mapSceneEntity.latLonPoint = cloudItem.getLatLonPoint();
                                mapSceneEntity.price = filld.get("price");
                                mapSceneEntity.yjcount = filld.get("yjcount");
                                mapSceneEntity.xjdes = filld.get("xjdes");
                                mapSceneEntity.spot_id = filld.get("spot_id");
                                mapSceneEntity.image = filld.get("image");
                                mapSceneEntity.address = cloudItem.getSnippet();
                                mapSceneEntity.title = cloudItem.getTitle();
                                mapSceneEntity.id = mapSceneEntity.spot_id;
                                if (mapSceneEntitys.size() == 0) {
                                    mapSceneEntitys.add(mapSceneEntity);
                                }
                                for (int n = 0; n < mapSceneEntitys.size(); n++) {
                                    if (mapSceneEntitys.get(n).getId().equals(mapSceneEntity.id)) {
                                        break;
                                    }
                                    if (mapSceneEntitys.size() == n + 1) {
                                        mapSceneEntitys.add(mapSceneEntity);
                                    }
                                }
                            }
                        }
                        if (type == type_friend) {
                            addMarker(mapFirendEntitys, type);


                        } else if (type == type_order_scene) {
                            addMarker(mapOrderSceneEntitys, type);

                        } else if (type == type_scene) {
                            addMarker(mapSceneEntitys, type);
                        }

                    } else {
                        clearMapMarker();
                    }
                } else {
                    clearMapMarker();

                }
            }

            @Override
            public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {
            }
        });
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(new LatLonPoint(
                centerLatLng.latitude, centerLatLng.longitude), 10000);
        try

        {
            String table = "";
            if (type == type_friend) {
                table = "58eb4c60afdf522b2822fda3";
            } else if (type == type_order_scene) {
                table = "5905996cafdf522b281639ab";
            } else if (type == type_scene) {
                table = "58e64edb2376c11620d5b2e7";
            }
            CloudSearch.Query mQuery = new CloudSearch.Query(table, "", bound);
            if (type == type_friend) {
                if (!sex.equals("")) {
                    mQuery.addFilterString("sex", sex);
                }
            }
            mCloudSearch.searchCloudAsyn(mQuery);

        } catch (AMapException e)

        {
            Log.e("CloudSearch", "CloudSearch");
        }
    }

    public <T extends MerchantBaseEntity> void addMarker(ArrayList<T> merchantEntitys, int type) {
        if (merchantEntitys == null || merchantEntitys.size() == 0) {
            clearMapMarker();
        }
        for (int i = 0; i < markers.size(); i++) {
            Mmark mmark = markers.get(i);
            boolean equals = false;
            T merchantEntity3 = null;
            for (T merchantEntity2 : merchantEntitys) {
                if (mmark.getId().equals(merchantEntity2.getId())) {
                    equals = true;
                    merchantEntity3 = merchantEntity2;
                }
            }
            if (!equals) {
                mmark.remove();
                markers.remove(i);
                Log.e("removeid", "" + mmark.getId());
                i = i - 1;
            } else {
                merchantEntitys.remove(merchantEntity3);
            }
        }
        for (MerchantBaseEntity merchantEntity :
                merchantEntitys) {
            markers.add(new Mmark(merchantEntity, type));
        }
//        for (Mmark mmark : markers) {
//            Log.e("id", "" + mmark.getId());
//        }
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
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
        mAmap.getUiSettings().setMyLocationButtonEnabled(false);
        mAmap.setMyLocationEnabled(true);
        mAmap.getUiSettings().setLogoPosition(
                AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// logo位置
        mAmap.getUiSettings().setScaleControlsEnabled(false);// 标尺开关
        mAmap.getUiSettings().setCompassEnabled(false);// 指南针开关
        mAmap.getUiSettings().setZoomControlsEnabled(false);

        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        Log.d(TAG,
                "max = " + mAmap.getMaxZoomLevel() + "min = "
                        + mAmap.getMinZoomLevel());
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
        Log.d(TAG, "deactivate");
        mLocationChangeListener = null;
        if (mLocationClient != null) {

            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        Log.d(TAG, "activate");
        mLocationChangeListener = listener;
        if (mLocationClient == null) {
            initLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
//        Log.d(TAG, "onLocationChanged");
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                // 定位成功回调信息，设置相关消息
                amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();// 获取经度
                amapLocation.getLongitude();// 获取纬度
                amapLocation.getAccuracy();// 获取精度信息
                SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);// 定位时间
                amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果
                amapLocation.getCountry();// 国家信息
                amapLocation.getProvince();// 省信息
                amapLocation.getCity();// 城市信息
                amapLocation.getDistrict();// 城区信息
                amapLocation.getRoad();// 街道信息
                amapLocation.getCityCode();// 城市编码
                amapLocation.getAdCode();// 地区编码
                if (mMyLocationPoint == null) {
                    mMyLocationPoint = amapLocation;
                    if (mMyLocationPoint != null) {
                        mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
                        mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMyLocationPoint.getLatitude(), mMyLocationPoint.getLongitude())));
                    }
                }
                mMyLocationPoint = amapLocation;
                mLocationChangeListener.onLocationChanged(mMyLocationPoint);
            } else {
                // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError",
                        "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        if (marker.getObject() == null) {
            return null;
        }
        MapFriendEntity data = (MapFriendEntity) marker.getObject();
        View view = inflater.inflate(R.layout.pop_map_firend, null,
                false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_distance.setText(canculat(new LatLng(data.getLatLonPoint().getLatitude(), data.getLatLonPoint().getLongitude())));
        tv_title.setText(data.title);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("我2");
            }
        });
        GlideApp.with(MapsFragment.this)
                .load(Urls.address + data.getImage())
                .circleCrop()
                .placeholder(R.drawable.icon17)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .error(R.drawable.icon17)
                .into(image);

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public void toMyLocation(float level) {

        if (mMyLocationPoint != null) {
            mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMyLocationPoint.getLatitude(), mMyLocationPoint.getLongitude())));
            mAmap.moveCamera(CameraUpdateFactory.zoomTo(level));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_friend:
                if (type != type_friend) {
                    type = type_friend;
                    btn_type_friend.setVisibility(View.VISIBLE);
                    sex = "";
                    clearMapMarker();
                    toMyLocation(16);
                    loadInfo();
                }
                break;
            case R.id.btn_order_scene:
                if (type != type_order_scene) {
                    type = type_order_scene;
                    btn_type_friend.setVisibility(View.GONE);
                    clearMapMarker();
                    toMyLocation(12);
                    loadInfo();
                }
                break;
            case R.id.btn_scene:
                if (type != type_scene) {
                    type = type_scene;
                    btn_type_friend.setVisibility(View.GONE);
                    clearMapMarker();
                    toMyLocation(12);
                    loadInfo();
                }
                break;

            case R.id.button:
                playMenuAnimation();
                break;
            case R.id.btn_location:
                toMyLocation(13);
                Intent intent = new Intent(getContext(), CircleFirendActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_nai:
                Poi start = new Poi("三元桥", new LatLng(39.96087, 116.45798), "");
/**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
                Poi end = new Poi("北京站", new LatLng(39.904556, 116.427231), "B000A83M61");
                List<Poi> wayList = new ArrayList();//途径点目前最多支持3个。
                wayList.add(new Poi("团结湖", new LatLng(39.93413, 116.461676), ""));
                wayList.add(new Poi("呼家楼", new LatLng(39.923484, 116.461327), ""));
                wayList.add(new Poi("华润大厦", new LatLng(39.912914, 116.434247), ""));
                AmapNaviPage.getInstance().showRouteActivity(getActivity(), new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
                    @Override
                    public void onInitNaviFailure() {

                    }

                    @Override
                    public void onGetNavigationText(String s) {

                    }

                    @Override
                    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

                    }

                    @Override
                    public void onArriveDestination(boolean b) {

                    }

                    @Override
                    public void onStartNavi(int i) {

                    }

                    @Override
                    public void onCalculateRouteSuccess(int[] ints) {

                    }

                    @Override
                    public void onCalculateRouteFailure(int i) {

                    }

                    @Override
                    public void onStopSpeaking() {

                    }
                });
//                startActivity(new Intent(getContext(), NavActivity.class));
                break;
            case R.id.btn_type_friend:
                showSexPop(v);
                break;
            case R.id.btn_province:
//                showAreaDialog();
                search();
                break;


            default:
                break;
        }
    }

    public void showNoSceneDialog() {

    }

    public void showSexPop(View v) {
//        Tools.getPopupWindow(getContext(),new String[]{"那是","那是2ad"});
//        PopupWindow popupWindow = new PopupWindow(getContext());
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        TextView text2 = new TextView(getContext());
//        text2.setText("dsadsadsad");
//        text2.setBackgroundColor(getResources().getColor(R.color.white));
//        popupWindow.setContentView(text2);
        Tools.getPopupWindow(getContext(), new String[]{"全部用户", "只看女生", "只看男生"}, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (!sex.equals("")) {
                        sex = "";
                        clearMapMarker();
                        loadInfo();
                    }
                } else if (position == 1) {
                    if (!sex.equals("0")) {
                        sex = "0";
                        clearMapMarker();
                        loadInfo();
                    }
                } else if (position == 2) {
                    if (!sex.equals("1")) {
                        sex = "1";
                        clearMapMarker();
                        loadInfo();
                    }
                }
            }
        }, null).showAsDropDown(v, 20, 0);
    }


    public void showAreaDialog() {
        Dialog dialog = new Dialog(getContext(), R.style.LoadingDialog);
        TextView text = new TextView(getContext());
        text.setText("dsadsadsad");
        text.setBackgroundColor(getResources().getColor(R.color.white));
        dialog.setContentView(text);
        dialog.show();
    }

    private void playMenuAnimation() {
        if (!animating) {
            animating = true;
            if (menuOpen) {
                final float animatedValue = -menu.getHeight() / 3F;
                ObjectAnimator animSeachbar = ObjectAnimator.ofFloat(menu, "TranslationY", animatedValue);
                animSeachbar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        menu.setAlpha(1f - ((float) animation.getAnimatedValue()) / animatedValue);
                    }
                });
                animSeachbar.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        menuOpen = false;

                        animating = false;
                        menu.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animSeachbar.setDuration(500);
                animSeachbar.start();
            } else {

                menu.setVisibility(View.VISIBLE);
                menu.setAlpha(0);
                final float animatedValue = menu.getHeight() / 3F;
                ObjectAnimator animSeachbar = ObjectAnimator.ofFloat(menu, "TranslationY", 0);
                animSeachbar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float a = (1 + (1 + (float) animation.getAnimatedValue()) / animatedValue);
                        if (a > 0f && a < 1f) {
                            menu.setAlpha(a);
                        }
                    }
                });
                animSeachbar.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        menuOpen = true;
                        animating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animSeachbar.setDuration(500);
                animSeachbar.start();
            }
        }
    }

    /**
     * 计算距离
     */
    private String canculat(LatLng endLatlng) {
        if (mMyLocationPoint == null) {
            return "没有获取到您的位置！";
        }
        LatLng startLatlng = new LatLng(mMyLocationPoint.getLatitude(), mMyLocationPoint.getLongitude());
        float distance = AMapUtils.calculateLineDistance(startLatlng, endLatlng);
        if (distance < 1000f) {
            return distance + "m";
        } else {
            BigDecimal b = new BigDecimal(((double) distance) / 1000);
            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            return f1 + "km";
        }
    }

    class Mmark<T extends MerchantBaseEntity> {
        Marker marker;
        Marker fdmarker;
        T merchantEntity;
        SimpleTarget<Drawable> simpleTarget;
        boolean remove = false;
        int type;

        public Mmark(T merchantEntity, int type) {
            this.merchantEntity = merchantEntity;
            this.type = type;
            add();
        }

        public T getMerchantEntity() {
            return merchantEntity;
        }

        public String getId() {
            return merchantEntity.getId();
        }

        public void add() {
            simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    if (!remove) {
                        MarkerOptions markerOption = new MarkerOptions();
                        markerOption.position(new LatLng(merchantEntity.getLatLonPoint().getLatitude(), merchantEntity.getLatLonPoint().getLongitude()));
                        markerOption.draggable(false);//设置Marker可拖动
                        View view = null;
                        if (Mmark.this.type == type_friend) {
                            view = inflater.inflate(R.layout.mark_firend, null,
                                    false);
                            ImageView image = (ImageView) view.findViewById(R.id.image);
                            image.setImageDrawable(resource);

                        } else if (Mmark.this.type == type_order_scene) {
                            view = inflater.inflate(R.layout.mark_order_scene, null,
                                    false);
                            ImageView image = (ImageView) view.findViewById(R.id.image);
                            image.setImageDrawable(resource);
                        } else if (Mmark.this.type == type_scene) {

                            view = inflater.inflate(R.layout.mark_scene, null,
                                    false);
                            ImageView image = (ImageView) view.findViewById(R.id.image);
                            image.setImageDrawable(resource);

                        }
                        if (Mmark.this.type != type_friend) {
                            View view2 = inflater.inflate(R.layout.mark_fd, null,
                                    false);
                            ImageView image = (ImageView) view2.findViewById(R.id.image);
                            image.setImageDrawable(resource);
                            MarkerOptions markerOption2 = new MarkerOptions();
                            markerOption2.position(new LatLng(merchantEntity.getLatLonPoint().getLatitude(), merchantEntity.getLatLonPoint().getLongitude()));
                            markerOption2.draggable(false);//设置Marker可拖动
                            markerOption2.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view2)));
                            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                            markerOption2.setFlat(true);//设置marker平贴地图效果
                            fdmarker = mAmap.addMarker(markerOption2);
                            fdmarker.setVisible(false);
                        }
                        markerOption.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
                        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                        markerOption.setFlat(true);//设置marker平贴地图效果
                        marker = mAmap.addMarker(markerOption);
                        marker.setObject(merchantEntity);
                    }
                }
            };
            GlideApp.with(MapsFragment.this)
                    .load(Urls.address + merchantEntity.getImage())
                    .circleCrop()
                    .placeholder(R.drawable.icon17)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object
                                model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object
                                model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(simpleTarget);

        }

        public void hiddenFd() {
            T S = merchantEntity;
            if (fdmarker != null) {
                fdmarker.setVisible(false);
//                fdmarker.setZIndex(fdmarker.getZIndex() - 3);
                marker.setVisible(true);
            }
        }

        public void showFd() {
            T S = merchantEntity;
            if (fdmarker != null) {
                marker.setVisible(false);
                fdmarker.setZIndex(fdmarker.getZIndex() + 3);
                fdmarker.setToTop();
                fdmarker.setVisible(true);

            }
        }

        public void remove() {
            remove = true;
            if (marker != null) {
                marker.destroy();

            }
            if (fdmarker != null) {
                fdmarker.destroy();
            }
            simpleTarget.onStop();
        }
    }
}