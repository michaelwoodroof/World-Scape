package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS1
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS2
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS3
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.default_toolbar.*
import kotlinx.android.synthetic.main.fragment_create_character_s1.*
import java.lang.Exception

// @TODO Fix Animation on Link Places

class CreateCharacterActivity : AppCompatActivity() {

    var currentCharacter : CharacterContent.CharacterItem =
        CharacterContent.CharacterItem("", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    var r : Runnable = Runnable {}
    var h : Handler = Handler(Looper.getMainLooper())

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

    override fun onBackPressed() {
        backTasks()
        super.onBackPressed()
    }

    fun goBack(view: View) {
        backTasks()
        super.onBackPressed()
    }

    private fun backTasks() {
        when (flCCMain.tag) {
            "S2" -> {
                flCCMain.tag = "S1"
                updateCharacter(1)
            }

            "S3" -> {
                flCCMain.tag = "S2"
            }
        }
    }

    // Gets Reset on Text Changed
    private fun delayError(field : Int) {
        // Ensures Reset before Attempting Timeout
        h.removeCallbacksAndMessages(null)
        r = Runnable {
            checkField(field)
        }
        h.postDelayed(r, 2000)
    }

    private fun String.toEditable() : Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

    fun fillFields(stageNumber: Int) {
        when (stageNumber) {

            1 -> {
                val h = findViewById<TextInputEditText>(R.id.tietHeight)
                h.text = currentCharacter.height?.toEditable()

                val w = findViewById<TextInputEditText>(R.id.tietWeight)
                w.text = currentCharacter.weight?.toEditable()

                val eye = findViewById<TextInputEditText>(R.id.tietEyeColor)
                eye.text = currentCharacter.eyeColor?.toEditable()

                val race = findViewById<TextInputEditText>(R.id.tietRace)
                race.text = currentCharacter.race?.toEditable()

                val hc = findViewById<TextInputEditText>(R.id.tietHairColor)
                hc.text = currentCharacter.hairColor?.toEditable()

                val build = findViewById<TextInputEditText>(R.id.tietBuild)
                build.text = currentCharacter.build?.toEditable()

                val marks = findViewById<TextInputEditText>(R.id.tietMarkings)
                marks.text = currentCharacter.markings?.toEditable()

                val hs = findViewById<TextInputEditText>(R.id.tietHairStyle)
                hs.text = currentCharacter.hairStyle?.toEditable()

                val cl = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                cl.text = currentCharacter.clothingStyle?.toEditable()
            }

            2 -> {

            }

        }
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

    private fun updateCharacter(stageNumber: Int) {
        when (stageNumber) {
            0 -> {
                val cn = findViewById<TextInputEditText>(R.id.tietCharacterName)
                currentCharacter.name = cn.text.toString()

                val bio = findViewById<TextInputEditText>(R.id.tietBiography)
                currentCharacter.biography = bio.text.toString()

                val by = findViewById<TextInputEditText>(R.id.tietBirthYear)
                currentCharacter.yearOfBirth = by.text.toString()

                val bd = findViewById<TextInputEditText>(R.id.tietBirthDate)
                currentCharacter.birthday = bd.text.toString()

                val cl = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                currentCharacter.currentLocation = cl.text.toString()

                val pob = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                currentCharacter.placeOfBirth = pob.text.toString()

                val img = findViewById<MaterialCardView>(R.id.cvPreviewCC)
                currentCharacter.hasImg = img.visibility == View.VISIBLE
            }

            1 -> {
                val h = findViewById<TextInputEditText>(R.id.tietHeight)
                currentCharacter.height = h.text.toString()

                val w = findViewById<TextInputEditText>(R.id.tietWeight)
                currentCharacter.weight = w.text.toString()

                val eye = findViewById<TextInputEditText>(R.id.tietEyeColor)
                currentCharacter.eyeColor = eye.text.toString()

                val race = findViewById<TextInputEditText>(R.id.tietRace)
                currentCharacter.race = race.text.toString()

                val hc = findViewById<TextInputEditText>(R.id.tietHairColor)
                currentCharacter.hairColor = hc.text.toString()

                val build = findViewById<TextInputEditText>(R.id.tietBuild)
                currentCharacter.build = build.text.toString()

                val marks = findViewById<TextInputEditText>(R.id.tietMarkings)
                currentCharacter.markings = marks.text.toString()

                val hs = findViewById<TextInputEditText>(R.id.tietHairStyle)
                currentCharacter.hairStyle = hs.text.toString()

                val cl = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                currentCharacter.clothingStyle = cl.text.toString()
            }

            2 -> {

            }
        }
    }

    fun stepForward(view : View) {
        if (flCCMain.tag == "S1") {
            // Update Frag if Condition passed
            if (checkFields(0)) {
                flCCMain.tag = "S2"
                val createCharacterFragment = CreateCharacterFragmentS2()
                val transaction = supportFragmentManager.beginTransaction()

                updateCharacter(0)

                transaction.replace(R.id.flCCMain, createCharacterFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else if (flCCMain.tag == "S2") {
            // Update Frag if Condition passes
            if (checkFields(1)) {
                flCCMain.tag = "S3"
                val createCharacterFragment = CreateCharacterFragmentS3()
                val transaction = supportFragmentManager.beginTransaction()

                updateCharacter(1)

                transaction.replace(R.id.flCCMain, createCharacterFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else if (flCCMain.tag == "S3") {
//            if (checkFields(2)) {
//                flCCMain.tag = "S4"
//            }
        }
    }

    private fun checkFields(stageNumber: Int) : Boolean {

        when (stageNumber) {

            0 -> {
                val c1 = checkField(0)
                val c2 = checkField(1)
                return if (c1 && c2) {
                    true
                } else {
                    checkField(2)
                    checkField(3)
                    checkField(4)
                    checkField(5)
                    checkField(6)
                    false
                }
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
        h.removeCallbacksAndMessages(null)
        try {
            when (field) {

                0 -> {
                    val cn = findViewById<TextInputEditText>(R.id.tietCharacterName)
                    val cnp = findViewById<TextInputLayout>(R.id.tilCharacterName)

                    return if (cn.text.toString().trim() == "") {
                        cnp.error = getString(R.string.err_no_cc_name)
                        false
                    } else {
                        cnp.error = null
                        true
                    }
                }

                1 -> {
                    val bio = findViewById<TextInputEditText>(R.id.tietBiography)
                    val biop = findViewById<TextInputLayout>(R.id.tilBiography)

                    return if (bio.text.toString().trim() == "") {
                        biop.error = getString(R.string.err_no_bio)
                        false
                    } else {
                        biop.error = null
                        true
                    }
                }

                2 -> {
                    val by = findViewById<TextInputEditText>(R.id.tietBirthYear)
                    val byp = findViewById<TextInputLayout>(R.id.tilBirthYear)
                    val o = findViewById<TextView>(R.id.tvOptionalCC1)

                    if (by.text.toString().trim() == "") {
                        byp.error = getString(R.string.err_no_by)
                        o.visibility = View.GONE
                    } else {
                        byp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                3 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                    val xp = findViewById<TextInputLayout>(R.id.tilPlaceOfBirth)
                    val btn = findViewById<ImageButton>(R.id.btnLinkPlace)
                    val o = findViewById<TextView>(R.id.tvOptionalCC4)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_pob)
                        o.visibility = View.GONE
                        if (btn.tag != "err") {
                            when (val drawable = btn.drawable) {
                                is AnimatedVectorDrawable -> {
                                    btn.tag = "err"
                                    drawable.start()
                                }
                            }
                        }
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                        if (btn.tag == "err") {
                            btn.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.warning_to_link,
                                    null
                                )
                            )
                            when (val drawable = btn.drawable) {
                                is AnimatedVectorDrawable -> {
                                    btn.tag = "ne"
                                    drawable.start()
                                }
                            }
                        }
                    }
                    return true
                }

                4 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                    val xp = findViewById<TextInputLayout>(R.id.tilCurrentLocation)
                    val btn = findViewById<ImageButton>(R.id.btnLinkCurrentLoc)
                    val o = findViewById<TextView>(R.id.tvOptionalCC3)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_cl)
                        o.visibility = View.GONE
                        if (btn.tag != "err") {
                            when (val drawable = btn.drawable) {
                                is AnimatedVectorDrawable -> {
                                    btn.tag = "err"
                                    drawable.start()
                                }
                            }
                        }
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                        if (btn.tag == "err") {
                            btn.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.warning_to_link,
                                    null
                                )
                            )
                            when (val drawable = btn.drawable) {
                                is AnimatedVectorDrawable -> {
                                    btn.tag = "ne"
                                    drawable.start()
                                }
                            }
                        }
                    }
                    return true
                }

                5 -> {
                    val x = findViewById<ImageView>(R.id.imgPreviewCC)

                    // Image Checking
                    if (x.visibility == View.VISIBLE) {
                        // @TODO Implement
                    } else {
                        // @TODO Implement
                    }
                    return true
                }

                6 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietBirthDate)
                    val xp = findViewById<TextInputLayout>(R.id.tilBirthDate)
                    val o = findViewById<TextView>(R.id.tvOptionalCC2)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_bday)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                7 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietHeight)
                    val xp = findViewById<TextInputLayout>(R.id.tilHeight)
                    val o = findViewById<TextView>(R.id.tvOptional2CC1)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_height)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                8 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietWeight)
                    val xp = findViewById<TextInputLayout>(R.id.tilWeight)
                    val o = findViewById<TextView>(R.id.tvOptional2CC2)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_weight)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                9 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietEyeColor)
                    val xp = findViewById<TextInputLayout>(R.id.tilEyeColor)
                    val o = findViewById<TextView>(R.id.tvOptional2CC3)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_eye_colour)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                10 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietRace)
                    val xp = findViewById<TextInputLayout>(R.id.tilRace)
                    val o = findViewById<TextView>(R.id.tvOptional2CC4)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_race)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                11 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietHairColor)
                    val xp = findViewById<TextInputLayout>(R.id.tilHairColor)
                    val o = findViewById<TextView>(R.id.tvOptional2CC5)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                12 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietBuild)
                    val xp = findViewById<TextInputLayout>(R.id.tilBuild)
                    val o = findViewById<TextView>(R.id.tvOptional2CC6)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                13 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietMarkings)
                    val xp = findViewById<TextInputLayout>(R.id.tilMarkings)
                    val o = findViewById<TextView>(R.id.tvOptional2CC7)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                14 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietHairStyle)
                    val xp = findViewById<TextInputLayout>(R.id.tilHairStyle)
                    val o = findViewById<TextView>(R.id.tvOptional2CC8)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                15 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                    val xp = findViewById<TextInputLayout>(R.id.tilClothingStyle)
                    val o = findViewById<TextView>(R.id.tvOptional2CC9)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                else -> {
                    return false
                }

            }
        } catch (e : Exception) {
            Log.e("error", e.toString())
            return false
        }
    }

    fun resetFields(stageNumber: Int) {
        try {
            when (stageNumber) {
                0 -> {
                    val x = findViewById<TextInputLayout>(R.id.tilCharacterName)
                    x.error = null

                    val y = findViewById<TextInputLayout>(R.id.tilBiography)
                    y.error = null

                    val z = findViewById<TextInputLayout>(R.id.tilBirthYear)
                    z.error = null

                    val a = findViewById<TextInputLayout>(R.id.tilBirthDate)
                    a.error = null

                    val b = findViewById<TextInputLayout>(R.id.tilCurrentLocation)
                    b.error = null
                    b.clearFocus()
                    b.clearAnimation()

                    val c = findViewById<TextInputLayout>(R.id.tilPlaceOfBirth)
                    c.error = null
                    c.clearFocus()
                    c.clearAnimation()
                }

                1 -> {

                }

                2 -> {

                }
            }
        } catch (e : Exception) {
            Log.e("error", e.toString())
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
                        h.removeCallbacksAndMessages(null)
                    } else {
                        delayError(0)
                    }
                }

                val tietBio = findViewById<TextInputEditText>(R.id.tietBiography)
                tietBio.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(1)
                        h.removeCallbacksAndMessages(null)
                    } else {
                        delayError(1)
                    }
                }

                val tietYOB = findViewById<TextInputEditText>(R.id.tietBirthYear)
                tietYOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(2)
                        h.removeCallbacksAndMessages(null)
                    } else {
                        delayError(2)
                    }
                }

                val tietPOB = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                tietPOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(3)
                        h.removeCallbacksAndMessages(null)
                    } else {
                        delayError(3)
                    }
                }

                val tietCurrLoc = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                tietCurrLoc.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(4)
                        h.removeCallbacksAndMessages(null)
                    } else {
                        delayError(4)
                    }
                }

                val tietBD = findViewById<TextInputEditText>(R.id.tietBirthDate)
                tietBD.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(6)
                        h.removeCallbacksAndMessages(null)
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
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(7)
                    }
                }

                val tietWeight = findViewById<TextInputEditText>(R.id.tietWeight)
                tietWeight.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(8)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(8)
                    }
                }

                val tietEyeColour = findViewById<TextInputEditText>(R.id.tietEyeColor)
                tietEyeColour.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(9)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(9)
                    }
                }

                val tietRace = findViewById<TextInputEditText>(R.id.tietRace)
                tietRace.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(10)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(10)
                    }
                }

                val tietHair = findViewById<TextInputEditText>(R.id.tietHairColor)
                tietHair.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(11)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(11)
                    }
                }

                val tietBuild = findViewById<TextInputEditText>(R.id.tietBuild)
                tietBuild.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(12)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(12)
                    }
                }

                val tietMarkings = findViewById<TextInputEditText>(R.id.tietMarkings)
                tietMarkings.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(13)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(13)
                    }
                }

                val tietHairStyle = findViewById<TextInputEditText>(R.id.tietHairStyle)
                tietHairStyle.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(14)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(14)
                    }
                }

                val tietClothingStyle = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                tietClothingStyle.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(15)
                        h.removeCallbacksAndMessages(r)
                    } else {
                        delayError(15)
                    }
                }
            }

            else -> {
                Log.e("error", "Err : Non-Existent Stage for Focus Changer")
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

                val tietBuild = findViewById<TextInputEditText>(R.id.tietBuild)
                tietBuild.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(12)
                    } else {
                        checkField(12)
                    }
                }

                val tietMarkings = findViewById<TextInputEditText>(R.id.tietMarkings)
                tietMarkings.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(13)
                    } else {
                        checkField(13)
                    }
                }

                val tietHairStyle = findViewById<TextInputEditText>(R.id.tietHairStyle)
                tietHairStyle.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(14)
                    } else {
                        checkField(14)
                    }
                }

                val tietClothingStyle = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                tietClothingStyle.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(15)
                    } else {
                        checkField(15)
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