package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CreateCharacterActivity : AppCompatActivity() {

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

        // Used to Hide Create Character when Keyboard is not Screen (for better user experience)
        val v : View = findViewById(R.id.clMainCC)
        v.viewTreeObserver.addOnGlobalLayoutListener {
            if ((v.rootView.height - v.height) > TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 200F, baseContext.resources.displayMetrics)
            ) {
                btnCreateCC.visibility = View.INVISIBLE
            } else {
                btnCreateCC.visibility = View.VISIBLE
            }
        }

    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun createCharacter(view : View) {
        super.onBackPressed()
    }

}