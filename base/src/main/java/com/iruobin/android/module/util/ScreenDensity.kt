package com.iruobin.android.module.util

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat.startActivityForResult
import com.iruobin.android.permission.Permission
import com.iruobin.android.permission.PermissionCallback
import com.iruobin.android.permission.PermissionHandleCallback


/**
 * 屏幕密度转换类
 * @author iRuoBin
 * @since 0.1
 */
object ScreenDensity {

    private var appContext: Context? = null

    fun init(context: Context?) {
        appContext = context?.applicationContext
    }

    /**
     * 获取屏幕亮度（亮度值在（0~255）之间）
     */
    fun getScreenBrightness(): Int {
        if (appContext == null) return -1
        val contentResolver: ContentResolver = appContext!!.contentResolver
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    }

    /**
     * 设置 APP 界面屏幕亮度值（亮度值在（0~255）之间 不影响其他 APP）
     */
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
    fun setKeepScreenOn(activity: Activity, keepOn: Boolean) {
        if (keepOn)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * 获取亮度模式（true 为自动模式，false 为手动模式）
     */
    fun isAutoBrightnessMode(): Boolean {
        return when(Settings.System.getInt(appContext!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC -> true
            else -> false
        }
    }

    /**
     * 设置亮度模式（分为自动亮度和手动亮度）
     * 需以下权限
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     *
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC 自动调节屏幕亮度模式 值为1
     * SCREEN_BRIGHTNESS_MODE_MANUAL 手动调节屏幕亮度模式 值为0
     */
    fun setBrightnessMode(autoMode: Boolean) {
        Permission.with(appContext).permission(Manifest.permission.WRITE_SETTINGS)
            .callback(object : PermissionHandleCallback(appContext){
                override fun onPermissionsCompleteGranted() {
                    try {
                        Settings.System.putInt(appContext!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                            if (autoMode) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                            else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                    } catch (e: SettingNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }).request()
    }

    /**
     * 设置系统屏幕亮度（需关闭光感，设置手动调节背光模式和以下权限）
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     */
    fun aff() {
//        if (appContext == null) return
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //大于等于23 请求权限
//            if (!Settings.System.canWrite(appContext)) {
//                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
//                intent.data = Uri.parse("package:" + appContext!!.packageName)
//                activity.startActivityForResult(intent, 0)
//            }
//        }
    }

//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    @JvmOverloads
//    fun dp2px(dpValue: Float, context: Context = App.appContext): Int {
//        val scale = context.resources.displayMetrics.density
//        return (dpValue * scale + 0.5f).toInt()
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     */
//    @JvmOverloads
//    fun px2dp(pxValue: Float, context: Context = App.appContext): Int {
//        val scale = context.resources.displayMetrics.density
//        return (pxValue / scale + 0.5f).toInt()
//    }
//
//    /**
//     * 将sp值转换为px值，保证文字大小不变
//     *
//     * @param spValue （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    @JvmOverloads
//    fun sp2px(spValue: Float, context: Context = App.appContext): Int {
//        val fontScale = context.resources.displayMetrics.scaledDensity
//        return (spValue * fontScale + 0.5f).toInt()
//    }
//
//    /**
//     * 将px值转换为sp值，保证文字大小不变
//     *
//     * @param pxValue （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    @JvmOverloads
//    fun px2sp(pxValue: Float, context: Context = App.appContext): Int {
//        val fontScale = context.resources.displayMetrics.scaledDensity
//        return (pxValue / fontScale + 0.5f).toInt()
//    }
//
//    /**
//     * 获取屏幕宽度
//     */
//    @JvmOverloads
//    fun getScreenWidth(context: Context = App.appContext): Int {
//        return context.resources.displayMetrics.widthPixels
//    }
//
//    /**
//     * 获取屏幕宽度
//     */
//    @JvmOverloads
//    fun getScreenHeight(context: Context = App.appContext): Int {
//        return context.resources.displayMetrics.heightPixels
//    }
}