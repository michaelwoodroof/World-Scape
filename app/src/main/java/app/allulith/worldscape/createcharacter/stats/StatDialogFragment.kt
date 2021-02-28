package app.allulith.worldscape.createcharacter.stats

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import app.allulith.worldscape.R
import com.google.android.material.textfield.TextInputLayout
import app.allulith.worldscape.createcharacter.CreateCharacterActivity
import app.allulith.worldscape.utils.assignTouch
import app.allulith.worldscape.structure.StatItem
import app.allulith.worldscape.createcharacter.stats.fragments.FireEmblemFragment
import app.allulith.worldscape.utils.afterTextChanged
import kotlinx.android.synthetic.main.fragment_stat_sheet.*

class StatDialogFragment: DialogFragment() {

    private var currentPreset = ""
    private lateinit var flStats: FrameLayout

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_stat_sheet, container, false)
        flStats = root.findViewById(R.id.flStats)
        val toolbar = root.findViewById<ConstraintLayout>(R.id.incToolbarStat)
        val title = toolbar.findViewById<TextView>(R.id.tvTitle)
        title.text = getString(R.string.stats)

        val close = toolbar.findViewById<ImageButton>(R.id.btnClose)
        close.setOnClickListener {
            this.dismiss()
            (activity as CreateCharacterActivity).popStack()
            (activity as CreateCharacterActivity).isDialogLoaded = false
        }

        close.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.avd_close_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.avd_close_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        val save = toolbar.findViewById<Button>(R.id.btnSave)
        save.setOnClickListener {
            val attributes = saveStats()
            (activity as CreateCharacterActivity).currentCharacter?.stats = attributes
            this.dismiss()
            (activity as CreateCharacterActivity).popStack()
            (activity as CreateCharacterActivity).isDialogLoaded = false
        }

        val items = listOf("DND", "Fire Emblem", "Custom")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, items)
        val preset = root.findViewById<TextInputLayout>(R.id.txtiPreset)
        (preset.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        preset.editText?.afterTextChanged {
            if (currentPreset == "" || currentPreset != it) {
                currentPreset = it
                updatePreset()
            }
        }

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun updatePreset() {
        var statFragment: Fragment? = null
        when (currentPreset) {

            "DND" -> {

            }

            "Fire Emblem" -> {
                statFragment = FireEmblemFragment()
            }

            "Custom" -> {

            }

        }

        if (statFragment != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.flStats, statFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    private fun saveStats(): ArrayList<StatItem> {
        val attributes: ArrayList<StatItem> = ArrayList()
        var lastTextView = ""

        var cl: ConstraintLayout? = null

        when (currentPreset) {
            "DND" -> {
                //@TODO
            }

            "Fire Emblem" -> {
                cl = flStats.findViewById(R.id.clInnerFE)
            }

            "Custom" -> {
                //@TODO
            }

            else -> {
                cl = null
            }
        }

        return if (cl != null) {
            for (view in cl.children) {
                when (view) {
                    is TextView -> {
                        lastTextView = view.text.toString()
                    }
                    is TextInputLayout -> {
                        attributes.add(StatItem(lastTextView, view.editText?.text.toString()))
                    }
                }
            }
            attributes
        } else {
            attributes
        }
    }

}