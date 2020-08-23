package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.ui.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_world_detail.*
import kotlinx.android.synthetic.main.default_toolbar.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        when (sharedPreferences.getString("theme", "")) {
            "dark_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            "light_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

        }

        setContentView(R.layout.activity_main)

        // Add Fragment to Frame Layout
        val worldFragment = WorldFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragmentsM, worldFragment)
        transaction.commit()

        // Set Up Toolbar
        val tv = incToolbarM.findViewById<TextView>(tvTitle.id)
        // Hide Menu as Not Needed
        val btnMenu = incToolbarM.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE

        val btnSettings = incToolbarM.findViewById<ImageButton>(btnSettings.id)
        btnSettings.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.settings_expand, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.settings_shrink, null) as AnimatedVectorDrawable)
        })

        tv.text = getString(R.string.app_name)
    }

    private fun handleTouch(view : ImageButton, event: MotionEvent) : Boolean {
        val chosenDrawable1 = ResourcesCompat.getDrawable(resources, R.drawable.settings_expand, null)
        val chosenDrawable2 = ResourcesCompat.getDrawable(resources, R.drawable.settings_shrink, null)
        val chosenDrawable : AnimatedVectorDrawable
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (view.tag == "na") {
                    val b = view as ImageButton
                    chosenDrawable = chosenDrawable1 as AnimatedVectorDrawable
                    b.tag = "es"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (view.tag == "es") {
                    val b = view as ImageButton

                    val xE = event.x
                    val yE = event.y

                    if ((xE < 0 || xE > b.width || yE < 0 || yE > b.height)) {
                        chosenDrawable = chosenDrawable2 as AnimatedVectorDrawable
                        b.tag = "na"
                        b.setImageDrawable(chosenDrawable)
                        chosenDrawable.start()
                    }
                }
                return false
            }
            MotionEvent.ACTION_UP -> {
                if (view.tag == "es") {
                    val b = view as ImageButton
                    chosenDrawable = chosenDrawable2 as AnimatedVectorDrawable
                    b.tag = "na"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()

                    val xE = event.x
                    val yE = event.y

                    if (!(xE < 0 || xE > b.width || yE < 0 || yE > b.height)) {
                        // Is Within Bounds
                        view.performClick()
                    }
                }
                return false
            }
            else -> {return false}
        }
    }

    override fun onStart() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString("theme", "")) {
            "dark_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            "light_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

        }
        super.onStart()
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