package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.WorldContent
import com.michaelwoodroof.worldscape.adapters.WorldAdapter
import com.michaelwoodroof.worldscape.helper.ManageFiles
import kotlin.collections.ArrayList

class WorldFragment : Fragment() {

    private lateinit var rv : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_world, container, false)
        val rvWorlds = root.findViewById(R.id.rvWorlds) as RecyclerView
        val fab = root.findViewById(R.id.fabCreateWorld) as ExtendedFloatingActionButton
        rvWorlds.layoutManager = LinearLayoutManager(activity)
        val dataset = loadWorlds()
        // Ensures in Alphabetical Order @TODO Allow user to Filter and Search for Worlds
        //dataset.sortBy { it.title }
        rvWorlds.adapter = WorldAdapter(dataset)
        rv = rvWorlds

        val sl = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fab.shrink()
                } else {
                    fab.extend()
                }
            }
        }

        rvWorlds.addOnScrollListener(sl)

        return root
    }

    override fun onStart() {
        val dataset = loadWorlds()
        rv.adapter = WorldAdapter(dataset)
        if (dataset.size == 0) {
           rv.overScrollMode = View.OVER_SCROLL_NEVER
        } else {
            // @TODO Add Scroll effect when items exceed phone height
            rv.overScrollMode = View.OVER_SCROLL_ALWAYS
        }
        super.onStart()
    }

    private fun loadWorlds() : ArrayList<WorldContent.WorldItem> {
        val mf = activity?.baseContext?.let { ManageFiles(it) }
        return mf?.getWorlds() ?: ArrayList()
    }

}