package app.allulith.worldscape.fragments

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
import app.allulith.worldscape.R
import app.allulith.worldscape.adapters.WDCharacterAdapter
import app.allulith.worldscape.databinding.FragmentWorldDetailBinding
import app.allulith.worldscape.detail.WorldDetailActivity
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.utils.assignTouch
import app.allulith.worldscape.structure.MyCharacter
import app.allulith.worldscape.structure.Place
import app.allulith.worldscape.structure.Story

class WorldDetailFragment: Fragment() {

    // @TODO Add Edit plus description textview
    private lateinit var binding: FragmentWorldDetailBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWorldDetailBinding.inflate(inflater, container, false)

        // Set - Up Recent Characters for RecyclerView
        binding.rvRecentCharacters.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentCharacters.adapter = WDCharacterAdapter(loadRecentCharacters())

        // Set - Up Recent Places for RecylcerView
        //val rvRPlaces = root.findViewById<RecyclerView>(R.id.rvRecentPlaces)
//        rvRPlaces.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRPlaces.adapter = WDPlacesAdapter(loadRecentPlaces())


       // Set - Up Recent Stories for RecylcerView
        val rvRStories = binding.rvRecentStories
//        rvRStories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        rvRStories.adapter = WDStoriesAdapter(loadRecentStories())

        val btnSAC : ImageButton = binding.btnShowAllCharacters
        val btnSAP : ImageButton = binding.btnShowAllPlaces
        val btnSAS : ImageButton = binding.btnShowAllStories

        // Set On Touches
        btnSAC.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
            ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_expansion, null) as AnimatedVectorDrawable,
            ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAP.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        btnSAS.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(view as ImageButton, event,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_expansion, null) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(resources, R.drawable.avd_chevron_shrink_short, null) as AnimatedVectorDrawable)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataSet = loadRecentCharacters()

        binding.rvRecentCharacters.adapter = WDCharacterAdapter(dataSet)
        if (dataSet.size == 0) {
            binding.rvRecentCharacters.overScrollMode = View.OVER_SCROLL_NEVER
        } else {
            // @TODO Add Scroll effect when items exceed phone height
            binding.rvRecentCharacters.overScrollMode = View.OVER_SCROLL_ALWAYS
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