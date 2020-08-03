package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
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
import kotlin.collections.ArrayList

// @TODO Combine with Main Activity if Needed

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

    // @TODO Update with Real Method
    fun loadWorlds() : ArrayList<WorldContent.WorldItem> {
        return GenerateSampleData.generateWorldData(0)
    }


}