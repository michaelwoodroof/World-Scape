package com.michaelwoodroof.worldscape.ui.create_character

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R

class CreateCharacterFragmentS3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_character_s3, container, false)

        if (Build.VERSION.SDK_INT >= 23) {
            val sl = View.OnScrollChangeListener { _, _, sy, _, osy ->
                if (sy > osy) {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.shrink()
                } else {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.extend()
                }
            }

            val sv = root.findViewById<ScrollView>(R.id.svCreateCharacterS3)
            sv.setOnScrollChangeListener(sl)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(2)
        (activity as CreateCharacterActivity).setUpTextChangers(2)
    }

}