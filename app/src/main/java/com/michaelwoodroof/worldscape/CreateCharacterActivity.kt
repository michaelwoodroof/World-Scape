package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS1
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS2
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.default_toolbar.*
import kotlinx.android.synthetic.main.fragment_create_character_s1.*
import kotlinx.android.synthetic.main.fragment_create_character_s1.tvOptionalCC1
import kotlinx.android.synthetic.main.fragment_create_character_s1.tvOptionalCC2
import kotlinx.android.synthetic.main.fragment_create_character_s1.tvOptionalCC3
import kotlinx.android.synthetic.main.fragment_create_character_s1.tvOptionalCC4
import kotlinx.android.synthetic.main.fragment_create_character_s2.*
import org.w3c.dom.Text

class CreateCharacterActivity : AppCompatActivity() {

    // @TODO Utilise
    lateinit var currentCharacter : CharacterContent.CharacterItem
    lateinit var r : Runnable

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
            return@OnTouchListener AssignTouchEvent.assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.chevron_expansion_left,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.chevron_shrink_left,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        // Set up Fragment
        val createCharacterFragment = CreateCharacterFragmentS1()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flCCMain, createCharacterFragment)
        transaction.commit()

        // Used to Hide Create Character when Keyboard is not Screen (for better user experience)
        val v : View = findViewById(R.id.clMainCC)
        v.viewTreeObserver.addOnGlobalLayoutListener {
            if ((v.rootView.height - v.height) > TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 200F, baseContext.resources.displayMetrics
                )
            ) {
                fabNext.visibility = View.INVISIBLE
            } else {
                fabNext.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setUpFocusChangers(0)
        setUpTextChangers(0)
        // Init Runnable
        r = Runnable {}
    }

    override fun onBackPressed() {
        backTasks()
        super.onBackPressed()
    }

    fun goBack(view: View) {
        backTasks()
        super.onBackPressed()
    }

    private fun backTasks() {
        // Update Step Number
        when (flCCMain.tag) {
            "S2" -> {
                flCCMain.tag = "S1"
            }
        }
    }

    // Get Reset on Text Changed
    private fun delayError(field : Int) {
        // Ensures Reset before Attempting Timeout
        Handler().removeCallbacksAndMessages(r)
        r = Runnable {
            checkField(field)
        }
        Handler().postDelayed(r, 2000)
    }

    fun setLink(view: View) {
        val mf = ManageFiles(this)
        when(view.id) {

            // @TODO Replace with Actual Places

            btnLinkPlace.id -> {
                val items = arrayOf("Item One", "Item Two", "Item Three", "Item Four", "Item Five")
                MaterialAlertDialogBuilder(this)
                    .setTitle("Test Title")
                    .setItems(items) { dialog, which ->
                        tietPlaceOfBirth.text = SpannableStringBuilder(items[which])
                        tietPlaceOfBirth.setSelection(items[which].length)
                        dialog.dismiss()
                    }.show()
            }

            btnLinkCurrentLoc.id -> {
                val items = arrayOf("Item One", "Item Two", "Item Three", "Item Four", "Item Five")
                MaterialAlertDialogBuilder(this)
                    .setTitle("Test Title")
                    .setItems(items) { dialog, which ->
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

    fun stepForward(view: View) {
        if (flCCMain.tag == "S1") {
            // Update Frag if Condition passed
            if (checkFields(0)) {
                flCCMain.tag = "S2"
                val createCharacterFragment = CreateCharacterFragmentS2()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flCCMain, createCharacterFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun checkFields(stageNumber: Int) : Boolean {

        when (stageNumber) {

            0 -> {
                val c1 = checkField(0)
                val c2 = checkField(1)
                // Optional fields
                checkField(2)
                checkField(3)
                checkField(4)
                checkField(5)
                checkField(6)
                return c1 && c2
            }

            1 -> {
                return true
            }

            2 -> {
                return true
            }

            else -> {
                return false
            }

        }

    }

    private fun checkField(field: Int) : Boolean {

        when (field) {

            0 -> {
                return if (tietCharacterName.text.toString().trim() == "") {
                    tilCharacterName.error = getString(R.string.err_no_cc_name)
                    false
                } else {
                    tilCharacterName.error = null
                    true
                }
            }

            1 -> {
                return if (tietBiography.text.toString().trim() == "") {
                    tilBiography.error = getString(R.string.err_no_bio)
                    false
                } else {
                    tilBiography.error = null
                    true
                }
            }

            2 -> {
                if (tietBirthYear.text.toString().trim() == "") {
                    tilBirthYear.error = getString(R.string.err_no_by)
                    tvOptionalCC1.visibility = View.GONE
                } else {
                    tilBirthYear.error = null
                    tvOptionalCC1.visibility = View.VISIBLE
                }
                return true
            }

            3 -> {
                if (tietPlaceOfBirth.text.toString().trim() == "") {
                    tilPlaceOfBirth.error = getString(R.string.err_no_pob)
                    tvOptionalCC4.visibility = View.GONE
                    if (btnLinkPlace.tag != "err") {
                        when (val drawable = btnLinkPlace.drawable) {
                            is AnimatedVectorDrawable -> {
                                btnLinkPlace.tag = "err"
                                drawable.start()
                            }
                        }
                    }
                } else {
                    tilPlaceOfBirth.error = null
                    tvOptionalCC4.visibility = View.VISIBLE
                    if (btnLinkPlace.tag == "err") {
                        btnLinkPlace.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.warning_to_link,
                                null
                            )
                        )
                        when (val drawable = btnLinkPlace.drawable) {
                            is AnimatedVectorDrawable -> {
                                btnLinkPlace.tag = "ne"
                                drawable.start()
                            }
                        }
                    }
                }
                return true
            }

            4 -> {
                if (tietCurrentLocation.text.toString().trim() == "") {
                    tilCurrentLocation.error = getString(R.string.err_no_cl)
                    tvOptionalCC3.visibility = View.GONE
                    if (btnLinkCurrentLoc.tag != "err") {
                        when (val drawable = btnLinkCurrentLoc.drawable) {
                            is AnimatedVectorDrawable -> {
                                btnLinkCurrentLoc.tag = "err"
                                drawable.start()
                            }
                        }
                    }
                } else {
                    tilCurrentLocation.error = null
                    tvOptionalCC3.visibility = View.VISIBLE
                    if (btnLinkCurrentLoc.tag == "err") {
                        btnLinkCurrentLoc.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.warning_to_link,
                                null
                            )
                        )
                        when (val drawable = btnLinkCurrentLoc.drawable) {
                            is AnimatedVectorDrawable -> {
                                btnLinkCurrentLoc.tag = "ne"
                                drawable.start()
                            }
                        }
                    }
                }
                return true
            }

            5 -> {
                // Image Checking
                if (imgPreviewCC.visibility == View.VISIBLE) {
                    // @TODO Implement
                } else {
                    // @TODO Implement
                }
                return true
            }

            6 -> {
                if (tietBirthDate.text.toString().trim() == "") {
                    tilBirthDate.error = getString(R.string.err_no_bday)
                    tvOptionalCC2.visibility = View.GONE
                } else {
                    tilBirthDate.error = null
                    tvOptionalCC2.visibility = View.VISIBLE
                }
                return true
            }

            7 -> {
                if (tietHeight.text.toString().trim() == "") {
                    tilHeight.error = getString(R.string.err_no_height)
                    tvOptional2CC1.visibility = View.GONE
                } else {
                    tilHeight.error = null
                    tvOptional2CC1.visibility = View.VISIBLE
                }
                return true
            }

            8 -> {
                if (tietWeight.text.toString().trim() == "") {
                    tilWeight.error = getString(R.string.err_no_weight)
                    tvOptional2CC2.visibility = View.GONE
                } else {
                    tilWeight.error = null
                    tvOptional2CC2.visibility = View.VISIBLE
                }
                return true
            }

            9 -> {
                if (tietEyeColor.text.toString().trim() == "") {
                    tilEyeColor.error = getString(R.string.err_no_eye_colour)
                    tvOptional2CC3.visibility = View.GONE
                } else {
                    tilEyeColor.error = null
                    tvOptional2CC3.visibility = View.VISIBLE
                }
                return true
            }

            10 -> {
                if (tietRace.text.toString().trim() == "") {
                    tilRace.error = getString(R.string.err_no_race)
                    tvOptional2CC4.visibility = View.GONE
                } else {
                    tilRace.error = null
                    tvOptional2CC4.visibility = View.VISIBLE
                }
                return true
            }

            11 -> {
                if (tietHairColor.text.toString().trim() == "") {
                    tilHairColor.error = getString(R.string.err_no_hair)
                    tvOptional2CC5.visibility = View.GONE
                } else {
                    tilHairColor.error = null
                    tvOptional2CC5.visibility = View.VISIBLE
                }
                return true
            }

            else -> {
                return false
            }

        }

    }

    fun setUpFocusChangers(stageNumber: Int) {

        when (stageNumber) {

            0 -> {
                // Set-up for Stage One
                val tietCN = findViewById<TextInputEditText>(R.id.tietCharacterName)
                tietCN.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(0)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(0)
                    }
                }

                val tietBio = findViewById<TextInputEditText>(R.id.tietBiography)
                tietBio.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(1)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(1)
                    }
                }

                val tietYOB = findViewById<TextInputEditText>(R.id.tietBirthYear)
                tietYOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(2)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(2)
                    }
                }

                val tietPOB = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                tietPOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(3)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(3)
                    }
                }

                val tietCurrLoc = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                tietCurrLoc.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(4)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(4)
                    }
                }

                val tietBD = findViewById<TextInputEditText>(R.id.tietBirthDate)
                tietBD.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(6)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(6)
                    }
                }
            }

            1 -> {
                // Set-up for Stage Two
                val tietHeight = findViewById<TextInputEditText>(R.id.tietHeight)
                tietHeight.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(7)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(7)
                    }
                }

                val tietWeight = findViewById<TextInputEditText>(R.id.tietWeight)
                tietWeight.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(8)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(8)
                    }
                }

                val tietEyeColour = findViewById<TextInputEditText>(R.id.tietEyeColor)
                tietEyeColour.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(9)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(9)
                    }
                }

                val tietRace = findViewById<TextInputEditText>(R.id.tietRace)
                tietRace.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(10)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(10)
                    }
                }

                val tietHair = findViewById<TextInputEditText>(R.id.tietHairColor)
                tietHair.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(11)
                        Handler().removeCallbacksAndMessages(r)
                    } else {
                        delayError(11)
                    }
                }
            }

        }

    }

    private fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(e: Editable?) {
                afterTextChanged.invoke(e.toString())
            }

        })
    }

    fun setUpTextChangers(stageNumber: Int) {
        when (stageNumber) {

            0 -> {
                val tietNOC = findViewById<TextInputEditText>(R.id.tietCharacterName)
                tietNOC.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(0)
                    } else {
                        checkField(0)
                    }
                }

                val tietBio = findViewById<TextInputEditText>(R.id.tietBiography)
                tietBio.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(1)
                    } else {
                        checkField(1)
                    }
                }

                val tietYOB = findViewById<TextInputEditText>(R.id.tietBirthYear)
                tietYOB.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(2)
                    } else {
                        checkField(2)
                    }
                }

                val tietPOB = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                tietPOB.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(3)
                    } else {
                        checkField(3)
                    }
                }

                val tietCurLoc = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                tietCurLoc.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(4)
                    } else {
                        checkField(4)
                    }
                }

                val tietBDay = findViewById<TextInputEditText>(R.id.tietBirthDate)
                tietBDay.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(6)
                    } else {
                        checkField(6)
                    }
                }
            }

            1 -> {
                // Set-up for Stage Two
                val tietHeight = findViewById<TextInputEditText>(R.id.tietHeight)
                tietHeight.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(7)
                    } else {
                        checkField(7)
                    }
                }

                val tietWeight = findViewById<TextInputEditText>(R.id.tietWeight)
                tietWeight.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(8)
                    } else {
                        checkField(8)
                    }
                }

                val tietEyeColour = findViewById<TextInputEditText>(R.id.tietEyeColor)
                tietEyeColour.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(9)
                    } else {
                        checkField(9)
                    }
                }

                val tietRace = findViewById<TextInputEditText>(R.id.tietRace)
                tietRace.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(10)
                    } else {
                        checkField(10)
                    }
                }

                val tietHair = findViewById<TextInputEditText>(R.id.tietHairColor)
                tietHair.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(11)
                    } else {
                        checkField(11)
                    }
                }
            }

            else -> {
                Log.e("error", "Err : Non-Existent Stage for Text Changer")
            }

        }
    }

    fun createCharacter(view: View) {
        super.onBackPressed() // @TODO Modify this is not ideal
    }

}