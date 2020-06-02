package com.iruobin.android.module.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.view.Window
import android.view.WindowManager

/**
 * 屏幕亮度工具类
 * @author iRuoBin
 * @since 0.1
 */
object ScreenBrightness {
    private var appContext: Context? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
    }

    /**
     * 获取屏幕亮度（亮度值在（0~255）之间）
     */
    @JvmStatic
    fun getScreenBrightness(): Int {
        if (appContext == null) return -1
        val contentResolver: ContentResolver = appContext!!.contentResolver
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    }

    /**
     * 设置 APP 界面屏幕亮度值（亮度值在（0~255）之间 不影响其他 APP）
     */
    @JvmStatic
    fun setAppScreenBrightness(activity: Activity, brightnessValue: Int) {
        val window: Window = activity.window
        val lp: WindowManager.LayoutParams = window.attributes
        val brightness = brightnessValue / 255.0f
        lp.screenBrightness = when {
            brightness > 1 -> 1f
            brightness < 0 -> 0f
            else -> brightness
        }
        window.attributes = lp
    }

    /**
     * 设置屏幕常亮
     */
    @JvmStatic
    fun setKeepScreenOn(activity: Activity, keepOn: Boolean) {
        if (keepOn)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * 获取亮度模式（true 为自动模式，false 为手动模式）
     */
    @JvmStatic
    fun isAutoBrightnessMode(): Boolean {
        return when(Settings.System.getInt(appContext!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC -> true
            else -> false
        }
    }

    /**
     * TODO 因权限问题暂未实现
     * 设置亮度模式（分为自动亮度和手动亮度）
     * 需以下权限
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     *
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC 自动调节屏幕亮度模式 值为1
     * SCREEN_BRIGHTNESS_MODE_MANUAL 手动调节屏幕亮度模式 值为0
     */
    fun setBrightnessMode(autoMode: Boolean) {
//        Permission.with(ScreenDensity.appContext).permission(Manifest.permission.WRITE_SETTINGS)
//            .callback(object : PermissionHandleCallback(ScreenDensity.appContext){
//                override fun onPermissionsCompleteGranted() {
//                    try {
//                        Settings.System.putInt(
//                            ScreenDensity.appContext!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
//                            if (autoMode) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
//                            else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
//                    } catch (e: Settings.SettingNotFoundException) {
//                        e.printStackTrace()
//                    }
//                }
//            }).request()
    }

    /**
     * TODO 因权限问题暂未实现
     * 设置系统亮度值（亮度值在（0~255）之间 影响其他 APP）
     */
    fun setSystemBrightness(activity: Activity, brightnessValue: Int) {

    }
}