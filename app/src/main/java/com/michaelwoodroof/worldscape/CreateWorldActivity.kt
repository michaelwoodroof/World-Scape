package com.michaelwoodroof.worldscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.michaelwoodroof.worldscape.helper.ManageFiles
import kotlinx.android.synthetic.main.activity_create_world.*

class CreateWorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_world)
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
        val success = ManageFiles.saveWorld(title, desc, img)
        super.onBackPressed()
    }

}