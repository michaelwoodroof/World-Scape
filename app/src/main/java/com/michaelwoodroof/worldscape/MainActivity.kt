package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.helper.SetStatusBar
import com.michaelwoodroof.worldscape.helper.assignTouch
import com.michaelwoodroof.worldscape.structure.World
import com.michaelwoodroof.worldscape.ui.fragments.WorldFragment
import kotlinx.android.synthetic.main.activity_main.*
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

        SetStatusBar.create(this.window, this)

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
        btnSettings.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.settings_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.settings_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        tv.text = getString(R.string.app_name)
    }

    fun loadSettings(view: View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    fun loadCreateWorld(view: View) {
        val i = Intent(this, CreateWorldActivity::class.java)
        startActivity(i)
    }

    fun deleteWorld(view: View) {
        MaterialAlertDialogBuilder(this, R.style.DialogTheme)
            .setTitle(resources.getString(R.string.delete_world_dialog_title))
            .setMessage(resources.getString(R.string.delete_world_dialog_message))
            .setNeutralButton(resources.getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.dialog_accept)) { dialog, _ ->
                val mf = ManageFiles(baseContext)
                if (mf.deleteWorld(view.tag as String)) {
                    // Was Deleted @TODO Include option to Undo
                    val worldFragment = WorldFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.flFragmentsM, worldFragment)
                    transaction.commit()
                    dialog.dismiss()
                } else {
                    // Try Again Once
                    if (!mf.deleteWorld(view.tag as String)) {
                        Toast.makeText(
                            baseContext, resources.getString(R.string.delete_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.show()
    }

    fun loadWorld(view: View) {
        val intent = Intent(this, WorldDetailActivity::class.java)
        val tag = view.tag as World
        intent.putExtra("uid", tag.uid)
        intent.putExtra("title", tag.title)
        this.startActivity(intent)
    }

}