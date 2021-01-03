package com.michaelwoodroof.worldscape.ui.create_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R

class CreateCharacterFragmentS4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_character_s4, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(3)
        (activity as CreateCharacterActivity).setUpTextChangers(3)
    }

}