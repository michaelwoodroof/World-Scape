package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.helper.SetStatusBar
import com.michaelwoodroof.worldscape.helper.assignTouch
import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.structure.World
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.activity_world_detail.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CharacterDetailActivity : AppCompatActivity() {

    private var character : MyCharacter? = null
    lateinit var world : World

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        SetStatusBar.create(this.window, this)

        val btnSettings = incToolbarCharacterDetail.findViewById<ImageButton>(btnSettings.id)
        btnSettings.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.settings_expand, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.settings_shrink, null) as AnimatedVectorDrawable
            )
        })

        val btnBack = incToolbarCharacterDetail.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE
        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion_left, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_left, null) as AnimatedVectorDrawable
            )
        })

        val btnMenu = incToolbarCharacterDetail.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE

        character = intent?.getParcelableExtra("character")

        val title = incToolbarCharacterDetail.findViewById<TextView>(R.id.tvTitle)
        title.text = character?.name

        tvCDDesc.text = character?.biography

        val mf = ManageFiles(this)
        imgCDPreview.setImageBitmap(character?.wuid?.let { character?.uid?.let { it1 ->
            mf.getCharacterImage(it,
                it1
            )
        }})
        
//        tvCDHeight.text = character?.height
//        tvCDWeight.text = character?.weight
//        tvCDBirthday.text = character?.yearOfBirth
//        tvCDBirthPlace.text = character?.placeOfBirth

    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun loadSettings(view : View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }
}