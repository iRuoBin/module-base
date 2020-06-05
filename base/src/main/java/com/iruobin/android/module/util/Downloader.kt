package com.iruobin.android.module.util

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import java.io.File

/**
 * 下载工具类
 * 需要写入权限 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * @author iRuoBin
 * @since 0.1
 */
object Downloader {

    var appContext: Context? = null
    var downloadManager: DownloadManager? = null

    fun init(context: Context?) {
        appContext = context?.applicationContext
        downloadManager = appContext?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    fun download(downloadUrl: String, listener: DownloadCompleteListener) {
        //创建下载任务,downloadUrl就是下载链接
        val request = DownloadManager.Request(Uri.parse(downloadUrl))
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/download/", downloadUrl.substringAfterLast("/"))
        //将下载任务加入下载队列，否则不会进行下载
        val taskId = downloadManager?.enqueue(request) ?: 0
        //注册广播接收者，监听下载状态
        appContext?.registerReceiver(CompleteReceiver(taskId, listener), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    //广播接受者，接收下载状态
    internal class CompleteReceiver(
        private val taskId: Long,
        private val listener: DownloadCompleteListener
    ) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //检查下载状态
            checkDownloadStatus(this, taskId, listener)
        }
    }

    interface DownloadCompleteListener {
        fun downloadCompleted(localFilePath: String)
    }

    //检查下载状态 经测试 红米只能监听到下载完成事件
    private fun checkDownloadStatus(completeReceiver: CompleteReceiver, taskId: Long, listener: DownloadCompleteListener) {
        val query = DownloadManager.Query()
        //筛选下载任务，传入任务ID，可变参数
        query.setFilterById(taskId)
        val cursor = downloadManager!!.query(query)
        if (cursor.moveToFirst()) {
            when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                DownloadManager.STATUS_PAUSED -> PrintLog.i(">>>下载暂停")
                DownloadManager.STATUS_PENDING -> PrintLog.i(">>>下载延迟")
                DownloadManager.STATUS_RUNNING -> PrintLog.i(">>>正在下载")
                DownloadManager.STATUS_SUCCESSFUL -> {
                    PrintLog.i(">>>下载完成")
                    listener.downloadCompleted(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                    appContext?.unregisterReceiver(completeReceiver)
                }
                DownloadManager.STATUS_FAILED -> PrintLog.i(">>>下载失败")
            }
        }
    }

    //下载Apk后执行安装
    fun installAPK(file: File) {
        if (!file.exists()) return
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse("file://" + file.toString())
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        //在服务中开启activity必须设置flag,后面解释
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        appContext?.startActivity(intent)
    }
}