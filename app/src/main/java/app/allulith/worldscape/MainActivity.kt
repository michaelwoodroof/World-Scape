package app.allulith.worldscape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import app.allulith.worldscape.adapters.WorldAdapter
import app.allulith.worldscape.detail.WorldDetailActivity
import app.allulith.worldscape.shared.SortDialogFragment
import app.allulith.worldscape.utils.*
import app.allulith.worldscape.structure.World
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.*

class MainActivity : AppCompatActivity() {

    private var deletedWorld: World? = null
    private var deletedWorldImage: Bitmap? = null
    private var worldData: ArrayList<World> = ArrayList()
    private lateinit var snackBar: Snackbar

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

    override fun onPause() {
        cleanUp()
        super.onPause()
    }

    override fun onDestroy() {
        cleanUp()
        super.onDestroy()
    }

    private fun cleanUp() {
        // Delete any Pending Worlds
        if (deletedWorld != null) {
            val mf = ManageFiles(this)
            deletedWorld?.uid?.let { mf.deleteWorld(it) }
        }

        // Dismiss Snackbar
        if (this::snackBar.isInitialized) {
            snackBar.dismiss()
        }
    }

    private fun updateDataSet(givenDataSet: ArrayList<World>? = null) {
        val data: ArrayList<World>
        if (givenDataSet == null) {
            val mf = ManageFiles(this)
            worldData = mf.getWorlds()
            data = worldData
        } else {
            data = givenDataSet
        }

        val adapter = WorldAdapter(data)
        rvWorldsM.adapter = adapter
        adapter.notifyDataSetChanged()

        // Detect overflows
        if (worldData.size == 0) {
            rvWorldsM.overScrollMode = View.OVER_SCROLL_NEVER
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val calculatedHeight = displayMetrics.heightPixels - 50.toPixels()
            val worldItemsHeight = (WorldAdapter.cardHeight.toPixels() * data.size)

            if (calculatedHeight - worldItemsHeight > 0) {
                rvWorldsM.overScrollMode = View.OVER_SCROLL_NEVER
            } else {
                rvWorldsM.overScrollMode = View.OVER_SCROLL_ALWAYS
            }
        }

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

                deletedWorldImage = if (deletedWorld?.hasImg == true) {
                    deletedWorld?.uid?.let { mf.getWorldImage(it) }
                } else {
                    null
                }

                val modifiedDataSet = worldData.clone() as ArrayList<World>
                modifiedDataSet.remove(deletedWorld)

                updateDataSet(modifiedDataSet)

                snackBar = Snackbar.make(
                    coMain,
                    R.string.snack_bar_delete_world_message,
                    Snackbar.LENGTH_LONG
                )
                .setAction(R.string.undo) {
                    // Save World
                    updateDataSet()
                }

                // Add Callback to Snackbar
                snackBar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(snackbar: Snackbar?, event: Int) {
                        if (event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_TIMEOUT) {
                            // Delete World Now
                            deletedWorld?.let { mf.deleteWorld(it.uid) }
                            updateDataSet()
                            // Reset Variables
                            deletedWorld = null
                            deletedWorldImage = null
                        } else {
                            updateDataSet()
                            // Reset Variables
                            deletedWorld = null
                            deletedWorldImage = null
                        }
                        super.onDismissed(snackbar, event)
                    }
                })

                snackBar.show()
                dialog.dismiss()

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
        // Load a dialog with options to sort Worlds
        val fragmentManager = supportFragmentManager
        val sortFragment = SortDialogFragment()
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(android.R.id.content, sortFragment).commit()
    }

}