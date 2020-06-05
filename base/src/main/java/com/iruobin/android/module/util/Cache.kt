package com.iruobin.android.module.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * 缓存工具类
 * @author iRuoBin
 * @since 0.1
 */
object Cache {

    @Throws(Exception::class)
    @JvmStatic
    fun getTotalCacheSize(context: Context): String? {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == "mounted" && context.externalCacheDir != null) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    @JvmStatic
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == "mounted" && context.externalCacheDir != null) {
            deleteDir(context.externalCacheDir)
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }
        return dir!!.delete()
    }

    @Throws(Exception::class)
    @JvmStatic
    fun getFolderSize(file: File?): Long {
        var size = 0L
        try {
            val fileList = file!!.listFiles()
            for (i in fileList.indices) {
                size += if (fileList[i].isDirectory) {
                    getFolderSize(fileList[i])
                } else {
                    fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    @JvmStatic
    fun getFormatSize(size: Double): String? {
        val kiloByte = size / 1024.0
        return if (kiloByte < 1.0) {
            "0"
        } else {
            val megaByte = kiloByte / 1024.0
            if (megaByte < 1.0) {
                val result1 = BigDecimal(kiloByte.toString())
                result1.setScale(2, 4).toPlainString() + "KB"
            } else {
                val gigaByte = megaByte / 1024.0
                if (gigaByte < 1.0) {
                    val result2 = BigDecimal(megaByte.toString())
                    result2.setScale(2, 4).toPlainString() + "MB"
                } else {
                    val teraBytes = gigaByte / 1024.0
                    val result4: BigDecimal
                    if (teraBytes < 1.0) {
                        result4 = BigDecimal(gigaByte.toString())
                        result4.setScale(2, 4).toPlainString() + "GB"
                    } else {
                        result4 = BigDecimal(teraBytes)
                        result4.setScale(2, 4).toPlainString() + "TB"
                    }
                }
            }
        }
    }
}