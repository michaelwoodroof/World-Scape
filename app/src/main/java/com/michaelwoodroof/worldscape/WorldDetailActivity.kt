package com.michaelwoodroof.worldscape

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.michaelwoodroof.worldscape.ui.CharacterFragment
import com.michaelwoodroof.worldscape.ui.WorldDetailFragment
import com.michaelwoodroof.worldscape.ui.WorldFragment
import kotlinx.android.synthetic.main.activity_world_detail.*

class WorldDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_detail)

        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            if (bundle.containsKey("uid")) {
                // @TODO LOAD DATA FROM UID
//                tvDesc.text = intent.getStringExtra("uid")
            }

            if (bundle.containsKey("title")) {
                // @TODO
                tvTitle.text = intent.getStringExtra("title")

                val worldDetailFragment = WorldDetailFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.flFragmentsWD, worldDetailFragment)
                transaction.commit()
            }
        }

    }

    fun openNavMenu(view : View) {
        dlMain.openDrawer(GravityCompat.START)
    }

    fun openSubMenu(item : MenuItem) {

        when (item.title) {

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