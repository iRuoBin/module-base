package com.iruobin.android.module.util

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener


/**
 * 定位工具类
 * @author iRuoBin
 * @since 0.1
 */
object Location {

    var appContext: Context? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
    }

    /**
     * 获取当前位置 by 高德定位
     */
    @JvmStatic
    fun getAMapLocation(listener: AMapLocationListener) {
        //声明AMapLocationClient类对象
        var locationClient = AMapLocationClient(appContext)
        //声明定位回调监听器
        val locationListener = AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    PrintLog.d("amapLocation:${amapLocation}")
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    PrintLog.e("amapLocation Error, ErrCode:${amapLocation.errorCode}, errInfo:${amapLocation.errorInfo}")
                }
            }
            listener.onLocationChanged(amapLocation)
            //停止定位后，本地定位服务并不会被销毁
            locationClient.stopLocation()
            //销毁定位客户端，同时销毁本地定位服务。
            //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
            locationClient.onDestroy()
        }
        //设置定位回调监听
        locationClient.setLocationListener(locationListener)
        //启动定位
        locationClient.startLocation()
    }
}