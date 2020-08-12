package com.michaelwoodroof.worldscape

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.michaelwoodroof.worldscape.helper.ManageFiles
import kotlinx.android.synthetic.main.activity_create_world.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CreateWorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_world)

        // Set-Up Toolbar
        val tv = incToolbarCW.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.create_world_title)
        val btnMenu = incToolbarCW.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE
        val btnSettings = incToolbarCW.findViewById<ImageButton>(btnSettings.id)
        btnSettings.visibility = View.GONE
        val btnBack = incToolbarCW.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE

        // Set-Up DropDown
        // @Todo expand
        val genreTypes = arrayOf("Fantasy", "Sci-Fi", "Other")

        val adapter = ArrayAdapter(baseContext, R.layout.dropdown_menu_popup_item, genreTypes)
        ddGenre.setAdapter(adapter)

        addAnimation()
    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun addWorld(view : View) {
        // Load Intent
        // @TODO Add Data to File
        val title = tietWorld.text.toString()
        val desc = tietDesc.text.toString()
        val img = null
        // @TODO Add image saving val img
        if (ManageFiles.saveWorld(title, desc, img)) {
            // @TODO Report

        } else {
            // @TODO Report

        }
        super.onBackPressed()
    }

    private fun addAnimation() {
        findViewById<Button>(R.id.btnPickImage).setOnClickListener {
            // Only Animate if Condition is Met
            animatePickImage()
        }
    }

    private fun animatePickImage() {
        val root = clMainCW

        val c = ConstraintSet()
        c.clone(this, R.layout.activity_create_world_alt)

        if (btnPickImage.tag != "run") {
            val dur = ChangeBounds()
            dur.duration = 300

            val r1 = Runnable {
                TransitionManager.beginDelayedTransition(root, dur)
                c.applyTo(root)
                btnPickImage.text = ""
            }

            val r2 = Runnable {
                // Convert to Circle
                btnPickImage.setPadding(36, 4, 12, 4)
                btnPickImage.text = ""
                btnPickImage.background = ContextCompat.getDrawable(this, R.drawable.circle)
                btnPickImage.tag = "run"
                // Make Image Visible
                cvPreview.visibility = View.VISIBLE
            }

            val r = Runnable {}

            r.run {
                r1.run()
                Handler().postDelayed(r2, dur.duration)
            }

        }
    }

}