package com.michaelwoodroof.worldscape.content

object WorldContent {

    data class WorldItem(val title : String, val desc : String, val uid : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}