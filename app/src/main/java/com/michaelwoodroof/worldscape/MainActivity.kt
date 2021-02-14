package com.michaelwoodroof.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.michaelwoodroof.worldscape.adapters.WorldAdapter
import com.michaelwoodroof.worldscape.helper.*
import com.michaelwoodroof.worldscape.structure.World
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.*

class MainActivity : AppCompatActivity() {

    private var deletedWorld: World? = null
    private var worldData: ArrayList<World> = ArrayList()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
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

        setContentView(R.layout.activity_main)
        SetStatusBar.create(this.window, this)

        // Set Up Toolbar
        val tv = incToolbarM.findViewById<TextView>(tvTitle.id)
        // Hide Menu as Not Needed
        val btnMenu = incToolbarM.findViewById<ImageButton>(btnMenu.id)
        btnMenu.visibility = View.GONE

        val btnSettings = incToolbarM.findViewById<ImageButton>(btnSettings.id)
        btnSettings.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.settings_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.settings_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        val btnSort = incToolbarM.findViewById<ImageButton>(btnSort.id)
        btnSort.visibility = View.VISIBLE
        btnSort.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sort_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sort_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        // Set-up the Recyclerview
        val mf = ManageFiles(this)
        worldData = mf.getWorlds()

        rvWorldsM.adapter = WorldAdapter(worldData)

        val sl = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fabCreateWorldM.shrink()
                } else {
                    fabCreateWorldM.extend()
                }
            }
        }

        rvWorldsM.layoutManager = LinearLayoutManager(this)
        rvWorldsM.adapter = WorldAdapter(this.worldData)
        rvWorldsM.addOnScrollListener(sl)

        tv.text = getString(R.string.app_name)
        SetGradient.assign(btnPrompt as Button, this)

        SetGradient.assign(fabCreateWorldM as ExtendedFloatingActionButton, this)

        showPrompt(worldData.size)
    }

    override fun onStart() {
        super.onStart()
        updateDataSet()
        showPrompt(worldData.size)
    }

    private fun updateDataSet() {
        val mf = ManageFiles(this)
        worldData = mf.getWorlds()
        val adapter = WorldAdapter(worldData)
        rvWorldsM.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun loadSettings(view: View) {
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    fun loadCreateWorld(view: View) {
        val i = Intent(this, CreateWorldActivity::class.java)
        startActivity(i)
    }

    fun deleteWorld(view: View) {
        MaterialAlertDialogBuilder(this, R.style.DialogTheme)
            .setTitle(resources.getString(R.string.delete_world_dialog_title))
            .setMessage(resources.getString(R.string.delete_world_dialog_message))
            .setNeutralButton(resources.getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.dialog_accept)) { dialog, _ ->
                val mf = ManageFiles(this)
                deletedWorld = mf.getWorld(view.tag as String)
                if (mf.deleteWorld(view.tag as String)) {
                    // Create Snack Bar
                    updateDataSet()
                    Snackbar.make(coMain, R.string.delete_world_dialog_message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.undo) {
                            // Save World
                            deletedWorld?.uid?.let { dworld -> mf.createFolderStructure(dworld, this) }
                            mf.saveWorld(deletedWorld)
                            updateDataSet()
                        }
                        .show()

                    dialog.dismiss()
                } else {
                    // Try Again Once
                    if (!mf.deleteWorld(view.tag as String)) {
                        Toast.makeText(
                            baseContext, resources.getString(R.string.delete_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.show()
    }

    fun loadWorld(view: View) {
        val intent = Intent(this, WorldDetailActivity::class.java)
        val tag = view.tag as World
        intent.putExtra("uid", tag.uid)
        intent.putExtra("title", tag.title)
        this.startActivity(intent)
    }

    private fun showPrompt(size: Int) {
        when {
            size > 0 -> {
                imgWorldPrompt.visibility = View.GONE
                btnPrompt.visibility = View.GONE
                fabCreateWorldM.visibility = View.VISIBLE
            }
            size == 1 -> {
                // Animate it leaving the screen @TODO
                imgWorldPrompt.visibility = View.GONE
                btnPrompt.visibility = View.GONE
                fabCreateWorldM.visibility = View.VISIBLE
            }
            else -> {
                imgWorldPrompt.visibility = View.VISIBLE
                btnPrompt.visibility = View.VISIBLE
                fabCreateWorldM.visibility = View.GONE
            }
        }
    }

    fun sortData(view: View) {
        // @TODO
    }

}