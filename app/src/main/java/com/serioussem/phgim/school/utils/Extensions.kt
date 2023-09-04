package com.serioussem.phgim.school.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun String.toJsoupDocument(): Document {
    return Jsoup.parse(this)
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}