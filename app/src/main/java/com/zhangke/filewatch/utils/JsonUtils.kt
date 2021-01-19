package com.zhangke.filewatch.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by ZhangKe on 2020/11/28.
 */

val sharedGson: Gson = GsonBuilder()
    .disableHtmlEscaping()
    .create()
