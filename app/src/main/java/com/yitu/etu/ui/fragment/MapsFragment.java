package com.yitu.etu.ui.fragment;

import android.Manifest;
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
import android.widget.ImageView;

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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yitu.etu.R;
import com.yitu.etu.entity.MapFirendEntity;
import com.yitu.etu.entity.MapOrderSceneEntity;
import com.yitu.etu.entity.MapSceneEntity;
import com.yitu.etu.entity.MerchantBaseEntity;
import com.yitu.etu.ui.activity.GPSNaviActivity;
import com.yitu.etu.util.PermissionUtil;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.widget.GlideApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public static final int type_fd = 4;
    View btn_type_friend;
    int type = type_scene;
    LatLng centerLatLng;
    private Mmark fd_mark;
    private View dialog;
    private ViewGroup menu;
    boolean menuOpen = false;
    private boolean animating;
    private ImageView mButton;

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
        mButton = (ImageView) mRoot.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mRoot.findViewById(R.id.btn_location).setOnClickListener(this);
        mRoot.findViewById(R.id.btn_nai).setOnClickListener(this);


        menu = (ViewGroup) mRoot.findViewById(R.id.menu);

        dialog = mRoot.findViewById(R.id.dialog);

        mMapView.onCreate(savedInstanceState);
        image = (ImageView) mRoot.findViewById(R.id.image);
        if (!PermissionUtil.hasPermissions(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PermissionUtil.requestPermissions(this, 100, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        initMap();
        return mRoot;
    }

    public void showDialog() {
        dialog.setVisibility(View.VISIBLE);
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
                    }
                    hiddenDialog();

                    centerLatLng = mAmap.getCameraPosition().target;
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
                    }
                    MerchantBaseEntity merchantEntity = (MerchantBaseEntity) marker.getObject();
                    if (merchantEntity.getType() == type_friend) {
                        marker.showInfoWindow();
                    } else {
                        for (Mmark mmark :
                                markers) {
                            if (mmark.getId() == merchantEntity.getId()) {
                                fd_mark = mmark;
                                mmark.showFd();
                                if (dialog.getVisibility() != View.VISIBLE) {
                                    showDialog();
                                }
                            }
                        }

                    }
                    Log.e("点击", merchantEntity.getId() + "");
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
            loadInfo();
        }
    }


    public void clearMapMarker() {
        mAmap.clear();
        markers.clear();
    }

    public void loadInfo() {
        hiddenDialog();
        if (type == type_friend) {
            ArrayList<MapFirendEntity> aa = new ArrayList<>();
//            for (int i = a; i < a + 4; i++) {
            MapFirendEntity MerchantEntity = new MapFirendEntity();
            MerchantEntity.setId(1);
            MerchantEntity.lng = 116.397972;
            aa.add(MerchantEntity);
//            }
            addMarker(aa, type);

        } else if (type == type_scene) {
            ArrayList<MapSceneEntity> aa = new ArrayList<>();
            MapSceneEntity MerchantEntity = new MapSceneEntity();
            MerchantEntity.setId(1);
            MerchantEntity.lng = 116.397972;
            aa.add(MerchantEntity);

            MapSceneEntity MerchantEntity2 = new MapSceneEntity();
            MerchantEntity2.setId(2);
            MerchantEntity2.lng = 117.397972;
            aa.add(MerchantEntity2);

            MapSceneEntity MerchantEntity3 = new MapSceneEntity();
            MerchantEntity3.setId(3);
            MerchantEntity3.lng = 118.397972;
            aa.add(MerchantEntity3);
            addMarker(aa, type);

        } else if (type == type_order_scene) {
            ArrayList<MapOrderSceneEntity> aa = new ArrayList<>();
            for (int i = a; i < a + 4; i++) {
                MapOrderSceneEntity MerchantEntity = new MapOrderSceneEntity();
                MerchantEntity.setId(i);
                MerchantEntity.lng = 116.397972 + a;
                aa.add(MerchantEntity);
            }
            addMarker(aa, type);

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
                if (mmark.getId() == merchantEntity2.getId()) {
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
        for (Mmark mmark : markers) {
            Log.e("id", "" + mmark.getId());
        }
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
        Log.d(TAG, "onLocationChanged");
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
//                        mAmap.moveCamera(CameraUpdateFactory.zoomTo(13));
//                        mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMyLocationPoint.getLatitude(), mMyLocationPoint.getLongitude())));
                    }
                }
                mMyLocationPoint = amapLocation;
//                mLocationChangeListener.onLocationChanged(mMyLocationPoint);
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
        View view = inflater.inflate(R.layout.pop_map_firend, null,
                false);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("我2");
            }
        });
        GlideApp.with(MapsFragment.this)
                .load("http://img2.imgtn.bdimg.com/it/u=1025471167,1921781839&fm=27&gp=0.jpg")
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

    public void toMyLocation() {

        if (mMyLocationPoint != null) {
            mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(mMyLocationPoint.getLatitude(), mMyLocationPoint.getLongitude())));
            mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_friend:
                if (type != type_friend) {
                    type = type_friend;
                    btn_type_friend.setVisibility(View.VISIBLE);
                    clearMapMarker();
                    loadInfo();
                }
                break;
            case R.id.btn_order_scene:
                if (type != type_order_scene) {
                    type = type_order_scene;
                    btn_type_friend.setVisibility(View.GONE);
                    clearMapMarker();
                    loadInfo();
                }
                break;
            case R.id.btn_scene:
                if (type != type_scene) {
                    type = type_scene;
                    btn_type_friend.setVisibility(View.GONE);
                    clearMapMarker();
                    loadInfo();
                }
                break;

            case R.id.button:
                playMenuAnimation();
                break;
            case R.id.btn_location:
                toMyLocation();
                break;

            case R.id.btn_nai:
                startActivity(new Intent(getContext(), GPSNaviActivity.class));
                break;
            case R.id.btn_type_friend:

                break;
            case R.id.btn_province:

                break;


            default:
                break;
        }
    }

    private void playMenuAnimation() {
        if (!animating) {
            animating = true;
            if (menuOpen) {
          /*  final float animatedValue = -menu.getHeight() / 3F;
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

                    animating=false;
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
            animSeachbar.start();*/
                menu.setVisibility(View.GONE);
                menuOpen=false;
                animating=false;
            } else {
                menu.setVisibility(View.VISIBLE);
                menuOpen=true;
                animating=false;
                /*//第一阶段动画
                ObjectAnimator anim1 = null;
                float start = 0;
                for (int i = 0; i < menu.getChildCount(); i++) {
                    View view = menu.getChildAt(i);
                    if (anim1 == null) {
                        anim1 = ObjectAnimator.ofFloat(view, "TranslationY", start, view.getTop() + view.getHeight()).setDuration(200);
                        start += view.getTop() + view.getHeight();
                    }
                    anim1.start();
                }
                //第二阶段动画
                ObjectAnimator anim2 = null;
                for (int i = 1; i < menu.getChildCount(); i++) {
                    View view = menu.getChildAt(i);
                    if (anim2 == null) {
                        anim2 = ObjectAnimator.ofFloat(view, "TranslationY", start, view.getTop() + view.getHeight()).setDuration(200);
                        start += view.getTop() + view.getHeight();
                        anim2.setStartDelay(200);
                    }
                    anim2.start();
                }
                //第三阶段动画
                ObjectAnimator anim3 = null;
                for (int i = 2; i < menu.getChildCount(); i++) {
                    View view = menu.getChildAt(i);
                    if (anim3 == null) {
                        anim3 = ObjectAnimator.ofFloat(view, "TranslationY", start, view.getTop() + view.getHeight()).setDuration(200);
                        start += view.getTop() + view.getHeight();
                        anim3.setStartDelay(400);
                    }
                    anim3.start();
                }*/
            /*menu.setVisibility(View.VISIBLE);
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
                    animating=false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animSeachbar.setDuration(500);
//                    ObjectAnimator alpha = ObjectAnimator.ofFloat(menu, "alpha", 1);
//                    alpha.setDuration(1500);
//                    AnimatorSet animatorSet = new AnimatorSet();
//                    //将所有动画加入到动画序列里面
//                    animatorSet.playSequentially(animSeachbar, alpha);

            animSeachbar.start();*/
            }
        }
    }

    //    /**
