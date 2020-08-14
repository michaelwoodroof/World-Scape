package com.michaelwoodroof.worldscape

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.ui.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.*

class MainActivity : AppCompatActivity() {

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
        tv.text = getString(R.string.app_name)
    }

    override fun onStart() {
        val mf = ManageFiles(this)
        mf.getWorldImage("")
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