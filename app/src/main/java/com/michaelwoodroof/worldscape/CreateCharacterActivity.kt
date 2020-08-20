package com.michaelwoodroof.worldscape

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CreateCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_character)

        // Set-Up Toolbar
        val tv = incToolbarCC.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.create_character_title)
        val btnMenu = incToolbarCC.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE
        val btnSettings = incToolbarCC.findViewById<ImageButton>(btnSettings.id)
        btnSettings.visibility = View.GONE
        val btnBack = incToolbarCC.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE

    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun createCharacter(view : View) {

    }

}