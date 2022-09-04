package com.lbc.app.common

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

object Utils {
    fun getGlideUrl(url: String) = GlideUrl(
        url, LazyHeaders.Builder()
            .addHeader("User-Agent", "Android")
            .build()
    )
}