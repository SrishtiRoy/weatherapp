package com.gs.weather.utils.extension

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator


fun TextView.animateNumberChange(
    newValue: Int,
    isSignShown: Boolean = false,
    duration: Long = 400L
) {
    val oldValue: Int = text.toString().toIntOrNull() ?: 0
    val animator = ValueAnimator.ofInt(oldValue, newValue)
    animator.addUpdateListener {
        text = if (isSignShown) {
            (it.animatedValue as Int).withSign()
        } else {
            (it.animatedValue as Int).toString()
        }
    }
    animator.interpolator = FastOutSlowInInterpolator()
    animator.duration = duration
    animator.start()
}