package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.adapters.WDCharacterAdapter
import com.michaelwoodroof.worldscape.adapters.WDPlacesAdapter
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.PlacesContent
import com.michaelwoodroof.worldscape.content.StoriesContent
import com.michaelwoodroof.worldscape.helper.GenerateSampleData
import kotlinx.android.synthetic.main.fragment_world_detail.*

class WorldDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_world_detail, container, false)
        // Set - Up Recent Characters for RecyclerView
        val rvRCharacters = root.findViewById<RecyclerView>(R.id.rvRecentCharacters)
        rvRCharacters.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvRCharacters.adapter = loadRecentCharacters()?.let { WDCharacterAdapter(it) }
        rvRCharacters.overScrollMode = View.OVER_SCROLL_NEVER

//        val rvRPlaces = rvRecentCharacters
//        rvRPlaces.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRPlaces.adapter = WDPlacesAdapter(loadRecentPlaces())
//        rvRPlaces.overScrollMode = View.OVER_SCROLL_NEVER


        //        val rvRStories = rvRecentStories
//        rvRStories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRStories.adapter = WDStoriesAdapter(loadRecentStories())
//        rvRStories.overScrollMode = View.OVER_SCROLL_NEVER

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