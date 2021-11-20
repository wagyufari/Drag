package com.mayburger.drag.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.res.Resources
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.core.animation.addListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


object ViewUtils {

    private var screenWidth = 0
    private var screenHeight = 0

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Float): Float {
        return (dp * Resources.getSystem().displayMetrics.density)
    }

    suspend fun Dialog.showSuspended(){
        CoroutineScope(Main).launch {
            show()
        }
    }
    suspend fun Dialog.dismiss(){
        CoroutineScope(Main).launch {
            dismiss()
        }
    }

    fun View.getDuration(duration: Long): Long {
        return when (Settings.Global.getFloat(
            this.context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f
        )) {
            0.5f -> {
                duration
            }
            1f -> {
                duration / 2
            }
            else -> {
                duration
            }
        }
    }

    fun View.height(
        height: Int, duration: Long? = 500, onEnd: (() -> Unit)? = {}, percent: Float? = 100f,
        onPercent: (() -> Unit)? = {}
    ) {
        ValueAnimator.ofInt(this.height, dpToPx(height)).apply {
            this.duration = getDuration(duration ?: 500)
            Handler().postDelayed({
                onPercent?.invoke()
            }, (500.times(percent ?: 100f) / 100).toLong())
            addUpdateListener {
                this@height.layoutParams.height = it.animatedValue as Int
                this@height.requestLayout()
            }
            addListener(onEnd = {
                onEnd?.invoke()
            })
            start()
        }
    }


    fun View.flipX(
        duration: Long? = 400,
        onFlip: (() -> Unit)? = {},
        onEnd: (() -> Unit)? = {},
        after: Long? = 0
    ) {
        Handler().postDelayed({
            AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(this@flipX, View.SCALE_X, 1f).apply {
                    this.duration = getDuration(duration ?: 500)
                    addListener(onEnd = {
                        onEnd?.invoke()
                    })
                }).after(
                    ObjectAnimator.ofFloat(this@flipX, View.SCALE_X, 0f).apply {
                        this.duration = getDuration(duration ?: 500)
                        addListener(onEnd = {
                            onFlip?.invoke()
                        })
                    }
                )
                start()
            }
        }, after ?: 0)
    }

    fun View.animToY(
        y: Float,
        after: Long? = 0,
        duration: Long? = 500,
        onEnd: (() -> Unit)? = {},
        percent: Float? = 100f,
        onPercent: (() -> Unit)? = {},
        interpolator: TimeInterpolator? = null
    ) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@animToY, View.TRANSLATION_Y, dpToPx(y)).apply {
                this.duration = getDuration(duration ?: 500)
                Handler().postDelayed({
                    onPercent?.let {
                        it.invoke()
                        this.cancel()
                    }
                }, (getDuration(duration ?: 500).times(percent ?: 100f) / 100).toLong())
                addListener(onEnd = {
                    onEnd?.invoke()
                })
                interpolator?.let {
                    this.interpolator = it
                }
            }).after(after ?: 0)
            start()
        }
    }

    fun View.animToX(
        x: Float,
        duration: Long? = 500,
        onEnd: (() -> Unit)? = {},
        percent: Float? = 100f,
        onPercent: (() -> Unit)? = {}
    ) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@animToX, View.TRANSLATION_X, dpToPx(x)).apply {
                this.duration = getDuration(duration ?: 500)
                Handler().postDelayed({
                    onPercent?.invoke()
                }, (getDuration(duration ?: 500).times(percent ?: 100f) / 100).toLong())
                addListener(onEnd = {
                    onEnd?.invoke()
                })
            })
            start()
        }
    }

    fun View.scale(
        scale: Float,
        duration: Long? = 1000,
        onEnd: (() -> Unit)? = {},
        after: Long? = 0
    ) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@scale, View.SCALE_X, scale).apply {
                this.duration = getDuration(duration ?: 500)
            }).after(after ?: 0)
            play(ObjectAnimator.ofFloat(this@scale, View.SCALE_Y, scale).apply {
                this.duration = getDuration(duration ?: 500)
                addListener(onEnd = {
                    onEnd?.invoke()
                })
            }).after(after ?: 0)
            start()
        }
    }


    fun View.scaleY(
        scale: Float,
        duration: Long? = 1000,
        onEnd: (() -> Unit)? = {},
        after: Long? = 0
    ) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@scaleY, View.SCALE_Y, scale).apply {
                this.duration = getDuration(duration ?: 500)
                addListener(onEnd = {
                    onEnd?.invoke()
                })
            }).after(after ?: 0)
            start()
        }
    }

    fun View.scaleX(scale: Float, duration: Long? = 1000, onEnd: (() -> Unit)? = {}) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@scaleX, View.SCALE_X, scale).apply {
                this.duration = getDuration(duration ?: 500)
                addListener(onEnd = {
                    onEnd?.invoke()
                })
            })
            start()
        }
    }

    fun View.width(
        width: Int, duration: Long? = 500, onEnd: (() -> Unit)? = {}, percent: Float? = 100f,
        onPercent: (() -> Unit)? = {}
    ) {
        ValueAnimator.ofInt(this.width, dpToPx(width)).apply {
            this.duration = getDuration(duration ?: 500)
            Handler().postDelayed({
                onPercent?.invoke()
            }, (500.times(percent ?: 100f) / 100).toLong())
            addUpdateListener {
                this@width.layoutParams.width = it.animatedValue as Int
                this@width.requestLayout()
            }
            addListener(onEnd = {
                onEnd?.invoke()
            })
            start()
        }
    }

    fun View.shrinkHide(callback: (() -> Unit)? = {}) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@shrinkHide, View.SCALE_X, 0f).apply {
                duration = 300
            })
            play(ObjectAnimator.ofFloat(this@shrinkHide, View.SCALE_Y, 0f).apply {
                duration = 300
                addListener(onEnd = {
                    callback?.invoke()
                    visibility = View.GONE
                })
            })
            start()
        }
    }

    fun View.expandShow(callback: (() -> Unit)? = {}) {
        this.visibility = View.VISIBLE
        this.scaleX = 0f
        this.alpha = 1f
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@expandShow, View.SCALE_X, 1f).apply {
                duration = 300
            })
            play(ObjectAnimator.ofFloat(this@expandShow, View.SCALE_Y, 1f).apply {
                duration = 300
                addListener(onEnd = {
                    callback?.invoke()
                })
            })
            start()
        }
    }


    fun View.fadeHide(onEnd: (() -> Unit)? = { }, duration: Long? = 700, after: Long? = 0) {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@fadeHide, View.ALPHA, 0f).apply {
                this.duration = getDuration(duration ?: 500)
                addListener(onEnd = {
                    onEnd?.invoke()
                    visibility = View.GONE
                })
            }).after(after ?: 0)
            start()
        }
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.isHidden(isHidden: Boolean) {
        visibility = if (isHidden) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    fun View.isHidden():Boolean{
        return visibility == View.GONE || visibility == View.INVISIBLE
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.toggle(duration: Long? = 1000) {
        if (this.visibility == View.VISIBLE) {
            fadeHide(duration = duration)
        } else if (this.visibility == View.GONE) {
            fadeShow(duration = duration)
        }
    }


    fun View.fadeShow(
        onEnd: (() -> Unit)? = {},
        duration: Long? = 1000,
        after: Long? = 0,
        alpha: Float? = 1f
    ) {
        this.alpha = 0f
        this.visibility = View.VISIBLE
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(this@fadeShow, View.ALPHA, alpha ?: 1f).apply {
                this.duration = getDuration(duration ?: 500)
                addListener(onEnd = {
                    onEnd?.invoke()
                })
            }).after(after ?: 0)
            start()
        }
    }
}


