package com.michaelwoodroof.worldscape.content

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

object PlacesContent {

    @Parcelize
    data class PlacesItem(val name : String, val age : String, val uid : String) : Parcelable, Serializable {
        override fun toString(): String {
            return super.toString()
        }

        companion object {
            @JvmStatic private val serialVersionUID: Long = 103
        }
    }

}