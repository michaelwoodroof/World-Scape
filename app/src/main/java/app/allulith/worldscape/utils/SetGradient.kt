package app.allulith.worldscape.utils

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import app.allulith.worldscape.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

object SetGradient {

    private const val ICON_SIZE : Int = 24
    private const val OFFSET : Int = 16

    fun assign(fab: ExtendedFloatingActionButton, context: Context, givenWidth: Int = -1, offsets: Boolean = true) {
        val paint = Paint()
        var width: Float

        var adjustedIconSize = ICON_SIZE
        var adjustedOffset = OFFSET

        if (!offsets) {
            adjustedIconSize = 0
            adjustedOffset = 0
        }

        if (givenWidth == -1) {
            width = paint.measureText(fab.text.toString())
            width += adjustedIconSize.toPixels()
            width += adjustedOffset.toPixels()
        } else {
            width = (givenWidth + adjustedIconSize.toPixels() + adjustedOffset.toPixels()).toFloat()
        }

        // Set Gradient Text on FAB
        fab.paint.shader = LinearGradient(0f, 0f, width, 0f,
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end),
            Shader.TileMode.CLAMP
        )
    }

    fun assign(btn: Button, context: Context, givenWidth: Int = -1, offsets: Boolean = true) {
        val paint = Paint()
        var width: Float

        var adjustedIconSize = ICON_SIZE
        var adjustedOffset = OFFSET

        if (!offsets) {
            adjustedIconSize = 0
            adjustedOffset = 0
        }

        if (givenWidth == -1) {
            width = paint.measureText(btn.text.toString())
            width += adjustedIconSize.toPixels()
            width += adjustedOffset.toPixels()
        } else {
            width = (givenWidth + adjustedIconSize.toPixels() + adjustedOffset.toPixels()).toFloat()
        }

        // Set Gradient Text on FAB
        btn.paint.shader = LinearGradient(0f, 0f, width, 0f,
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end),
            Shader.TileMode.CLAMP
        )
    }

    fun assign(tv: TextView, context: Context) {
        val paint = Paint()
        val width: Float = paint.measureText(tv.text.toString())

        tv.paint.shader = LinearGradient(0f, 0f, width, 0f,
            ContextCompat.getColor(context, R.color.gradient_start),
            ContextCompat.getColor(context, R.color.gradient_end),
            Shader.TileMode.CLAMP
        )
    }


}