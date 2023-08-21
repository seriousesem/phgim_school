package com.serioussem.phgim.school.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun String.toJsoupDocument(): Document {
    return Jsoup.parse(this)
}