//     * 计算距离
//     */
//    private int canculat() {
//        LatLng startLatlng = new LatLng(curLatitude, curLongitude);
//        LatLng endLatlng = new LatLng(curDataBean.getLat(), curDataBean.getLng());
//
//        BigDecimal b = new BigDecimal(((double) distance) / 1000);
//        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//        this.distance.setText(f1 + "km");
//        return (int) AMapUtils.calculateLineDistance(startLatlng, endLatlng);
//    }
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

        public int getId() {
            return merchantEntity.getId();
        }

        public void add() {
            simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    if (!remove) {
                        MarkerOptions markerOption = new MarkerOptions();
                        markerOption.position(new LatLng(39.906901, merchantEntity.lng));
                        markerOption.draggable(false);//设置Marker可拖动
                        View view = null;
                        if (Mmark.this.type == type_friend) {
                            view = inflater.inflate(R.layout.mark_firend, null,
                                    false);
                            ImageView image = (ImageView) view.findViewById(R.id.image);
                            image.setImageDrawable(resource);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showMessage("我");
                                }
                            });

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
                            markerOption2.position(new LatLng(39.906901, merchantEntity.lng));
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
                    .load("http://pic.58pic.com/58pic/13/66/58/20258PICpDh_1024.png")
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
            if (fdmarker != null) {
                fdmarker.setVisible(false);
//                fdmarker.setZIndex(fdmarker.getZIndex() - 3);
                marker.setVisible(true);
            }
        }

        public void showFd() {
            if (fdmarker != null) {
                marker.setVisible(false);
                fdmarker.setToTop();
                fdmarker.setZIndex(fdmarker.getZIndex() + 3);
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