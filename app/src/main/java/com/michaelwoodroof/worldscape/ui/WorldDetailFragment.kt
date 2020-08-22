package com.michaelwoodroof.worldscape.ui

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.adapters.WDCharacterAdapter
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.PlacesContent
import com.michaelwoodroof.worldscape.content.StoriesContent
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
        rvRCharacters.overScrollMode = View.OVER_SCROLL_NEVER

        val btnSAC : ImageButton = root.findViewById(R.id.btnShowAllCharacters)
        val btnSAP : ImageButton = root.findViewById(R.id.btnShowAllPlaces)
        val btnSAS : ImageButton = root.findViewById(R.id.btnShowAllStories)

        // Set On Touches
        btnSAC.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener handleTouch(view as ImageButton, event)
        })

        btnSAP.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener handleTouch(view as ImageButton, event)
        })

        btnSAS.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener handleTouch(view as ImageButton, event)
        })

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

    private fun handleTouch(view : ImageButton, event: MotionEvent) : Boolean {
        val chosenDrawable1 = ResourcesCompat.getDrawable(resources, R.drawable.chevron_expansion, null)
        val chosenDrawable2 = ResourcesCompat.getDrawable(resources, R.drawable.chevron_shrink_short, null)
        val chosenDrawable : AnimatedVectorDrawable
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (view.tag == "na") {
                    val b = view as ImageButton
                    chosenDrawable = chosenDrawable1 as AnimatedVectorDrawable
                    b.tag = "es"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (view.tag == "es") {
                    val b = view as ImageButton
                    chosenDrawable = chosenDrawable2 as AnimatedVectorDrawable
                    b.tag = "na"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()

                    val xE = event.x
                    val yE = event.y

                    if (!(xE < 0 || xE > b.width || yE < 0 || yE > b.height)) {
                        // Is Within Bounds
                        view.performClick()
                    }
                }
                return false
            }
            else -> {return false}
        }
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