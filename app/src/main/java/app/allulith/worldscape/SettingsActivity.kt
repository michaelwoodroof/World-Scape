package app.allulith.worldscape

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import app.allulith.worldscape.databinding.ActivitySettingsBinding
import app.allulith.worldscape.utils.SetStatusBar
import app.allulith.worldscape.utils.assignTouch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString("theme", "")) {
            "dark_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            "light_mode" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

        }

        setContentView(R.layout.activity_settings)

        SetStatusBar.create(this.window, this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set-Up Toolbar
        val toolbar = findViewById<ConstraintLayout>(R.id.incToolbarS)

        val tv = toolbar.findViewById<TextView>(R.id.tvTitle)
        tv.text = getString(R.string.settings)
        val btnMenu = toolbar.findViewById<ImageButton>(R.id.btnMenu)
        btnMenu.visibility = View.GONE
        val btnSettings = toolbar.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.visibility = View.GONE
        val btnBack = toolbar.findViewById<ImageButton>(R.id.btnBack)
        btnBack.visibility = View.VISIBLE

        btnBack.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_expansion_left, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_shrink_left, null) as AnimatedVectorDrawable
            )
        })
    }

    fun goBack(view : View) {
        super.onBackPressed()
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            when (key) {
                "theme" -> {
                    when (sharedPreferences.getString("theme", "")) {
                        "dark_mode" -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }

                        "light_mode" -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }

                        else -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        }

                    }
                }
            }
        }
    }

}
