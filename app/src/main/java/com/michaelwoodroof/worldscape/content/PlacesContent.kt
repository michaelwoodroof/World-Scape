package com.michaelwoodroof.worldscape.content

object PlacesContent {

    data class PlacesItem(val name : String, val age : String, val uid : String) {
        override fun toString(): String {
            return super.toString()
        }
    }

}