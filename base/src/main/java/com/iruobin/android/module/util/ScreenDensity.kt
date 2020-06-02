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
import android.util.DisplayMetrics
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
    private var displayMetrics: DisplayMetrics? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
        displayMetrics = appContext?.resources?.displayMetrics
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        val scale = displayMetrics?.density ?: return 0
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale = displayMetrics?.density ?: return 0
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    @JvmStatic
    fun sp2px(spValue: Float): Int {
        val fontScale = displayMetrics?.scaledDensity ?: return 0
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    @JvmStatic
    fun px2sp(pxValue: Float): Int {
        val fontScale = displayMetrics?.scaledDensity ?: return 0
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getScreenWidth(): Int {
        return displayMetrics?.widthPixels ?: return 0
    }

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getScreenHeight(): Int {
        return displayMetrics?.heightPixels ?: return 0
    }
}