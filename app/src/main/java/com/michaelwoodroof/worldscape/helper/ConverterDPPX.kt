package com.michaelwoodroof.worldscape.helper

import android.content.res.Resources

object ConverterDPPX {

    fun Int.toPixels(): Int = (this * Resources.getSystem().displayMetrics.density.toInt())

    fun Int.toDP(): Int = (this / Resources.getSystem().displayMetrics.density.toInt())

}