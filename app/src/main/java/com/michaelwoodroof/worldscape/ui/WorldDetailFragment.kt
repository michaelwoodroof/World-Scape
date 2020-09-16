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
import com.michaelwoodroof.worldscape.adapters.WDCharacterAdapter
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.PlacesContent
import com.michaelwoodroof.worldscape.content.StoriesContent
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent
import com.michaelwoodroof.worldscape.helper.GenerateSampleData

class WorldDetailFragment : Fragment() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_world_detail, container, false)
        // Set - Up Recent Characters for RecyclerView
        val rvRCharacters = root.findViewById<RecyclerView>(R.id.rvRecentCharacters)
        rvRCharacters.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvRCharacters.adapter = loadRecentCharacters()?.let { WDCharacterAdapter(it) }

        val btnSAC : ImageButton = root.findViewById(R.id.btnShowAllCharacters)
        val btnSAP : ImageButton = root.findViewById(R.id.btnShowAllPlaces)
        val btnSAS : ImageButton = root.findViewById(R.id.btnShowAllStories)

        // Set On Touches
        btnSAC.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(view as ImageButton, event,
            ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
            ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAP.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAS.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

//        val rvRPlaces = rvRecentCharacters
//        rvRPlaces.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRPlaces.adapter = WDPlacesAdapter(loadRecentPlaces())


        //        val rvRStories = rvRecentStories
//        rvRStories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRStories.adapter = WDStoriesAdapter(loadRecentStories())

        return root
    }

    // @TODO Replace with real method
    private fun loadRecentCharacters() : ArrayList<CharacterContent.CharacterItem>? {
        return GenerateSampleData.generateCharacterData(0)
    }

    // @TODO Replace with real method
    private fun loadRecentPlaces() : ArrayList<PlacesContent.PlacesItem>? {
        return null
    }

    // @TODO Replace with real method
    private fun loadRecentStories() : ArrayList<StoriesContent.StoriesItem>? {
        return null
    }

}