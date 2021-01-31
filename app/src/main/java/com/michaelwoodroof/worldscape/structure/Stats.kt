package com.michaelwoodroof.worldscape.structure

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

// @TODO Implement Fields

@Parcelize
data class StatItem (var attribute: String, var value: String) : Parcelable, Serializable {

    override fun toString(): String {
        return "[attribute: $attribute, value: $value]"
    }

    companion object {
        @JvmStatic private val serialVersionUID: Long = 105
    }
}