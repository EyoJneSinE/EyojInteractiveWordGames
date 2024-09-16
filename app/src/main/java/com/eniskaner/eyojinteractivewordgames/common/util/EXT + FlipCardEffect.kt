package com.eniskaner.eyojinteractivewordgames.common.util

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import com.eniskaner.eyojinteractivewordgames.R

fun View.flipCardWithSetup(
    frontView: View,
    backView: View,
    context: Context,
    isFront: Boolean,
    onFrontFlipAction: (() -> Unit)? = null,
    onBackFlipAction: (() -> Unit)? = null
): Boolean {
    val scale: Float = context.resources.displayMetrics.density
    frontView.cameraDistance = 14000f * scale
    backView.cameraDistance = 14000f * scale


    val frontAnim = AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
    val backAnim = AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet

    return if (isFront) {
        frontAnim.setTarget(frontView)
        backAnim.setTarget(backView)
        onFrontFlipAction?.invoke()
        frontAnim.start()
        backAnim.start()
        false
    } else {
        frontAnim.setTarget(backView)
        backAnim.setTarget(frontView)
        onBackFlipAction?.invoke()
        frontAnim.start()
        backAnim.start()
        true
    }
}
