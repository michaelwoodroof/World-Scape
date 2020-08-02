package com.michaelwoodroof.worldscape.helper

import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.WorldContent
import java.util.*
import kotlin.collections.ArrayList

// This is a helper class to generate sample data for recyclerviews

object GenerateSampleData {

    fun generateCharacterData(limit : Int) : ArrayList<CharacterContent.CharacterItem> {

        val dataset = ArrayList<CharacterContent.CharacterItem>()
        dataset.add(CharacterContent.CharacterItem("Sir Nibbles", "22", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("Knight Nibbles", "23", generateUUID()))

        return dataset

    }

    fun generateWorldData(limit : Int) : ArrayList<WorldContent.WorldItem> {

        val dataset = ArrayList<WorldContent.WorldItem>()

        dataset.add(WorldContent.WorldItem("World One",
            "A very very long description for this world however, words can be long", generateUUID(), "#8BC34A"))

        dataset.add(WorldContent.WorldItem("World Two",
            "A short description", generateUUID(), "#F44336"))

        dataset.add(WorldContent.WorldItem("World Three",
            "A very very long description for this world however, words can be long", generateUUID(), "#03A9F4"))

        dataset.add(WorldContent.WorldItem("World Four",
            "A short description", generateUUID(), "#9C27B0"))

        dataset.add(WorldContent.WorldItem("World Five",
            "A very very long description for this world however, words can be long", generateUUID(), "#FFEB3B"))

        dataset.add(WorldContent.WorldItem("World Six",
            "A short description", generateUUID(), "#009688"))

        return dataset

    }

    private fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}