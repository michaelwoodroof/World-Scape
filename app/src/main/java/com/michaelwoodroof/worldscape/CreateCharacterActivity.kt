package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS1
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS2
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.default_toolbar.*
import kotlinx.android.synthetic.main.fragment_create_character_s1.*

class CreateCharacterActivity : AppCompatActivity() {

    // @TODO Utilise
    lateinit var currentCharacter : CharacterContent.CharacterItem

    @SuppressLint("ClickableViewAccessibility")
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

        btnBack.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion_left, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_left, null) as AnimatedVectorDrawable
            )
        })

        // Shrink FABS
        //fabNext.shrink()

        // Set up Fragment
        val createCharacterFragment = CreateCharacterFragmentS1()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flCCMain, createCharacterFragment)
        transaction.commit()

        // Used to Hide Create Character when Keyboard is not Screen (for better user experience)
        val v : View = findViewById(R.id.clMainCC)
        v.viewTreeObserver.addOnGlobalLayoutListener {
            if (flCCMain.tag == "S5") {
                if ((v.rootView.height - v.height) > TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 200F, baseContext.resources.displayMetrics)
                ) {
                    fabCreateCC.visibility = View.INVISIBLE
                } else {
                    fabCreateCC.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Update Step Number
        when (flCCMain.tag) {
            "S2" -> {
                flCCMain.tag = "S1"
            }
        }
    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun setLink(view : View) {
        val mf = ManageFiles(this)
        when(view.id) {

            // @TODO Replace with Actual Places

           btnLinkPlace.id -> {
                val items = arrayOf("Item One","Item Two","Item Three","Item Four","Item Five")
                MaterialAlertDialogBuilder(this)
                    .setTitle("Test Title")
                    .setItems(items) {dialog, which ->
                        tietPlaceOfBirth.text = SpannableStringBuilder(items[which])
                        tietPlaceOfBirth.setSelection(items[which].length)
                        dialog.dismiss()
                    }.show()
            }

            btnLinkCurrentLoc.id -> {
                val items = arrayOf("Item One","Item Two","Item Three","Item Four","Item Five")
                MaterialAlertDialogBuilder(this)
                    .setTitle("Test Title")
                    .setItems(items) {dialog, which ->
                        tietCurrentLocation.text = SpannableStringBuilder(items[which])
                        tietCurrentLocation.setSelection(items[which].length)
                        dialog.dismiss()
                    }.show()
            }

            else -> {
                Log.e("error", "Err : Button not found")
            }

        }
    }

    fun stepForward(view : View) {
        if (flCCMain.tag == "S1") {
            // Update Frag
            flCCMain.tag = "S2"
            val createCharacterFragment = CreateCharacterFragmentS2()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flCCMain, createCharacterFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    fun createCharacter(view : View) {
        super.onBackPressed()
    }

}