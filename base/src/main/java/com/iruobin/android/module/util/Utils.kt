package com.iruobin.android.module.util

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * 工具类汇总类
 * @author iRuoBin
 * @since 0.1
 */
object Utils {

    const val symbol = "iRuoBin"

    fun init(context: Context?) {
        PrintLog.init(context)
        SharePrefs.init(context)
        Vibrator.init(context)
        ScreenDensity.init(context)
        Clipboard.init(context)
        PackageInfos.init(context)
    }
    /**
     * 打印 Log 工具类（带堆栈信息）
     */
    val PrintLog = com.iruobin.android.module.util.PrintLog
    /**
     * 屏幕密度转换类（可以获取屏幕宽高）
     */
    val ScreenDensity = com.iruobin.android.module.util.ScreenDensity
    /**
     * SharePreferences 操作类（可以存取数据）
     */
    val SharePrefs = com.iruobin.android.module.util.SharePrefs
    /**
     * 震动马达操作类
     */
    val Vibrator = com.iruobin.android.module.util.Vibrator
    /**
     * 电池状态工具类
     */
    val Battery = com.iruobin.android.module.util.Battery
    /**
     * 剪贴板工具类
     */
    val Clipboard = com.iruobin.android.module.util.Clipboard
    /**
     * 设备方向工具类
     */
    val DeviceOrientation = com.iruobin.android.module.util.DeviceOrientation
    /**
     * 包信息工具类
     */
    val PackageInfos = com.iruobin.android.module.util.PackageInfos
}