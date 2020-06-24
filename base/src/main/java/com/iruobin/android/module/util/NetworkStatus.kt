package com.iruobin.android.module.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 网络状态工具类
 * @author iRuoBin
 * @since 0.1
 */
object NetworkStatus {
    const val action = ConnectivityManager.CONNECTIVITY_ACTION

    class Observer (val context: Context, callback: NetworkChangedCallback) : LifecycleObserver {
        private val receiver = NetworkChangedReceiver(callback)

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        private fun onStart() {
            val intentFilter = IntentFilter()
            intentFilter.addAction(action)
            context.registerReceiver(receiver, intentFilter)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        private fun onStop() {
            context.unregisterReceiver(receiver)
        }
    }

    interface NetworkChangedCallback {
        fun onChanged(context: Context?, networkInfo: NetworkInfo?)
    }

    internal class NetworkChangedReceiver(private val callback: NetworkChangedCallback) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.getActiveNetworkInfo()
            //如果无网络连接activeInfo为null
            //也可获取网络的类型
            if (networkInfo != null) {
                // 网络连接
                PrintLog.i("${if (isWifi(networkInfo)) "wifi" else "4g"} is connected")
            } else {
                // 网络断开
                PrintLog.i("network is disconnected")
            }
            if (intent?.action == action) {
                callback.onChanged(context, networkInfo)
            }
        }
    }

    /**
     * 是否有网络连接
     */
    fun isConnected(networkInfo: NetworkInfo?): Boolean {
        return networkInfo != null
    }

    /**
     * 网络类型是否是 Wifi
     */
    fun isWifi(networkInfo: NetworkInfo?): Boolean {
        if (!isConnected(networkInfo)) return false
        return when (networkInfo!!.type) {
            // 移动网络
            ConnectivityManager.TYPE_MOBILE -> false
            // Wifi
            ConnectivityManager.TYPE_WIFI -> true
            else -> false
        }
    }

    /**
     * 网络是否可用（针对有些网络虽已连接但是无法访问互联网的情况）
     */
    fun networkIsValidated(context: Context?): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.getNetworkCapabilities(manager.activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
        } else {
            return true
        }
    }
}