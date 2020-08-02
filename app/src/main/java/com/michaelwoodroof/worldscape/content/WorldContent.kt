package com.michaelwoodroof.worldscape.content

object WorldContent {

    data class WorldItem(val title : String, val desc : String, val uid : String, val colour : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}