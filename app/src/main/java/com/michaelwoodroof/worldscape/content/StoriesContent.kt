package com.michaelwoodroof.worldscape.content

object StoriesContent {

    data class StoriesItem(val name : String, val age : String, val uid : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}