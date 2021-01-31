package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.michaelwoodroof.worldscape.helper.*
import com.michaelwoodroof.worldscape.structure.World
import kotlinx.android.synthetic.main.activity_create_world.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CreateWorldActivity : AppCompatActivity() {

    lateinit var uriPointer : Uri
    lateinit var r : Runnable

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_world)

        SetStatusBar.create(this.window, this)

        val displayMetrics: DisplayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density.toInt()

        SetGradient.assign(btnCreate, this, dpWidth)

        // Set-Up Toolbar
        val tv = incToolbarCW.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.create_world_title)
        val btnMenu = incToolbarCW.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE
        val btnSettings = incToolbarCW.findViewById<ImageButton>(btnSettings.id)
        btnSettings.visibility = View.GONE
        val btnBack = incToolbarCW.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE

        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion_left, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_left, null) as AnimatedVectorDrawable
            )
        })

        // Set-Up DropDown
        val genreTypes = resources.getStringArray(R.array.genres)

        val adapter = ArrayAdapter(baseContext, R.layout.dropdown_menu_popup_item, genreTypes)
        ddGenre.setAdapter(adapter)

        addAnimation()
    }

    override fun onStart() {
        super.onStart()
        setUpFocusChangers()
        setUpTextChangers()
        // Init Runnable
        r = Runnable {}
    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun addWorld(view : View) {

        if (checkFields()) {
            // Captures Data from Activity
            val title = tietWorld.text.toString()
            val desc = tietDesc.text.toString()

            var img = false
            if (imgPreview.tag == "hasImage") {
                img = true
            }

            val mf = ManageFiles(this)
            val ed = ddGenre.text
            val uid = mf.generateUUID()

            mf.createFolderStructure(uid, this)

            // Attempt to Save World Image @TODO Review
            if (img && this::uriPointer.isInitialized) {
                mf.saveWorldImage(uid, uriPointer, this.contentResolver)
            }

            val world = World(title, desc, ed.toString(), img, "#ffffff", uid)

            if (mf.saveWorld(world)) {
                super.onBackPressed()
            } else {
                Snackbar.make(findViewById<CoordinatorLayout>(R.id.colMainCW), resources.getString(R.string.err_save_world), Snackbar.LENGTH_INDEFINITE).setAction(R.string.action_text) {
                    addWorld(findViewById<Button>(R.id.btnCreate))
                }.show()
            }
        }

    }

    private fun setUpTextChangers() {
        val tietWorld = findViewById<TextInputEditText>(R.id.tietWorld)
        tietWorld.afterTextChanged {
            if (it.isEmpty()) {
                delayError(0)
            } else {
                checkField(0)
            }
        }

        val tietDesc = findViewById<TextInputEditText>(R.id.tietDesc)
        tietDesc.afterTextChanged {
            if (it.isEmpty()) {
                delayError(1)
            } else {
                checkField(1)
            }
        }

        val ddGenre = findViewById<AutoCompleteTextView>(R.id.ddGenre)
        ddGenre.afterTextChanged {
            if (it.isEmpty()) {
                delayError(2)
            } else {
                checkField(2)
            }
        }

    }

    private fun delayError(field : Int) {
        // Ensures Reset before Attempting Timeout
        Handler(Looper.getMainLooper()).removeCallbacksAndMessages(r)
        r = Runnable {
            checkField(field)
        }
        Handler(Looper.getMainLooper()).postDelayed(r, 2000)
    }

    private fun checkField(field : Int) : Boolean {
        when (field) {

            0 -> {
                return if (tietWorld.text.toString().trim() == "") {
                    // Highlight Field to Indicate Error
                    tilWorld.error = getString(R.string.err_world)
                    false
                } else {
                    // Reset Field -- To Ensure Error isn't Showing
                    tilWorld.error = null
                    true
                }
            }

            1 -> {
                return if (tietDesc.text.toString().trim() == "") {
                    tilDesc.error = getString(R.string.err_desc)
                    false
                } else {
                    tilDesc.error = null
                    true
                }
            }

            2 -> {
                return if (ddGenre.text.toString().trim() == "") {
                    txtiGenre.error = getString(R.string.err_genre)
                    false
                } else {
                    txtiGenre.error = null
                    true
                }
            }

            3 -> {
                if (imgPreview.tag == "NT") {
                    tvWarning.visibility = View.VISIBLE
                    imgWarning.visibility = View.VISIBLE
                } else {
                    tvWarning.visibility = View.GONE
                    imgWarning.visibility = View.GONE
                }
                return true
            }

            else -> {
                return false
            }

        }
    }

    private fun checkFields() : Boolean {
        val t1 = checkField(0)
        val t2 = checkField(1)
        val t3 = checkField(2)
        val t4 = checkField(3)
        return t1 && t2 && t3 && t4
    }

    private fun addAnimation() {
        findViewById<Button>(R.id.fabPickImage).setOnClickListener {
            // Only Animate if Condition is Met

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ("image/*")

            // Mime Types
            val acceptedTypes = ArrayList<String>()
            acceptedTypes.add("image/png")
            acceptedTypes.add("image/jpg")
            acceptedTypes.add("image/jpeg")

            intent.putExtra(Intent.EXTRA_MIME_TYPES, acceptedTypes)
            startActivityForResult(intent, MEDIA_PICK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MEDIA_PICK_CODE) {
                val selectedImage = data?.data
                if (selectedImage != null) {
                    imgPreview.setImageURI(selectedImage)
                    uriPointer = selectedImage
                    imgPreview.tag = "hasImage"
                    animatePickImage()
                }
            }
        }
    }

    private fun setUpFocusChangers() {

        val tietWorld = findViewById<EditText>(R.id.tietWorld)

        tietWorld.onFocusChangeListener = View.OnFocusChangeListener{ _: View, focus ->
            if (!focus) {
                checkField(0)
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(r)
            } else {
                delayError(0)
            }
        }

        val tietDesc = findViewById<EditText>(R.id.tietDesc)

        tietDesc.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
            if (!focus) {
                checkField(1)
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(r)
            } else {
                delayError(1)
            }
        }

        val ddGenre = findViewById<AutoCompleteTextView>(R.id.ddGenre)

        ddGenre.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
            if (!focus) {
                checkField(2)
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(r)
            } else {
                delayError(2)
            }
        }

    }

    private fun animatePickImage() {
        val root = clMainCW

        val c = ConstraintSet()
        c.clone(this, R.layout.activity_create_world_alt)

        if (fabPickImage.tag != "run") {
            val dur = ChangeBounds()
            dur.duration = 300

            val r1 = Runnable {
                TransitionManager.beginDelayedTransition(root, dur)
                c.applyTo(root)
            }

            val r2 = Runnable {
                // Convert to Circle
                fabPickImage.shrink()
            }

            val r = Runnable {}

            r.run {
                Handler(Looper.getMainLooper()).postDelayed(r1, 100)
                Handler(Looper.getMainLooper()).postDelayed(r2, 400)
            }

            fabPickImage.tag = "run"
        }
    }

}