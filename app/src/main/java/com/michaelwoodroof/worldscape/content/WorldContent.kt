package com.michaelwoodroof.worldscape.content

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

object WorldContent {

    @Parcelize
    data class WorldItem(val title : String, val desc : String, val genre : String, val imgPath : String,
                         val color : String, val uid : String) : Parcelable, Serializable {
        override fun toString(): String {
            return super.toString()
        }
    }

}