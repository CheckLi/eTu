package com.yitu.etu.ui.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.RealTimeBean;
import com.yitu.etu.entity.RealTimeListBean;
import com.yitu.etu.eventBusItem.EventOpenRealTime;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.HashMap;

import okhttp3.Call;

/**
 * @className:RealTimeMapFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月04日 22:39
 */
public class RealTimeMapFragment extends SupportMapFragment implements AMapLocationListener, LocationSource {
    private int statusBarHeight=0;
    private View mRoot;
    private MapView mMapView;
    private TextView mCount;
    private View parent;
    private View bottomView;
    private AMap mAmap;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private SparseArray<RealTimeListBean> mMarkers;
    private ImageView left;
    private ImageView right;

    private String id = "";
    private LatLng mLatLng;
    private LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
    MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            id = getArguments().getString("chat_id");
        }
        mMarkers = new SparseArray<>();
        mRoot = inflater.inflate(R.layout.real_time_map, container,
                false);
        mMapView = (MapView) mRoot.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();

        mAmap.getUiSettings().setMyLocationButtonEnabled(false);
        mAmap.getUiSettings().setLogoPosition(
                AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// logo位置
        mAmap.getUiSettings().setScaleControlsEnabled(false);// 标尺开关
        mAmap.getUiSettings().setCompassEnabled(true);// 指南针开关
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        initMyLocation();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCount = (TextView) view.findViewById(R.id.tv_count);
        left = (ImageView) view.findViewById(R.id.iv_left_chang);
        right = (ImageView) view.findViewById(R.id.iv_right_chang);
        parent = view.findViewById(R.id.fl_real_time_parent);
        bottomView = view.findViewById(R.id.rl_real_time_content);
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        /**
         * 左侧改变大小按钮
         */
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation(true);
            }
        });

        /**
         * 右侧改变大小按钮
         */
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation(false);
            }
        });
        statusBarHeight=getStatusBarHeight();
        int screenHeight = getResources().getDisplayMetrics().heightPixels - getResources().getDimensionPixelSize(R.dimen.actionBarSize)-statusBarHeight;
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        params.height = (int) (screenHeight*0.5f);
        parent.setLayoutParams(params);
    }

    /**
     * 变化布局大小
     * @param left
     */
    public void animation(boolean left) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels - getResources().getDimensionPixelSize(R.dimen.actionBarSize)-statusBarHeight;
        int bottomHeight = bottomView.getHeight();
        int height = parent.getHeight();
        int toHeight = 0;
        int cha=(int)(10*getResources().getDisplayMetrics().density);
        if (left) {
            if (Math.abs(height-bottomHeight) <=cha  || Math.abs(height-screenHeight) <=cha) {
                toHeight = screenHeight >> 1;
                this.left.setImageResource(R.drawable.icon_up);
                this.right.setImageResource(R.drawable.icon_down);
            } else if (Math.abs(height-screenHeight*0.5) <=cha) {
                this.left.setImageResource(R.drawable.icon_down);
                this.right.setImageResource(R.drawable.icon_down);
                toHeight = bottomHeight;
            }
        } else {
            if (Math.abs(height-bottomHeight) <=cha|| Math.abs(height-screenHeight) <=cha) {
                toHeight = screenHeight >> 1;
                this.right.setImageResource(R.drawable.icon_down);
                this.left.setImageResource(R.drawable.icon_up);
            } else if (Math.abs(height-screenHeight*0.5) <=cha) {
                toHeight = screenHeight;
                this.left.setImageResource(R.drawable.icon_up);
                this.right.setImageResource(R.drawable.icon_up);
            }
        }
        if (toHeight > 0) {
            ValueAnimator anim = ObjectAnimator.ofInt(height, toHeight).setDuration(300);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    ViewGroup.LayoutParams params = parent.getLayoutParams();
                    params.height = value;
                    parent.setLayoutParams(params);
                }
            });

            anim.start();
        }
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int dimensionPixelSize = getResources().getDimensionPixelSize(x);
            return dimensionPixelSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (mLatLng == null) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("lat", mLatLng.latitude + "");
        params.put("lng", mLatLng.longitude + "");
        params.put("chat_id", id);
        Http.post(Urls.URL_GET_GETLOCATION, params, new GsonCallback<ObjectBaseEntity<RealTimeBean>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ObjectBaseEntity<RealTimeBean> response, int id) {
                if (response.success() && response.getData() != null) {
//                    mMarkers = response.getData().data;
                    if (response.getData().data != null && response.getData().data.size() > 0) {
                        /**
                         * 判断marker，如果有就移动，没有就添加
                         */
                        for (int i = 0; i < response.getData().data.size(); i++) {
                            RealTimeListBean bean = response.getData().data.get(i);
                            if (mMarkers.indexOfKey(bean.id) < 0) {
                                mMarkers.append(bean.id, bean);
                                addMarker(bean.lat, bean.lng, bean.id, bean.header);
                            } else if (mMarkers.get(bean.id).mMarker != null && (bean.lat != mMarkers.get(bean.id).lat || bean.lng != mMarkers.get(bean.id).lng)) {
                                mMarkers.get(bean.id).mMarker.setPosition(new LatLng(bean.lat, bean.lng));
                            } else if (mMarkers.get(bean.id).mMarker == null) {
                                RealTimeListBean bean2 = mMarkers.get(bean.id);
                                addMarker(bean2.lat, bean2.lng, bean2.id, bean2.header);
                            }
                        }
                        /**
                         * 去除列表中已经离开的marker
                         */
                        for (int i = 0; i < mMarkers.size(); i++) {
                            int key = mMarkers.keyAt(i);
                            int filterKey = -1;
                            for (int j = 0; j < response.getData().data.size(); j++) {
                                RealTimeListBean bean2 = response.getData().data.get(j);
                                if (key == bean2.id) {
                                    filterKey = -1;
                                    break;
                                } else {
                                    filterKey = bean2.id;
                                }
                            }
                            if (filterKey >= 0) {
                                remove(key);
                            }
                        }
                    } else {
                        for (int i = 0; i < mMarkers.size(); i++) {
                            int key = mMarkers.keyAt(i);
                            if (mMarkers.get(key).id != EtuApplication.getInstance().getUserInfo().getId()) {
                                remove(key);
                            } else if (mMarkers.get(key).mMarker == null && mMarkers.get(key).id == EtuApplication.getInstance().getUserInfo().getId()) {
                                RealTimeListBean bean2 = mMarkers.get(key);
                                addMarker(bean2.lat, bean2.lng, bean2.id, bean2.header);
                            }
                        }
                    }
                } else {
                    ToastUtil.showMessage(response.getMessage());
                    EventBus.getDefault().post(new EventOpenRealTime(null,false));
                }
                mCount.setText(mMarkers.size() + "人正在共享位置");
            }
        });
    }

    private void remove(int key) {
        if (mMarkers.size() > 0 && mMarkers.get(key).id != EtuApplication.getInstance().getUserInfo().getId()) {
            if (mMarkers.get(key).mMarker != null) {
                mMarkers.get(key).mMarker.remove();
            }
            mMarkers.remove(key);
        }
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
        mLocationOption.setInterval(5000);
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
        myLocationStyle.interval(5000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
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
        mAmap.clear();
        mMarkers.clear();
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
        if (mLocationClient != null) {

            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        if (mLocationClient == null) {
            initLocation();
        }
    }

    private boolean isCreate = true;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0 && isCreate) {
                isCreate = false;
                /**
                 * 添加本人位置
                 */
                mLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mAmap.moveCamera(CameraUpdateFactory.zoomTo(16));
                mAmap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                RealTimeListBean bean = new RealTimeListBean();
                bean.header = Urls.address + EtuApplication.getInstance().getUserInfo().getHeader();
                bean.id = EtuApplication.getInstance().getUserInfo().getId();
                bean.lat = mLatLng.latitude;
                bean.lng = mLatLng.longitude;
                mMarkers.append(bean.id, bean);
                addMarker(aMapLocation.getLatitude(), aMapLocation.getLongitude(), bean.id, bean.header);
//                refresh();
            }
            refresh();
        }
    }

    private void addMarker(double lat, double lng, final int userId, String url) {
        final MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(lat, lng));
        markerOption.draggable(true);//设置Marker可拖动
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(false);//设置marker平贴地图效果

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.mark_real_time, null,
                        false);
                if(mMarkers.indexOfKey(userId)>=0&&mMarkers.get(userId)!=null){
                    ImageView image = (ImageView) view.findViewById(R.id.image);
                    image.setImageBitmap(bitmap);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
                    mMarkers.get(userId).mMarker = mAmap.addMarker(markerOption);
                    for (int i = 0; i < mMarkers.size(); i++) {
                        int key = mMarkers.keyAt(i);
                        if (mMarkers.get(key) != null && mMarkers.get(key).mMarker != null) {
                            boundsBuilder.include(mMarkers.get(key).mMarker.getPosition());//把所有点都include进去（LatLng类型）
                        }
                    }
                    mAmap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 15));//第二个参数为四周留空宽度
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        if(url.startsWith(Urls.address+"http")){
            url=url.replace(Urls.address,"");
        }
        Picasso.with(getActivity())
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .resize((int) (50 * getResources().getDisplayMetrics().density), (int) (50 * getResources().getDisplayMetrics().density))
                .into(target);
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


}
