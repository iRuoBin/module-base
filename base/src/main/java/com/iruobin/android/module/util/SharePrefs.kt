package com.iruobin.android.module.util

import android.content.Context
import android.content.SharedPreferences
import com.iruobin.android.module.util.Utils.symbol
import java.lang.IllegalArgumentException

/**
 * SharePreferences 操作类
 * @author iRuoBin
 * @since 0.1
 */
object SharePrefs {

    private var defSharePrefsName = symbol
    private var appContext: Context? = null

    @JvmStatic
    fun init(context: Context?) {
        appContext = context?.applicationContext
        defSharePrefsName = appContext?.packageName?.substringAfterLast('.') ?: symbol
    }


    /**
     * 根据 SharePrefs Name 来获取 SharePreferences 对象
     */
    @JvmOverloads
    fun getSharePrefs(sharePrefsName: String = defSharePrefsName, context: Context? = appContext): SharedPreferences {
        if (context == null) throw IllegalArgumentException("context is null")
        return context.getSharedPreferences(sharePrefsName, Context.MODE_PRIVATE)
    }

    /**
     * 往相应的 SharePrefs 里面 存数据
     */
    @JvmOverloads
    fun put(key: String, value: Any, sharePrefsName: String = defSharePrefsName, context: Context? = appContext) {
        val sp = getSharePrefs(sharePrefsName, context)
        val editor = sp.edit()
        when(value) {
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is String -> editor.putString(key, value)
            else -> editor.putString(key, value.toString())
        }
        editor.apply()
    }

    /**
     * 从相应的 SharePrefs 里面 取数据
     */
    @JvmOverloads
    fun get(key: String, defaultValue: Any, sharePrefsName: String = defSharePrefsName, context: Context? = appContext): Any? {
        val sp = getSharePrefs(sharePrefsName, context)
        return when(defaultValue) {
            is Boolean -> sp.getBoolean(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            is String -> sp.getString(key, defaultValue)
            else -> sp.getString(key, defaultValue.toString())
        }
    }

    /**
     * 从相应的 SharePrefs 里面 删指定数据
     */
    @JvmOverloads
    fun remove(key: String, sharePrefsName: String = defSharePrefsName, context: Context? = appContext) {
        val sp = getSharePrefs(sharePrefsName, context)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * 看相应的 SharePrefs 里面 是否有指定数据
     */
    @JvmOverloads
    fun contain(key: String, sharePrefsName: String = defSharePrefsName, context: Context? = appContext): Boolean {
        val sp = getSharePrefs(sharePrefsName, context)
        return sp.contains(key)
    }

    /**
     * 相应的 SharePrefs 里面 删除所有数据
     */
    @JvmOverloads
    fun clear(sharePrefsName: String = defSharePrefsName, context: Context? = appContext) {
        val sp = getSharePrefs(sharePrefsName, context)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * 相应的 SharePrefs 里面 取出所有数据
     */
    @JvmOverloads
    fun all(sharePrefsName: String = defSharePrefsName, context: Context? = appContext): MutableMap<String, *>? {
        val sp = getSharePrefs(sharePrefsName, context)
        return sp.all
    }

}