package app.allulith.worldscape.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import app.allulith.worldscape.fragments.CharacterFragment
import app.allulith.worldscape.R
import app.allulith.worldscape.SettingsActivity
import app.allulith.worldscape.fragments.WorldDetailFragment
import app.allulith.worldscape.createcharacter.CreateCharacterActivity
import app.allulith.worldscape.utils.SetStatusBar
import app.allulith.worldscape.utils.assignTouch
import app.allulith.worldscape.structure.MyCharacter
import kotlinx.android.synthetic.main.activity_world_detail.*
import kotlinx.android.synthetic.main.default_toolbar.*

class WorldDetailActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_detail)

        SetStatusBar.create(this.window, this.baseContext)

        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            if (bundle.containsKey("uid")) {

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

        val btnSettings = incToolbarWD.findViewById<ImageButton>(btnSettings.id)
        btnSettings.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.settings_expand, null) as AnimatedVectorDrawable,
            ResourcesCompat.getDrawable(resources, R.drawable.settings_shrink, null) as AnimatedVectorDrawable)
        })

    }

    fun loadSettings(view : View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    fun loadMenu(view : View) {
        dlMain.openDrawer(GravityCompat.START)
    }

    fun loadCreateCharacter(view : View) {
        val wuid = intent.getStringExtra("uid").toString()
        val intent = Intent(this, CreateCharacterActivity::class.java)
        intent.putExtra("uid", wuid)
        startActivity(intent)
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

        // @TODO Review
        item.isCheckable = true
        item.isChecked = false

        dlMain.closeDrawer(GravityCompat.START)
    }

    fun loadCharacter(view : View) {
        val data = view.tag as MyCharacter
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("character", data as Parcelable)
        this.startActivity(intent)
    }

}