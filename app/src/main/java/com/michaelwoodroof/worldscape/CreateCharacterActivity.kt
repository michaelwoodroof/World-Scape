package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.michaelwoodroof.worldscape.helper.*
import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS1
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS2
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS3
import com.michaelwoodroof.worldscape.ui.create_character.CreateCharacterFragmentS4
import com.michaelwoodroof.worldscape.ui.fragments.AddChipBottomDialogFragment
import com.michaelwoodroof.worldscape.ui.fragments.StatDialogFragment
import kotlinx.android.synthetic.main.activity_create_character.*
import kotlinx.android.synthetic.main.bottom_sheet_new_chip.*
import kotlinx.android.synthetic.main.default_toolbar.*
import kotlinx.android.synthetic.main.fragment_create_character_s1.*
import kotlinx.android.synthetic.main.fragment_create_character_s2.view.*
import kotlinx.android.synthetic.main.fragment_create_character_s3.*
import kotlinx.android.synthetic.main.fragment_stat_sheet.*

// @TODO Fix Animation on Link Places

class CreateCharacterActivity : AppCompatActivity() {

    var currentCharacter : MyCharacter? = null
    var isDialogLoaded = false
    lateinit var uriPointer : Uri
    private lateinit var bottomSheetFragment : AddChipBottomDialogFragment
    var r : Runnable = Runnable {}
    var h : Handler = Handler(Looper.getMainLooper())

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_character)

        SetStatusBar.create(this.window, this)

        // Set-Up Toolbar
        val tv = incToolbarCC.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.create_character_title)
        val btnMenu = incToolbarCC.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE
        val btnSettings = incToolbarCC.findViewById<ImageButton>(btnSettings.id)
        btnSettings.visibility = View.GONE
        val btnBack = incToolbarCC.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE

        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
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

        val v : View = findViewById(R.id.flCCMain)
        v.viewTreeObserver.addOnGlobalLayoutListener {
            if (flCCMain.tag != "S4") {
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

        // Set up Gradient Button
        SetGradient.assign(fabNext, this)

        val displayMetrics: DisplayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density.toInt()
        SetGradient.assign(fabCreateCC, this, dpWidth)

        // Set up Fragment
        val createCharacterFragment = CreateCharacterFragmentS1()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flCCMain, createCharacterFragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        backTasks()
        isDialogLoaded = false
        super.onBackPressed()
    }

    fun goBack(view: View) {
        backTasks()
        super.onBackPressed()
    }

    private fun backTasks() {
        if (!isDialogLoaded) {
            when (flCCMain.tag) {
                "S2" -> {
                    flCCMain.tag = "S1"
                    updateCharacter(1)
                }

                "S3" -> {
                    flCCMain.tag = "S2"
                    updateCharacter(2)
                }

                "S4" -> {
                    flCCMain.tag = "S3"
                    updateCharacter(3)

                    fabCreateCC.hide()

                    fabNext.show()
                }
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

    fun fillFields(stageNumber: Int) {
        when (stageNumber) {

            0 -> {
                // Only Update for Image as All other fields will be maintained through back stack
                if (currentCharacter?.hasImg == true) {
                    val cv = findViewById<MaterialCardView>(R.id.cvPreviewCC)
                    cv.visibility = View.VISIBLE
                    val img = findViewById<ImageView>(R.id.imgPreviewCC)
                    img.setImageURI(uriPointer)
                }
            }

            1 -> {
                val h: TextInputEditText = findViewById(R.id.tietHeight)
                h.text = currentCharacter?.height?.toEditable()

                val w = findViewById<TextInputEditText>(R.id.tietWeight)
                w.text = currentCharacter?.weight?.toEditable()

                val eye = findViewById<TextInputEditText>(R.id.tietEyeColor)
                eye.text = currentCharacter?.eyeColor?.toEditable()

                val race = findViewById<TextInputEditText>(R.id.tietRace)
                race.text = currentCharacter?.race?.toEditable()

                val hc = findViewById<TextInputEditText>(R.id.tietHairColor)
                hc.text = currentCharacter?.hairColor?.toEditable()

                val build = findViewById<TextInputEditText>(R.id.tietBuild)
                build.text = currentCharacter?.build?.toEditable()

                val marks = findViewById<TextInputEditText>(R.id.tietMarkings)
                marks.text = currentCharacter?.markings?.toEditable()

                val hs = findViewById<TextInputEditText>(R.id.tietHairStyle)
                hs.text = currentCharacter?.hairStyle?.toEditable()

                val cl: TextInputEditText = findViewById(R.id.tietClothingStyle)
                cl.text = currentCharacter?.clothingStyle?.toEditable()
            }

            2 -> {
                val cgp = findViewById<ChipGroup>(R.id.cgPositiveTraits)
                if (currentCharacter?.positiveTraits?.isNotEmpty() == true) {
                    for (child in currentCharacter!!.positiveTraits) {
                        val chip = Chip(this)
                        chip.setTextAppearance(R.style.ChipText)
                        chip.text = child
                        chip.setOnCloseIconClickListener {
                            cgp.removeView(chip)
                        }
                        cgp.addView(chip)
                    }
                }


                val cgn = findViewById<ChipGroup>(R.id.cgNegativeTraits)
                if (currentCharacter?.negativeTraits?.isNotEmpty() == true) {
                    for (child in currentCharacter!!.negativeTraits) {
                        val chip = Chip(this, null, R.attr.NegativeChipStyle)
                        chip.setTextAppearance(R.style.NegativeChipText)
                        chip.text = child
                        chip.setOnCloseIconClickListener {
                            cgn.removeView(chip)
                        }
                        cgn.addView(chip)
                    }
                }

                val cgi = findViewById<ChipGroup>(R.id.cgInterests)
                if (currentCharacter?.interests?.isNotEmpty() == true) {
                    for (child in currentCharacter!!.interests) {
                        val chip = Chip(this)
                        chip.setTextAppearance(R.style.ChipText)
                        chip.text = child
                        chip.setOnCloseIconClickListener {
                            cgi.removeView(chip)
                        }
                        cgi.addView(chip)
                    }
                }


                val cgf = findViewById<ChipGroup>(R.id.cgFears)
                if (currentCharacter?.fears?.isNotEmpty() == true) {
                    for (child in currentCharacter!!.fears) {
                        val chip = Chip(this, null, R.attr.NegativeChipStyle)
                        chip.setTextAppearance(R.style.NegativeChipText)
                        chip.text = child
                        chip.setOnCloseIconClickListener {
                            cgf.removeView(chip)
                        }
                        cgf.addView(chip)
                    }
                }

            }

            3 -> {

            }

            else -> {
                Log.e("Error", "ERR: NO SUCH METHOD")
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
                currentCharacter?.name = cn.text.toString()

                val bio = findViewById<TextInputEditText>(R.id.tietBiography)
                currentCharacter?.biography = bio.text.toString()

                val by = findViewById<TextInputEditText>(R.id.tietBirthYear)
                currentCharacter?.yearOfBirth = by.text.toString()

                val bd = findViewById<TextInputEditText>(R.id.tietBirthDate)
                currentCharacter?.birthday = bd.text.toString()

                val cl = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                currentCharacter?.currentLocation = cl.text.toString()

                val pob = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                currentCharacter?.placeOfBirth = pob.text.toString()

                val occ = findViewById<TextInputEditText>(R.id.tietOccupation)
                currentCharacter?.occupation = occ.text.toString()

                val img = findViewById<MaterialCardView>(R.id.cvPreviewCC)
                currentCharacter?.hasImg = img.visibility == View.VISIBLE
            }

            1 -> {
                val h = findViewById<TextInputEditText>(R.id.tietHeight)
                currentCharacter?.height = h.text.toString()

                val w = findViewById<TextInputEditText>(R.id.tietWeight)
                currentCharacter?.weight = w.text.toString()

                val eye = findViewById<TextInputEditText>(R.id.tietEyeColor)
                currentCharacter?.eyeColor = eye.text.toString()

                val race = findViewById<TextInputEditText>(R.id.tietRace)
                currentCharacter?.race = race.text.toString()

                val hc = findViewById<TextInputEditText>(R.id.tietHairColor)
                currentCharacter?.hairColor = hc.text.toString()

                val build = findViewById<TextInputEditText>(R.id.tietBuild)
                currentCharacter?.build = build.text.toString()

                val marks = findViewById<TextInputEditText>(R.id.tietMarkings)
                currentCharacter?.markings = marks.text.toString()

                val hs = findViewById<TextInputEditText>(R.id.tietHairStyle)
                currentCharacter?.hairStyle = hs.text.toString()

                val cl = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                currentCharacter?.clothingStyle = cl.text.toString()
            }

            2 -> {
                val cgp = findViewById<ChipGroup>(R.id.cgPositiveTraits)
                currentCharacter?.positiveTraits = ArrayList()
                for (chip in cgp.children) {
                    chip as Chip
                    (currentCharacter?.positiveTraits as ArrayList<String>).add(chip.text.toString())
                }

                val cgn = findViewById<ChipGroup>(R.id.cgNegativeTraits)
                currentCharacter?.negativeTraits = ArrayList()
                for (chip in cgn.children) {
                    chip as Chip
                    (currentCharacter?.negativeTraits as ArrayList<String>).add(chip.text.toString())
                }

                val cgi = findViewById<ChipGroup>(R.id.cgInterests)
                currentCharacter?.interests = ArrayList()
                for (chip in cgi.children) {
                    chip as Chip
                    (currentCharacter?.interests as ArrayList<String>).add(chip.text.toString())
                }

                val cgf = findViewById<ChipGroup>(R.id.cgFears)
                currentCharacter?.fears = ArrayList()
                for (chip in cgf.children) {
                    chip as Chip
                    (currentCharacter?.fears as ArrayList<String>).add(chip.text.toString())
                }
            }

            3 -> {

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
            if (checkFields(2)) {
                flCCMain.tag = "S4"

                val createCharacterFragment = CreateCharacterFragmentS4()
                val transaction = supportFragmentManager.beginTransaction()

                updateCharacter(2)

                transaction.replace(R.id.flCCMain, createCharacterFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                fabNext.hide()
                fabCreateCC.show()
            }
        }
    }

    private fun checkFields(stageNumber: Int) : Boolean {

        when (stageNumber) {

            0 -> {
                val c1 = checkField(100)
                val c2 = checkField(101)
                return if (c1 && c2) {
                    true
                } else {
                    checkField(102)
                    checkField(103)
                    checkField(104)
                    checkField(105)
                    checkField(106)
                    checkField(107)
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

        Log.d("TESTING", field.toString())

        try {

            // 1XX - Stage One Fields
            // 2XX - Stage Two Fields
            // 3XX - Stage Three Fields
            // 4XX - Stage Four Fields

            when (field) {

                100 -> {
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

                101 -> {
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

                102 -> {
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

                103 -> {
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

                104 -> {
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

                105 -> {
                    val x = findViewById<ImageView>(R.id.imgPreviewCC)

                    // Image Checking
                    if (x.visibility == View.VISIBLE) {
                        // @TODO Implement
                    } else {
                        // @TODO Implement
                    }
                    return true
                }

                106 -> {
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

                107 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietOccupation)
                    val xp = findViewById<TextInputLayout>(R.id.tilOccupation)
                    val o = findViewById<TextView>(R.id.tvOptionalCC5)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_job)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                200 -> {
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

                201 -> {
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

                202 -> {
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

                203 -> {
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

                204 -> {
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

                205 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietBuild)
                    val xp = findViewById<TextInputLayout>(R.id.tilBuild)
                    val o = findViewById<TextView>(R.id.tvOptional2CC6)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_build)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                206 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietMarkings)
                    val xp = findViewById<TextInputLayout>(R.id.tilMarkings)
                    val o = findViewById<TextView>(R.id.tvOptional2CC7)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_markings)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                207 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietHairStyle)
                    val xp = findViewById<TextInputLayout>(R.id.tilHairStyle)
                    val o = findViewById<TextView>(R.id.tvOptional2CC8)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_hair_style)
                        o.visibility = View.GONE
                    } else {
                        xp.error = null
                        o.visibility = View.VISIBLE
                    }
                    return true
                }

                208 -> {
                    val x = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                    val xp = findViewById<TextInputLayout>(R.id.tilClothingStyle)
                    val o = findViewById<TextView>(R.id.tvOptional2CC9)

                    if (x.text.toString().trim() == "") {
                        xp.error = getString(R.string.err_no_clothing_style)
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
                    val a = findViewById<TextInputLayout>(R.id.tilBirthDate)
                    a.error = null

                    val b = findViewById<TextInputLayout>(R.id.tilCurrentLocation)
                    b.error = null
                    b.clearAnimation()

                    val c = findViewById<TextInputLayout>(R.id.tilPlaceOfBirth)
                    c.error = null
                    c.clearAnimation()

                    val d = findViewById<TextInputLayout>(R.id.tilOccupation)
                    d.error = null

                    val x = findViewById<TextInputLayout>(R.id.tilCharacterName)
                    x.error = null

                    val y = findViewById<TextInputLayout>(R.id.tilBiography)
                    y.error = null

                    val z = findViewById<TextInputLayout>(R.id.tilBirthYear)
                    z.error = null

                }

                1 -> {
                    val h = findViewById<TextInputLayout>(R.id.tilHeight)
                    h.error = null

                    val w = findViewById<TextInputLayout>(R.id.tilWeight)
                    w.error = null

                    val eye = findViewById<TextInputLayout>(R.id.tilEyeColor)
                    eye.error = null

                    val race = findViewById<TextInputLayout>(R.id.tilRace)
                    race.error = null

                    val hc = findViewById<TextInputLayout>(R.id.tilHairColor)
                    hc.error = null

                    val build = findViewById<TextInputLayout>(R.id.tilBuild)
                    build.error = null

                    val marks = findViewById<TextInputLayout>(R.id.tilMarkings)
                    marks.error = null

                    val hs = findViewById<TextInputLayout>(R.id.tilHairStyle)
                    hs.error = null

                    val cl = findViewById<TextInputLayout>(R.id.tilClothingStyle)
                    cl.error = null

                    val op9 = findViewById<TextView>(R.id.tvOptional2CC9)
                    op9.visibility = View.VISIBLE
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
                        checkField(100)
                    } else {
                        delayError(100)
                    }
                }

                val tietBio = findViewById<TextInputEditText>(R.id.tietBiography)
                tietBio.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(101)
                    } else {
                        delayError(101)
                    }
                }

                val tietYOB = findViewById<TextInputEditText>(R.id.tietBirthYear)
                tietYOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(102)
                    } else {
                        delayError(102)
                    }
                }

                val tietPOB = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                tietPOB.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(103)
                    } else {
                        delayError(103)
                    }
                }

                val tietCurrLoc = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                tietCurrLoc.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(104)
                    } else {
                        delayError(104)
                    }
                }

                val tietBD = findViewById<TextInputEditText>(R.id.tietBirthDate)
                tietBD.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
                    if (!focus) {
                        checkField(106)
                    } else {
                        delayError(106)
                    }
                }

                val tietOcc = findViewById<TextInputEditText>(R.id.tietOccupation)
                tietOcc.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(107)
                    } else {
                        delayError(107)
                    }
                }
            }

            1 -> {
                // Set-up for Stage Two
                val tietHeight = findViewById<TextInputEditText>(R.id.tietHeight)
                tietHeight.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(200)
                    } else {
                        delayError(200)
                    }
                }

                val tietWeight = findViewById<TextInputEditText>(R.id.tietWeight)
                tietWeight.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(201)
                    } else {
                        delayError(201)
                    }
                }

                val tietEyeColour = findViewById<TextInputEditText>(R.id.tietEyeColor)
                tietEyeColour.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(202)
                    } else {
                        delayError(202)
                    }
                }

                val tietRace = findViewById<TextInputEditText>(R.id.tietRace)
                tietRace.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(203)
                    } else {
                        delayError(203)
                    }
                }

                val tietHair = findViewById<TextInputEditText>(R.id.tietHairColor)
                tietHair.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(204)
                    } else {
                        delayError(204)
                    }
                }

                val tietBuild = findViewById<TextInputEditText>(R.id.tietBuild)
                tietBuild.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(205)
                    } else {
                        delayError(205)
                    }
                }

                val tietMarkings = findViewById<TextInputEditText>(R.id.tietMarkings)
                tietMarkings.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(206)
                    } else {
                        delayError(206)
                    }
                }

                val tietHairStyle = findViewById<TextInputEditText>(R.id.tietHairStyle)
                tietHairStyle.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(207)
                    } else {
                        delayError(207)
                    }
                }

                val tietClothingStyle = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                tietClothingStyle.onFocusChangeListener = View.OnFocusChangeListener { _ : View, focus ->
                    if (!focus) {
                        checkField(208)
                    } else {
                        delayError(208)
                    }
                }
            }

            2 -> {

            }

            3 -> {

            }

            else -> {
                Log.e("error", "Err : Non-Existent Stage for Focus Changer")
            }

        }

    }

    fun setUpTextChangers(stageNumber: Int) {

        when (stageNumber) {

            0 -> {
                val tietNOC = findViewById<TextInputEditText>(R.id.tietCharacterName)
                tietNOC.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(100)
                    } else {
                        checkField(100)
                    }
                }

                val tietBio = findViewById<TextInputEditText>(R.id.tietBiography)
                tietBio.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(101)
                    } else {
                        checkField(101)
                    }
                }

                val tietYOB = findViewById<TextInputEditText>(R.id.tietBirthYear)
                tietYOB.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(102)
                    } else {
                        checkField(102)
                    }
                }

                val tietPOB = findViewById<TextInputEditText>(R.id.tietPlaceOfBirth)
                tietPOB.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(103)
                    } else {
                        checkField(103)
                    }
                }

                val tietCurLoc = findViewById<TextInputEditText>(R.id.tietCurrentLocation)
                tietCurLoc.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(104)
                    } else {
                        checkField(104)
                    }
                }

                val tietBDay = findViewById<TextInputEditText>(R.id.tietBirthDate)
                tietBDay.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(106)
                    } else {
                        checkField(106)
                    }
                }

                val tietOcc = findViewById<TextInputEditText>(R.id.tietOccupation)
                tietOcc.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(107)
                    } else {
                        checkField(107)
                    }
                }

            }

            1 -> {
                // Set-up for Stage Two
                val tietHeight = findViewById<TextInputEditText>(R.id.tietHeight)
                tietHeight.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(200)
                    } else {
                        checkField(200)
                    }
                }

                val tietWeight = findViewById<TextInputEditText>(R.id.tietWeight)
                tietWeight.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(201)
                    } else {
                        checkField(201)
                    }
                }

                val tietEyeColour = findViewById<TextInputEditText>(R.id.tietEyeColor)
                tietEyeColour.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(202)
                    } else {
                        checkField(202)
                    }
                }

                val tietRace = findViewById<TextInputEditText>(R.id.tietRace)
                tietRace.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(203)
                    } else {
                        checkField(203)
                    }
                }

                val tietHair = findViewById<TextInputEditText>(R.id.tietHairColor)
                tietHair.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(204)
                    } else {
                        checkField(204)
                    }
                }

                val tietBuild = findViewById<TextInputEditText>(R.id.tietBuild)
                tietBuild.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(205)
                    } else {
                        checkField(205)
                    }
                }

                val tietMarkings = findViewById<TextInputEditText>(R.id.tietMarkings)
                tietMarkings.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(206)
                    } else {
                        checkField(206)
                    }
                }

                val tietHairStyle = findViewById<TextInputEditText>(R.id.tietHairStyle)
                tietHairStyle.afterTextChanged {
                    if (it.isEmpty()) {
                        delayError(207)
                    } else {
                        checkField(207)
                    }
                }

                val tietClothingStyle = findViewById<TextInputEditText>(R.id.tietClothingStyle)
                tietClothingStyle.afterTextChanged {
                    if (it.isEmpty() and tietClothingStyle.hasFocus()) {
                        delayError(208)
                    } else {
                        checkField(208)
                    }
                }
            }

            2 -> {

            }

            else -> {
                Log.e("error", "Err : Non-Existent Stage for Text Changer")
            }

        }
    }

    fun addChip(view: View) {
        // Show Bottom Sheet on Click
        bottomSheetFragment = AddChipBottomDialogFragment()
        when (view.tag) {

            "0" -> {
                bottomSheetFragment.isPos = true
                bottomSheetFragment.titleOfFrag = getString(R.string.name_of_trait)
                bottomSheetFragment.buttonText = getString(R.string.add_trait)
            }

            "1" -> {
                bottomSheetFragment.isPos = false
                bottomSheetFragment.titleOfFrag = getString(R.string.name_of_trait)
                bottomSheetFragment.buttonText = getString(R.string.add_trait)
            }

            "2" -> {
                bottomSheetFragment.isPos = true
                bottomSheetFragment.titleOfFrag = getString(R.string.name_of_interest)
                bottomSheetFragment.buttonText = getString(R.string.add_interest)
            }

            "3" -> {
                bottomSheetFragment.isPos = false
                bottomSheetFragment.titleOfFrag = getString(R.string.name_of_fear)
                bottomSheetFragment.buttonText = getString(R.string.add_fear)
            }

        }
        bottomSheetFragment.show(supportFragmentManager, "bottom_sheet_fragment")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun addTrait(view: View) {
        // Add Chip to ChipGroup and Set Attributes
        val chip : Chip
        if (bottomSheetFragment.isPos) {
            chip = Chip(this)
            chip.setTextAppearance(R.style.ChipText)
        } else {
            chip = Chip(this, null, R.attr.NegativeChipStyle)
            chip.setTextAppearance(R.style.NegativeChipText)
        }

        chip.text = bottomSheetFragment.tietNewChip.text.toString()

        var parent : ChipGroup = this.findViewById(R.id.cgPositiveTraits)
        when (bottomSheetFragment.titleOfFrag) {

            getString(R.string.name_of_trait) -> {
                parent = if (bottomSheetFragment.isPos) {
                    this.findViewById(R.id.cgPositiveTraits)
                } else {
                    this.findViewById(R.id.cgNegativeTraits)
                }
            }

            getString(R.string.name_of_interest) -> {
                parent = this.findViewById(R.id.cgInterests)
            }

            getString(R.string.name_of_fear) -> {
                parent = this.findViewById(R.id.cgFears)
            }

        }

        parent.addView(chip)

        chip.setOnCloseIconClickListener {
            parent.removeView(chip)
        }

        // Dismiss Fragment
        try {
            bottomSheetFragment.dismiss()
        } catch (e : Exception){
            Log.e("error", e.toString())
        }

    }

    fun createCharacter(view: View) {
        val mf = ManageFiles(this)
        // wuid is the World's uid
        currentCharacter?.uid = mf.generateUUID()
        currentCharacter?.wuid = intent.getStringExtra("uid").toString()
        if (currentCharacter?.let { mf.saveCharacter(it, intent.getStringExtra("uid").toString()) } == true) {
            // Save Image for Character
            if (currentCharacter?.hasImg == true && this::uriPointer.isInitialized) {
                currentCharacter?.uid?.let {
                    mf.saveCharacterImage(
                        it, intent.getStringExtra("uid").toString(), uriPointer,
                        this.contentResolver)
                }
            }
        } else {
            Snackbar.make(findViewById(R.id.colMainCC), resources.getString(R.string.err_save_character), Snackbar.LENGTH_INDEFINITE).setAction(R.string.action_text) {
                createCharacter(findViewById(R.id.fabCreateCC))
            }.show()
        }

        val fragmentManager = supportFragmentManager

        for (i in 0..fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }

        super.onBackPressed()

        // Load Intent
        val intent = Intent(this, WorldDetailActivity::class.java)
        this.startActivity(intent)
    }

    fun loadStatDialog(view : View) {
        if (!isDialogLoaded) {
            isDialogLoaded = true
            val fragmentManager = supportFragmentManager
            val statFragment = StatDialogFragment()
            fragmentManager.beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, statFragment).commit()
        }
    }

    fun popStack() {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()

        val fab = findViewById<ExtendedFloatingActionButton>(R.id.fabNext)
        fab.visibility = View.GONE
    }

}