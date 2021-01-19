package com.michaelwoodroof.worldscape.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.content.CharacterContent
import com.michaelwoodroof.worldscape.helper.ManageFiles
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

        if (item.hasImg) {
            val mf = ManageFiles(holder.mImg.context)
            holder.mImg.setImageBitmap(mf.getCharacterImage(item.wuid, item.uid))
        }
    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(mView : View) : RecyclerView.ViewHolder(mView) {
        val mTitle : TextView = mView.tvCharacterName
        val mImg : ImageView = mView.imgCharacterView

        override fun toString(): String {
            return super.toString() + " '"
        }
    }


}