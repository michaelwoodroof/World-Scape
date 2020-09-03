package com.michaelwoodroof.worldscape.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.CharacterContent
import kotlinx.android.synthetic.main.wd_character_layout.view.*

class WDCharacterAdapter (private val givenValues: List<CharacterContent.CharacterItem>)
: RecyclerView.Adapter<WDCharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wd_character_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = givenValues[position]
        holder.mTitle.text = item.name
    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(mView : View) : RecyclerView.ViewHolder(mView) {
        val mTitle : TextView = mView.tvCharacterName

        override fun toString(): String {
            return super.toString() + " '"
        }
    }


}