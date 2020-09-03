package com.michaelwoodroof.worldscape.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.wd_character_layout.view.*

class WDStoriesAdapter {

    class ViewHolder(mView : View) : RecyclerView.ViewHolder(mView) {
        val mTitle: TextView = mView.tvCharacterName

        override fun toString(): String {
            return super.toString() + " '"
        }
    }

}