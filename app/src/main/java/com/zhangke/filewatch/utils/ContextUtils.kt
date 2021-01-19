package com.zhangke.filewatch.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.location.LocationManager

/**
 * Created by ZhangKe on 2020/11/28.
 */

@Volatile
lateinit var appContext: Context
    private set

fun initApplication(application: Application) {
    appContext = application
}

fun Context.extractActivity(): Activity {
    return extractActivityOrNull()
        ?: throw IllegalArgumentException("$this is not an Activity or a wrapper with Activity.")
}

fun Context.extractActivityOrNull(): Activity? {
    var result = this
    while (result is ContextWrapper) {
        if (result is Activity) {
            return result
        }
        result = result.baseContext
    }
    return null
}

fun Context.getAlarmManagerService(): AlarmManager {
    return getSystemService(Context.ALARM_SERVICE) as AlarmManager
}

fun Context.getLocationManager(): LocationManager {
    return getSystemService(Context.LOCATION_SERVICE) as LocationManager
}