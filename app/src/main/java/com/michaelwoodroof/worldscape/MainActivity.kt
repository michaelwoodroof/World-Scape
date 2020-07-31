package com.michaelwoodroof.worldscape

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.michaelwoodroof.worldscape.ui.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add Fragment to Frame Layout
        val worldFragment = WorldFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flFragmentsM, worldFragment)
        transaction.commit()

        val fabSrc : Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_add_24, null)!!
        } else {
            resources.getDrawable(android.R.drawable.ic_input_add)
        }

        val filtered = fabSrc.constantState!!.newDrawable()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            filtered.mutate().colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
        } else {
            filtered.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        }

        fabNewWorld.setImageDrawable(filtered)

        // Set Up Toolbar
        val tv = incToolbar.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.app_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            incToolbar.elevation = 4F
        }
    }

    fun loadSettings(view : View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    fun loadCreateWorld(view : View) {
        val i = Intent(this, CreateWorldActivity::class.java)
        startActivity(i)
    }

}