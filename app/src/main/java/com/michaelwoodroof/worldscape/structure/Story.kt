package com.michaelwoodroof.worldscape.structure

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Story (val name : String, val age : String, val uid : String) : Parcelable, Serializable {
    override fun toString(): String {
        return super.toString()
    }

    companion object {
        @JvmStatic private val serialVersionUID: Long = 104
    }
}