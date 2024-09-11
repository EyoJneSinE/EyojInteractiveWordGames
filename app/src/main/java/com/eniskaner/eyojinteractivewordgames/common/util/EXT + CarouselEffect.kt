package com.eniskaner.eyojinteractivewordgames.common.util

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

fun ViewPager2.addCarouselEffect(enableZoom: Boolean = true) {
    clipChildren = false
    clipToPadding = false
    offscreenPageLimit = 3
    (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer(15 * Resources.getSystem().displayMetrics.density.toInt()))
    if (enableZoom) {
        compositePageTransformer.addTransformer {page, position ->
            /*val rotation = 180f * position
            page.alpha = if (rotation > 90f || rotation < 90f) 0f else 1f
            page.pivotX = page.width * 0.5f
            page.pivotY = page.height * 0.5f
            page.rotationY = rotation*/
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
    }
    setPageTransformer(compositePageTransformer)
}