package com.michaelwoodroof.worldscape

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.structure.World

class CharacterDetailActivity : AppCompatActivity() {

    lateinit var character : MyCharacter
    lateinit var world : World

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
    }
}