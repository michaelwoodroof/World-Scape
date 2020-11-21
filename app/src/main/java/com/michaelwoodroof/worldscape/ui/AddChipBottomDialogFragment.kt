package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.michaelwoodroof.worldscape.R

class AddChipBottomDialogFragment : BottomSheetDialogFragment() {

    // @TODO Maybe Add Suggestions from Positive / Negative Traits in strings
    // @TODO Adjust Keyboard to be just above Add Trait Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_new_chip, container, false)
    }

}