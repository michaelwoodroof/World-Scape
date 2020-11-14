package com.michaelwoodroof.worldscape.content

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

object CharacterContent {

    @Parcelize
    data class CharacterItem(
        var name : String, var hasImg : Boolean, var biography : String,
        var yearOfBirth : String?, var birthday : String?,
        var placeOfBirth : String?, var currentLocation : String?, var occupation: String,
        var height : String?, var weight : String?, var eyeColor : String?,
        var race : String?, var hairColor : String?, var build : String?,
        var markings: String?, var hairStyle: String?, var clothingStyle: String?,
        var uid : String) : Parcelable, Serializable {


        override fun toString(): String {
            return super.toString()
        }

        companion object {
            @JvmStatic private val serialVersionUID: Long = 102
        }
    }

}