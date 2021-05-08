package app.allulith.worldscape.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import app.allulith.worldscape.R
import app.allulith.worldscape.SettingsActivity
import app.allulith.worldscape.databinding.ActivityCharacterDetailBinding
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.utils.SetStatusBar
import app.allulith.worldscape.utils.assignTouch
import app.allulith.worldscape.structure.MyCharacter
import app.allulith.worldscape.structure.World

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private var character : MyCharacter? = null
    lateinit var world : World

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SetStatusBar.create(this.window, this)

        // Set-up Toolbar
        val toolbar = findViewById<ConstraintLayout>(R.id.incToolbarCharacterDetail)

        val btnSettings = toolbar.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_settings_expand, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_settings_shrink, null) as AnimatedVectorDrawable
            )
        })

        val btnBack = toolbar.findViewById<ImageButton>(R.id.btnBack)
        btnBack.visibility = View.VISIBLE
        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_expansion_left, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_shrink_left, null) as AnimatedVectorDrawable
            )
        })

        val btnMenu = toolbar.findViewById<ImageButton>(R.id.btnMenu)
        btnMenu.visibility = View.GONE

        character = intent?.getParcelableExtra("character")

        val title = toolbar.findViewById<TextView>(R.id.tvTitle)
        title.text = character?.name

        binding.tvCDDesc.text = character?.biography

        val mf = ManageFiles(this)
        binding.imgCDPreview.setImageBitmap(character?.wuid?.let { character?.uid?.let { it1 ->
            mf.getCharacterImage(it,
                it1
            )
        }})
        
//        tvCDHeight.text = character?.height
//        tvCDWeight.text = character?.weight
//        tvCDBirthday.text = character?.yearOfBirth
//        tvCDBirthPlace.text = character?.placeOfBirth

    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    fun loadSettings(view : View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

}