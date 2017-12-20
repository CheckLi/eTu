package com.yitu.etu.util.location

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.yitu.etu.EtuApplication

/**
 * @className:LocationUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月20日 22:40
 */
class LocationUtil {
    //声明AMapLocationClient类对象
    private var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    private var mLocationOption: AMapLocationClientOption? = null

    companion object {
        @JvmStatic
        fun getInstance(): LocationUtil {
            return LocationUtilInstance.instance
        }
    }

    object LocationUtilInstance {
        val instance=LocationUtil()
    }
    
    init {
        //初始化定位
        mLocationClient = AMapLocationClient(EtuApplication.getInstance())


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
        mLocationOption?.isOnceLocation = true

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption?.isOnceLocationLatest = true
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption?.isMockEnable = false
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption?.interval = 2000
        // 给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption)
    }

    fun startLocation(locationListener: AMapLocationListener) {
        //设置定位回调监听
        mLocationClient?.setLocationListener(locationListener)
        mLocationClient?.stopLocation()
        // 启动定位
        mLocationClient?.startLocation()
    }

    fun startLocation(locationListener: (location:AMapLocation) -> Unit) {
        //设置定位回调监听
        mLocationClient?.setLocationListener {it->
            locationListener(it)
            mLocationClient?.stopLocation()
        }
        mLocationClient?.stopLocation()
        // 启动定位
        mLocationClient?.startLocation()
    }
}
