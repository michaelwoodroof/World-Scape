package com.michaelwoodroof.worldscape.helper

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.michaelwoodroof.worldscape.R

object SetStatusBar {

    fun create(window: Window, context: Context) {
        val background: Drawable? = ContextCompat.getDrawable(context, R.drawable.default_gradient)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, R.color.transparent)
        window.setBackgroundDrawable(background)
        this.setShade(window, context)
    }

    private fun setShade(window: Window, context : Context) {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        when (sharedPreferences.getString("theme", "")) {
            "dark_mode" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    @Suppress("DEPRECATION") //@TODO Review
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }

            "light_mode" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility = 0
                }
            }

            else -> {
                // Values 33 and 17 are sometimes used for dark and light mode this is why they
                // are added
                when (context.resources.configuration.uiMode) {

                    Configuration.UI_MODE_NIGHT_YES, 33 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            @Suppress("DEPRECATION")
                            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        }
                    }

                    Configuration.UI_MODE_NIGHT_NO, 17 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            @Suppress("DEPRECATION")
                            window.decorView.systemUiVisibility = 0
                        }
                    }

                }
            }
        }

    }

}