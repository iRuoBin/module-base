package com.iruobin.android.module.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * 剪贴板工具类
 * @author iRuoBin
 * @since 0.1
 */
object Clipboard {

    private var appContext: Context? = null
    private var clipboardManager: ClipboardManager? = null

    fun init(context: Context?) {
        appContext = context?.applicationContext
        clipboardManager = appContext?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun setText(text: String) {
        clipboardManager?.setPrimaryClip(ClipData.newPlainText("", text))
    }

    fun getText(): String {
        return if (clipboardManager?.primaryClip?.itemCount ?: 0 > 0)
            clipboardManager?.primaryClip?.getItemAt(0)?.text?.toString() ?: ""
        else
            ""
    }

}