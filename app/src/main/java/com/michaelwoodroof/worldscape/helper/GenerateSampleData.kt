package com.michaelwoodroof.worldscape.helper

import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.content.WorldContent
import java.util.*
import kotlin.collections.ArrayList

// This is a helper class to generate sample data for Recycler Views

object GenerateSampleData {

    fun generateCharacterData(limit : Int) : ArrayList<CharacterContent.CharacterItem> {

        val dataset = ArrayList<CharacterContent.CharacterItem>()
        dataset.add(CharacterContent.CharacterItem("Sir Nibbles", false, "200BC", "Lorem",
            "Lorem", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("Knight", false, "2344BC", "Lorem",
            "Lorem", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("The Dark One", false, "10AD", "Lorem",
            "Lorem", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("Holy Crusader", false, "90AD", "Lorem",
            "Lorem", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("The Matriarch", false, "160AD", "Lorem",
            "Lorem", generateUUID()))
        dataset.add(CharacterContent.CharacterItem("Neo", false, "192AD", "Lorem",
            "Lorem", generateUUID()))

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