package app.allulith.worldscape

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import app.allulith.worldscape.databinding.ActivityCreateWorldBinding
import app.allulith.worldscape.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import app.allulith.worldscape.structure.World

class CreateWorldActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateWorldBinding
    private lateinit var uriPointer : Uri
    lateinit var r : Runnable

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateWorldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SetStatusBar.create(this.window, this)

        val displayMetrics: DisplayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density.toInt()

        SetGradient.assign(binding.btnCreate, this, dpWidth)

        val rootView = binding.colMainCW
        val height = displayMetrics.heightPixels

        // Detect Soft Keyboard
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff: Int = height - rootView.height
            if (heightDiff > 100) {
                binding.btnCreate.visibility = View.GONE
            } else {
                binding.btnCreate.visibility = View.VISIBLE
            }
        }

        // Set-Up Toolbar
        val toolbar = findViewById<ConstraintLayout>(R.id.incToolbarCW)

        val tv = toolbar.findViewById<TextView>(R.id.tvTitle)
        tv.text = getString(R.string.create_world_title)
        val btnMenu = toolbar.findViewById<ImageButton>(R.id.btnMenu)
        btnMenu.visibility = View.GONE
        val btnSettings = toolbar.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.visibility = View.GONE
        val btnBack = toolbar.findViewById<ImageButton>(R.id.btnBack)
        btnBack.visibility = View.VISIBLE

        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.avd_chevron_expansion_left,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.avd_chevron_shrink_left,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        // Set-Up DropDown
        val genreTypes = resources.getStringArray(R.array.genres)

        val adapter = ArrayAdapter(baseContext, R.layout.dropdown_menu_popup_item, genreTypes)
        binding.ddGenre.setAdapter(adapter)
    }

    override fun onStart() {
        super.onStart()
        setUpFocusChangers()
        setUpTextChangers()
        // Init Runnable
        r = Runnable {}
    }

    fun goBack(view: View) {
        super.onBackPressed()
    }

    fun addWorld(view: View) {

        if (checkFields()) {
            // Captures Data from Activity
            val title = binding.tietWorld.text.toString()
            val desc = binding.tietDesc.text.toString()

            var img = false
            if (binding.imgPreview.tag == "hasImage") {
                img = true
            }

            val mf = ManageFiles(this)
            val ed = binding.ddGenre.text
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
                Snackbar.make(
                    findViewById<CoordinatorLayout>(R.id.colMainCW),
                    resources.getString(R.string.err_save_world),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.action_text) {
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

    private fun delayError(field: Int) {
        // Ensures Reset before Attempting Timeout
        Handler(Looper.getMainLooper()).removeCallbacksAndMessages(r)
        r = Runnable {
            checkField(field)
        }
        Handler(Looper.getMainLooper()).postDelayed(r, 2000)
    }

    private fun checkField(field: Int) : Boolean {
        when (field) {

            0 -> {
                return if (binding.tietWorld.text.toString().trim() == "") {
                    // Highlight Field to Indicate Error
                    binding.tilWorld.error = getString(R.string.err_world)
                    false
                } else {
                    // Reset Field -- To Ensure Error isn't Showing
                    binding.tilWorld.error = null
                    true
                }
            }

            1 -> {
                return if (binding.tietDesc.text.toString().trim() == "") {
                    binding.tilDesc.error = getString(R.string.err_desc)
                    false
                } else {
                    binding.tilDesc.error = null
                    true
                }
            }

            2 -> {
                return if (binding.ddGenre.text.toString().trim() == "") {
                    binding.txtiGenre.error = getString(R.string.err_genre)
                    false
                } else {
                    binding.txtiGenre.error = null
                    true
                }
            }

            3 -> {
                if (binding.imgPreview.tag == "NT") {
                    binding.tvWarning.visibility = View.VISIBLE
                    binding.imgWarning.visibility = View.VISIBLE
                } else {
                    binding.tvWarning.visibility = View.GONE
                    binding.imgWarning.visibility = View.GONE
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

    fun pickImage(view: View) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MEDIA_PICK_CODE) {
                val selectedImage = data?.data
                if (selectedImage != null) {
                    binding.imgPreview.setImageURI(selectedImage)
                    binding.cvPreview.visibility = View.VISIBLE
                    uriPointer = selectedImage
                    binding.imgPreview.tag = "hasImage"
                    binding.btnPickImage.visibility = View.GONE
                    binding.btnPickImageShrunk.visibility = View.VISIBLE
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

}