package com.michaelwoodroof.worldscape.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.StatContent
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import kotlinx.android.synthetic.main.fragment_stat_sheet.*

class StatDialogFragment : DialogFragment() {

    var currentPreset = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_stat_sheet, container, false)
        val toolbar = root.findViewById<ConstraintLayout>(R.id.incToolbarStat)
        val title = toolbar.findViewById<TextView>(R.id.tvTitle)
        title.text = getString(R.string.stats)

        val close = toolbar.findViewById<ImageButton>(R.id.btnClose)
        close.setOnClickListener {
            this.dismiss()
            (activity as CreateCharacterActivity).popStack()
            (activity as CreateCharacterActivity).isDialogLoaded = false
        }

        close.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.close_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.close_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        val save = toolbar.findViewById<Button>(R.id.btnSave)
        save.setOnClickListener {
            this.dismiss()
            (activity as CreateCharacterActivity).popStack()
            (activity as CreateCharacterActivity).isDialogLoaded = false
            // @TODO Get Data from Fields
            val data = StatContent.StatItem("UU")
            (activity as CreateCharacterActivity).saveStats(data)
        }

        val items = listOf("DND", "Fire Emblem", "Custom")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, items)
        val preset = root.findViewById<TextInputLayout>(R.id.txtiPreset)
        (preset.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        preset.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(e: Editable?) {
                if (currentPreset == "" || currentPreset != e.toString()) {
                    Log.d("TESTDATA", "CHANGED PRESET")
                    currentPreset = e.toString()
                }
                Log.d("TESTDATA", e.toString())
            }

        })

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}