package com.iruobin.android.module.util

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils


/**
 * 包信息工具类
 * @author iRuoBin
 * @since 0.1
 */
object PackageInfos {

    private var appContext: Context? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
    }

    /**
     * app 是否存在
     */
    @JvmStatic
    fun checkApkExist(packageName: String): Boolean {
        return if (TextUtils.isEmpty(packageName)) false else
            try {
                appContext?.packageManager?.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
    }
}