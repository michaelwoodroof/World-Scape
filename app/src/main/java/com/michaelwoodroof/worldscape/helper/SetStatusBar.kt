package com.michaelwoodroof.worldscape.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.michaelwoodroof.worldscape.R

object SetStatusBar {

    fun create(window: Window, context: Context) {
        val background: Drawable? = ContextCompat.getDrawable(context, R.drawable.default_gradient)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, R.color.transparent)
        window.setBackgroundDrawable(background)
    }

}