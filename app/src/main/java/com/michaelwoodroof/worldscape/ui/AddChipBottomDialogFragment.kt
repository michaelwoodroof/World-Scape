package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.michaelwoodroof.worldscape.R

class AddChipBottomDialogFragment : BottomSheetDialogFragment() {

    // @TODO Maybe Add Suggestions from Positive / Negative Traits in strings
    // @TODO Adjust Keyboard to be just above Add Trait Button

    var isPos = true
    lateinit var titleOfFrag : String
    lateinit var buttonText : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_new_chip, container, false)
        val tvCreate = root.findViewById<TextView>(R.id.tvCreateTrait)
        tvCreate.text = titleOfFrag
        val btnAdd = root.findViewById<Button>(R.id.btnAddTrait)
        btnAdd.text = buttonText
        return root
    }

}