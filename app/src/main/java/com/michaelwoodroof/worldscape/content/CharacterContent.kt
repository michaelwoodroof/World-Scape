package com.michaelwoodroof.worldscape.content

object CharacterContent {

    data class CharacterItem(val name : String, val hasImg : Boolean, val yearOfBirth : String,
                             val placeOfBirth : String, val currentLocation : String,
                             val uid : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}