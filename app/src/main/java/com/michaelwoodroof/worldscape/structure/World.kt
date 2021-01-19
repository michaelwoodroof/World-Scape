package com.michaelwoodroof.worldscape.structure

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class World (
    val title: String, val desc: String, val genre: String, val hasImg: Boolean,
    val color: String, val uid: String) : Parcelable, Serializable {
    override fun toString(): String {
        return super.toString()
    }

    companion object {
        @JvmStatic private val serialVersionUID: Long = 101
    }

}
