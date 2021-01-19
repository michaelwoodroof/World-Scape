package com.michaelwoodroof.worldscape.helper

import com.michaelwoodroof.worldscape.structure.MyCharacter
import com.michaelwoodroof.worldscape.structure.World
import java.util.*
import kotlin.collections.ArrayList

// This is a helper class to generate sample data for Recycler Views

object GenerateSampleData {

    fun generateCharacterData(limit : Int) : ArrayList<MyCharacter> {

        val dataset = ArrayList<MyCharacter>()
        val baseCharacter = MyCharacter("Bob", false, "Bob is bob", "200BC",
            "200", "200", "200", "200", "200", "Blue", "Lizard", "Red", "Stocky", "None", "", "",
            "", ArrayList(), ArrayList(), ArrayList(), ArrayList(), null, generateUUID(), "")

        for (i in 0..limit) {
            baseCharacter.uid = generateUUID()
            baseCharacter.name = baseCharacter.uid
            dataset.add(baseCharacter)
        }

        return dataset

    }

    fun generateWorldData(limit : Int) : ArrayList<World> {

        val dataset = ArrayList<World>()

        dataset.add(World("World One",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(World("World Two",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(World("World Three",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(World("World Four",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(World("World Five",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        dataset.add(World("World Six",
            "A very very long description for this world however, words can be long", "",
            false, "#8BC34A" , generateUUID()))

        return dataset

    }

    private fun generateUUID() : String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

}