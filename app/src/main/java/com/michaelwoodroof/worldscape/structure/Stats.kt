package com.michaelwoodroof.worldscape.structure

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

// @TODO Implement Fields

@Parcelize
data class StatItem (
    var uid : String) : Parcelable, Serializable {

    override fun toString(): String {
        return super.toString()
    }

    companion object {
        @JvmStatic private val serialVersionUID: Long = 105
    }
}