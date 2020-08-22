package com.michaelwoodroof.worldscape

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.michaelwoodroof.worldscape.ui.CharacterFragment
import com.michaelwoodroof.worldscape.ui.WorldDetailFragment
import kotlinx.android.synthetic.main.activity_world_detail.*
import kotlinx.android.synthetic.main.default_toolbar.*

class WorldDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_detail)

        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            if (bundle.containsKey("uid")) {
                // @TODO LOAD DATA FROM UID
//                tvDesc.text = intent.getStringExtra("uid")
            } else {
                // @TODO
            }

            if (bundle.containsKey("title")) {
                // @TODO
                val tv = incToolbarWD.findViewById<TextView>(tvTitle.id)
                tv.text = intent.getStringExtra("title")

                val worldDetailFragment = WorldDetailFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.flFragmentsWD, worldDetailFragment)
                transaction.commit()
            } else {
                // @TODO
            }
        }

    }

    fun loadSettings(view : View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    fun loadMenu(view : View) {
        dlMain.openDrawer(GravityCompat.START)
    }

    fun loadCreateCharacter(view : View) {
        val uid = intent.getStringExtra("uid")
        val i = Intent(this, CreateCharacterActivity::class.java)
        intent.putExtra("uid", uid)
        startActivity(i)
    }

    fun loadAllCharacters(view : View) {
        val characterFragment = CharacterFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragmentsWD, characterFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun openSubMenu(item : MenuItem) {

        when (item.title) {

            // @TODO Replace Errors with to string.xml method

            // @TODO ADD MENU ITEMS
            "Characters" -> {
                val characterFragment = CharacterFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragmentsWD, characterFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            "Placeholder" -> {
                Toast.makeText(this, "TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show()
            }

            "Settings" -> {
                Toast.makeText(this, "TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "ERR : NO SUCH ITEM", Toast.LENGTH_SHORT).show()
            }
        }

        dlMain.closeDrawer(GravityCompat.START)

    }

}