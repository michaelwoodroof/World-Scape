package com.michaelwoodroof.worldscape.content

object CharacterContent {

    data class CharacterItem(val name : String, val age : String, val uid : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}