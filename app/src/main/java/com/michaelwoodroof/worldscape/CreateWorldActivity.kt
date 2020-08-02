package com.michaelwoodroof.worldscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
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

}