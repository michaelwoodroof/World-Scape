package com.michaelwoodroof.worldscape.helper

import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.WorldContent
import java.util.*
import kotlin.collections.ArrayList

// This is a helper class to generate sample data for Recycler Views

object GenerateSampleData {

    fun generateCharacterData(limit : Int) : ArrayList<CharacterContent.CharacterItem> {

        val dataset = ArrayList<CharacterContent.CharacterItem>()
        val baseCharacter = CharacterContent.CharacterItem("Bob", false, "Bob is bob", "200BC",
            "200", "200", "200", "200", "200", "Blue", "Lizard", "Red", "Stocky", "None", "", "",
            "", ArrayList(), ArrayList(), ArrayList(), ArrayList(), null, generateUUID(), "")

        for (i in 0..limit) {
            baseCharacter.uid = generateUUID()
            baseCharacter.name = baseCharacter.uid
            dataset.add(baseCharacter)
        }

        return dataset

    }

    fun generateWorldData(limit : Int) : ArrayList<WorldContent.WorldItem> {

        val dataset = ArrayList<WorldContent.WorldItem>()

        dataset.add(WorldContent.WorldItem("World One",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(WorldContent.WorldItem("World Two",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(WorldContent.WorldItem("World Three",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(WorldContent.WorldItem("World Four",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(WorldContent.WorldItem("World Five",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(WorldContent.WorldItem("World Six",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        return dataset

    }

    private fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}