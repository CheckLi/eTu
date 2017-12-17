package com.yitu.etu.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_map_select.*


class MapSelectActivity : BaseActivity() {
    var aMap: AMap? = null
    var myLocationStyle: MyLocationStyle? = null
    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null
    var mLocation: AMapLocation? = null
    override fun getLayout(): Int = R.layout.activity_map_select

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = map.map
        }
        initLocation()
    }

    override fun initActionBar() {
    }

    override fun initView() {

    }

    override fun getData() {
    }

    override fun initListener() {
    }

    private fun initLocation() {
        myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle?.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)//定位一次，且将视角移动到地图中心点。
        myLocationStyle?.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap?.myLocationStyle = myLocationStyle//设置定位蓝点的Style
//        aMap?.uiSettings?.isMyLocationButtonEnabled = true//设置默认定位按钮是否显示，非必需设置。
        aMap?.isMyLocationEnabled = true// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext)
//设置定位回调监听
        mLocationClient?.setLocationListener { location ->
            mLocation = location
            addMarker()
            aMap?.moveCamera(CameraUpdateFactory.zoomTo(14.0f))
            aMap?.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(location.latitude, location.longitude)))
        }
        aMap?.isMyLocationEnabled = true
        aMap?.uiSettings?.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_LEFT// logo位置
        aMap?.uiSettings?.isScaleControlsEnabled = false// 标尺开关
        aMap?.uiSettings?.isCompassEnabled = false// 指南针开关
        aMap?.uiSettings?.isZoomControlsEnabled = false

        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isNeedAddress = true
        // 设置是否只定位一次,默认为false
        mLocationOption?.isOnceLocation = false
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption?.isWifiActiveScan = true
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption?.isOnceLocation = true;

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption?.isOnceLocationLatest = true;
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption?.isMockEnable = false
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption?.interval = 2000
        // 给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption)
        // 启动定位
        mLocationClient?.startLocation()
    }

    /**
     * 添加marker
     */
    fun addMarker() {
        val latLng = LatLng(mLocation?.latitude ?: 0.0, mLocation?.longitude ?: 0.0)
        val latLng2 = LatLng(mLocation?.latitude ?: 0.0, (mLocation?.longitude ?: 0.0)+0.001111)
        val view2 = layoutInflater.inflate(R.layout.mark_fd, null,
                false)
        val view1 = layoutInflater.inflate(R.layout.mark_fd, null,
                false)

        val marker = aMap?.addMarker(MarkerOptions().
                icons(arrayListOf(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources,R.drawable.icon11)),BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources,R.drawable.icon12))))
                .position(latLng)
                .setFlat(true))


        aMap?.setOnMarkerClickListener { marker ->
            marker.setToTop()
            false
        }
    }

    fun convertViewToBitmap(view: View): Bitmap {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        map?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        map?.onSaveInstanceState(outState)
    }
}
