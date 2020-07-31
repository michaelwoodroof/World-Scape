package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.michaelwoodroof.worldscape.R

class WorldDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // @TODO Make Proper
        val root = inflater.inflate(R.layout.fragment_world_detail, container, false)
        // @TODO Add any needed changed to Layout
        return root
    }

}