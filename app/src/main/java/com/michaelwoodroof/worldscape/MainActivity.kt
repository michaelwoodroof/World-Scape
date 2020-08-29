package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.ui.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_world_detail.*
import kotlinx.android.synthetic.main.default_toolbar.*
import kotlinx.android.synthetic.main.fragment_world.*

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

    fun deleteWorld(view : View) {
        val mf = ManageFiles(baseContext)
        if (mf.deleteWorld(view.tag as String)!!) {
            // Was Deleted @TODO Include option to Undo
            // @TODO Improve this Method
            val worldFragment = WorldFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragmentsM, worldFragment)
            transaction.commit()
            Toast.makeText(baseContext, "Deleted", Toast.LENGTH_SHORT).show()
        } else {
            // @TODO Why it wasn't deleted
            // Try Again Once
        }
    }

}