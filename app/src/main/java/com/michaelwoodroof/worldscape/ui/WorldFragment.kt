package com.michaelwoodroof.worldscape.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.WorldContent
import com.michaelwoodroof.worldscape.adapters.WorldAdapter
import com.michaelwoodroof.worldscape.helper.GenerateSampleData
import com.michaelwoodroof.worldscape.helper.ManageFiles
import kotlin.collections.ArrayList

class WorldFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_world, container, false)
        val rvWorlds = root.findViewById(R.id.rvWorlds) as RecyclerView
        rvWorlds.layoutManager = LinearLayoutManager(activity)
        val dataset = loadWorlds()
        // Ensures in Alphabetical Order @TODO Allow user to Filter and Search for Worlds
        //dataset.sortBy { it.title }
        rvWorlds.adapter = WorldAdapter(dataset)
        rvWorlds.overScrollMode = View.OVER_SCROLL_NEVER
        return root
    }

    private fun loadWorlds() : ArrayList<WorldContent.WorldItem> {
        val mf = activity?.baseContext?.let { ManageFiles(it) }
        return mf?.getWorlds() ?: ArrayList()
    }


}