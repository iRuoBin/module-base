package com.iruobin.android.module.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 设备方向工具类
 *
 * 手机非 方向锁定 下，在需要监听的 Activity 的 Manifest 文件中添加
 * android:configChanges="orientation|screenSize"
 * 否则旋转设备会销毁重建 Activity，重走完整的生命周期函数
 *
 * @author iRuoBin
 * @since 0.1
 */
object DeviceOrientation {

    const val action = Intent.ACTION_CONFIGURATION_CHANGED

    class Observer (val context: Context, callback: ConfigChangedCallback) : LifecycleObserver {
        private val receiver = ConfigChangedReceiver(callback)

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

    interface ConfigChangedCallback {
        fun onChanged(intent: Intent?)
    }

    internal class ConfigChangedReceiver(private val callback: ConfigChangedCallback) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == action) {
                callback.onChanged(intent)
            }
        }
    }

    /**
     * 当前是否是竖屏
     */
    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

}