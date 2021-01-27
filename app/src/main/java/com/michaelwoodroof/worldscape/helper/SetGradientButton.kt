package com.michaelwoodroof.worldscape.helper

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.helper.ConverterDPPX.toPixels

object SetGradientButton {

    private const val ICON_SIZE : Int = 24
    private const val OFFSET : Int = 16

    fun assign(fab : ExtendedFloatingActionButton, context : Context) {
        val paint = Paint()
        var width : Float = paint.measureText(fab.text.toString())
        width += ICON_SIZE.toPixels()
        width += OFFSET.toPixels()

        // Set Gradient Text on FAB
        fab.paint.shader = LinearGradient(0f, 0f, width, 0f,
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end),
            Shader.TileMode.CLAMP)
    }

    fun assign(btn : Button, context : Context) {
        val paint = Paint()
        val width : Float = paint.measureText(btn.text.toString())

        // Set Gradient Text on FAB
        btn.paint.shader = LinearGradient(0f, 0f, width, 0f,
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end),
            Shader.TileMode.CLAMP)
    }

}