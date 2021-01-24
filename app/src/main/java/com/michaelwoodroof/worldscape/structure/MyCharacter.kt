package com.michaelwoodroof.worldscape.structure

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class MyCharacter (
    var name : String, var hasImg : Boolean, var biography : String,
    var yearOfBirth : String?, var birthday : String?,
    var placeOfBirth : String?, var currentLocation : String?, var occupation: String?,
    var height : String?, var weight : String?, var eyeColor : String?,
    var race : String?, var hairColor : String?, var build : String?,
    var markings: String?, var hairStyle: String?, var clothingStyle: String?,
    var positiveTraits: List<String>, var negativeTraits: List<String>,
    var interests : List<String>, var fears : List<String>, var stats : StatItem?,
    var uid : String, var wuid : String) : Parcelable, Serializable {

    override fun toString(): String {
        return super.toString()
    }

    companion object {
        @JvmStatic private val serialVersionUID: Long = 102
    }
}