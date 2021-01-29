package com.michaelwoodroof.worldscape.ui

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.WorldDetailActivity
import com.michaelwoodroof.worldscape.adapters.WDCharacterAdapter
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.helper.assignTouch
import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.structure.Place
import com.michaelwoodroof.worldscape.structure.Story
import kotlinx.android.synthetic.main.fragment_world_detail.*

class WorldDetailFragment : Fragment() {

    // @TODO Add Edit plus description textview

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_world_detail, container, false)

        // Set - Up Recent Characters for RecyclerView
        val rvRCharacters = root.findViewById<RecyclerView>(R.id.rvRecentCharacters)
        rvRCharacters.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvRCharacters.adapter = WDCharacterAdapter(loadRecentCharacters())

        // Set - Up Recent Places for RecylcerView
        val rvRPlaces = rvRecentPlaces
//        rvRPlaces.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRPlaces.adapter = WDPlacesAdapter(loadRecentPlaces())


       // Set - Up Recent Stories for RecylcerView
        val rvRStories = rvRecentStories
//        rvRStories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRStories.adapter = WDStoriesAdapter(loadRecentStories())

        val btnSAC : ImageButton = root.findViewById(R.id.btnShowAllCharacters)
        val btnSAP : ImageButton = root.findViewById(R.id.btnShowAllPlaces)
        val btnSAS : ImageButton = root.findViewById(R.id.btnShowAllStories)

        // Set On Touches
        btnSAC.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
            ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
            ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAP.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAS.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        return root
    }

    override fun onStart() {
        super.onStart()
        val dataset = loadRecentCharacters()

        rvRecentCharacters.adapter = WDCharacterAdapter(dataset)
        if (dataset.size == 0) {
            rvRecentCharacters.overScrollMode = View.OVER_SCROLL_NEVER
        } else {
            // @TODO Add Scroll effect when items exceed phone height
            rvRecentCharacters.overScrollMode = View.OVER_SCROLL_ALWAYS
        }
    }

    private fun loadRecentCharacters() : ArrayList<MyCharacter> {
        val mf = activity?.baseContext?.let { ManageFiles(it) }
        return mf?.getCharacters((activity as WorldDetailActivity).intent.getStringExtra("uid").toString(), 10) ?: ArrayList()
    }

    // @TODO Replace with real method
    private fun loadRecentPlaces() : ArrayList<Place>? {
        return null
    }

    // @TODO Replace with real method
    private fun loadRecentStories() : ArrayList<Story>? {
        return null
    }

}