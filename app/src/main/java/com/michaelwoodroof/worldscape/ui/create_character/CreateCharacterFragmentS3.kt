package com.michaelwoodroof.worldscape.ui.create_character

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R

class CreateCharacterFragmentS3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return(inflater.inflate(R.layout.fragment_create_character_s3, container, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(2)
        (activity as CreateCharacterActivity).setUpTextChangers(2)
    }

}