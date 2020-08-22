package com.michaelwoodroof.worldscape

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.michaelwoodroof.worldscape.helper.ManageFiles
import kotlinx.android.synthetic.main.activity_create_world.*
import kotlinx.android.synthetic.main.default_toolbar.*

class CreateWorldActivity : AppCompatActivity() {

    lateinit var uriPointer : Uri

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_world)

        // Set-Up Toolbar
        val tv = incToolbarCW.findViewById<TextView>(tvTitle.id)
        tv.text = getString(R.string.create_world_title)
        val btnMenu = incToolbarCW.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE
        val btnSettings = incToolbarCW.findViewById<ImageButton>(btnSettings.id)
        btnSettings.visibility = View.GONE
        val btnBack = incToolbarCW.findViewById<ImageButton>(btnBack.id)
        btnBack.visibility = View.VISIBLE

        // Set-Up DropDown
        val genreTypes = resources.getStringArray(R.array.genres)

        val adapter = ArrayAdapter(baseContext, R.layout.dropdown_menu_popup_item, genreTypes)
        ddGenre.setAdapter(adapter)

        setUpFocusChangers()
        addAnimation()
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

            // Attempt to Save World Image
            if (img && this::uriPointer.isInitialized) {
                mf.saveWorldImage(uid, uriPointer, this.contentResolver)
            }

            if (mf.saveWorld(title, desc, ed.toString(), img, "#ffffff", uid)) {
                super.onBackPressed()
            } else {
                Snackbar.make(findViewById<View>(R.id.colMainCW), resources.getString(R.string.err_save_world), Snackbar.LENGTH_INDEFINITE).setAction(R.string.action_text) {
                    addWorld(findViewById<View>(R.id.btnCreate))
                }.show()
            }
        }

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
                    tilWorld.error = ""
                    true
                }

            }

            1 -> {
                return if (tietDesc.text.toString().trim() == "") {
                    tilDesc.error = getString(R.string.err_desc)
                    false
                } else {
                    tilDesc.error = ""
                    true
                }
            }

            2 -> {
                return if (ddGenre.text.toString().trim() != "") {
                    txtiGenre.error = ""
                    true
                } else {
                    txtiGenre.error = getString(R.string.err_genre)
                    false
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
        findViewById<Button>(R.id.btnPickImage).setOnClickListener {
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
            }
        }

        val tietDesc = findViewById<EditText>(R.id.tietDesc)

        tietDesc.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
            if (!focus) {
                checkField(1)
            }
        }

        val ddGenre = findViewById<AutoCompleteTextView>(R.id.ddGenre)

        ddGenre.onFocusChangeListener = View.OnFocusChangeListener { _: View, focus ->
            if (!focus) {
                checkField(2)
            }
        }

    }

    private fun animatePickImage() {
        val root = clMainCW

        val c = ConstraintSet()
        c.clone(this, R.layout.activity_create_world_alt)

        if (btnPickImage.tag != "run") {
            val dur = ChangeBounds()
            dur.duration = 300

            val r1 = Runnable {
                TransitionManager.beginDelayedTransition(root, dur)
                c.applyTo(root)
                btnPickImage.text = ""
            }

            val r2 = Runnable {
                // Convert to Circle
                btnPickImage.setPadding(36, 4, 12, 4)
                btnPickImage.text = ""
                btnPickImage.background = ContextCompat.getDrawable(this, R.drawable.circle)
                btnPickImage.tag = "run"
                // Make Image Visible
                cvPreview.visibility = View.VISIBLE
            }

            val r = Runnable {}

            r.run {
                Handler().postDelayed(r1, 100)
                Handler().postDelayed(r2, dur.duration + 200)
            }

        }
    }

}