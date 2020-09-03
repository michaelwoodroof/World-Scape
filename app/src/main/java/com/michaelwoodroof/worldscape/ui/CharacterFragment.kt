package com.michaelwoodroof.worldscape.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.adapters.CharacterAdapter
import com.michaelwoodroof.worldscape.helper.GenerateSampleData

class CharacterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_character, container, false)
        val rvCharacter = root.findViewById(R.id.rvCharacters) as RecyclerView
        rvCharacter.layoutManager = LinearLayoutManager(activity)
        val dataset = loadCharacters()
        rvCharacter.adapter = CharacterAdapter(dataset)
        return root
    }

    // @TODO Update with Real Method
    private fun loadCharacters() : ArrayList<CharacterContent.CharacterItem> {
        return GenerateSampleData.generateCharacterData(0)
    }

}