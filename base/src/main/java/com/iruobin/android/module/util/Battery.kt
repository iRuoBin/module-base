package com.iruobin.android.module.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 电池工具类
 * @author iRuoBin
 * @since 0.1
 */
object Battery {

    const val action = Intent.ACTION_BATTERY_CHANGED

    class Observer (val context: Context, callback: BatteryChangedCallback) : LifecycleObserver {
        private val receiver = BatteryChangedReceiver(callback)

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

    interface BatteryChangedCallback {
        fun onChanged(intent: Intent?)
    }

    internal class BatteryChangedReceiver(private val callback: BatteryChangedCallback) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == action) {
                callback.onChanged(intent)
            }
        }
    }

    /**
     * key 值在 BatteryManager 类
     *
     * 例如:
     * 当前电量 BatteryManager.EXTRA_LEVEL
     * 满电量数值 BatteryManager.EXTRA_SCALE
     * 充电状态 BatteryManager.EXTRA_PLUGGED（0 是未充电）
     * 等等
     */
    @JvmStatic
    fun getIntInfo(key: String, intent: Intent?): Int {
        return intent?.getIntExtra(key, 0) ?: 0
    }

    /**
     * key 值在 BatteryManager 类
     */
    @JvmStatic
    fun getStringInfo(key: String, intent: Intent?): String {
        return intent?.getStringExtra(key) ?: ""
    }

    /**
     * 当前电量
     */
    @JvmStatic
    fun getCurrentQuantity(intent: Intent?): Int {
        return getIntInfo(BatteryManager.EXTRA_LEVEL, intent)
    }

    /**
     * 最大电量值
     */
    @JvmStatic
    fun getMaxQuantity(intent: Intent?): Int {
        return getIntInfo(BatteryManager.EXTRA_SCALE, intent)
    }

    /**
     * 是否充电中
     */
    @JvmStatic
    fun isCharging(intent: Intent?): Boolean {
        return getIntInfo(BatteryManager.EXTRA_PLUGGED, intent) != 0
    }
}