package com.iruobin.android.module.util

import android.content.Context
import android.os.Vibrator

/**
 * 震动工具类
 *
 * 需要 Manifest 添加震动权限
 * <uses-permission android:name="android.permission.VIBRATE"/>
 *
 * @author iRuoBin
 * @since 0.1
 */
object Vibrator {

    private var appContext: Context? = null
    private var vibrator: Vibrator? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
        vibrator = appContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    /**
     * 单次震动（设置震动时常：单位 ms）
     */
    @JvmStatic
    fun vibrate(milliseconds: Long) {
        vibrator?.vibrate(milliseconds)
    }

    /**
     * 模式震动（单位：ms）
     *
     * 数组的a[0]表示静止的时间，a[1]表示震动的时间，然后a[2]表示静止的时间，a[3]表示震动的时间……依次类推
     * vibrate的第二个参数表示从哪里开始循环，如果是0表示这个数组在第一次震完之后会从下标0开始循环到最后
     * 循环震动需调用 cancel 才可取消
     * 如果是-1则表示不循环
     */
    @JvmStatic
    fun vibrate(pattern: LongArray, repeat: Int) {
        vibrator?.vibrate(pattern, repeat)
    }

    /**
     * 不循环的模式震动
     */
    @JvmStatic
    fun vibrate(vararg pattern: Long) {
        vibrator?.vibrate(pattern, -1)
    }

    /**
     * 取消震动
     */
    @JvmStatic
    fun cancel() {
        vibrator?.cancel()
    }
}