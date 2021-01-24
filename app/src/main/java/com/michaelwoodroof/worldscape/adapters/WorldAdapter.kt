package com.michaelwoodroof.worldscape.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.helper.ManageFiles
import com.michaelwoodroof.worldscape.structure.World
import kotlinx.android.synthetic.main.world_layout.view.*

class WorldAdapter(private var givenValues: List<World>)
    : RecyclerView.Adapter<WorldAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.world_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = givenValues[position]
        holder.mTitle.text = item.title
        holder.mDesc.text = item.desc
        holder.mCont.id = position
        holder.mDel.tag = item.uid
        holder.itemView.tag = item.uid + "," + item.title
        holder.mCard.tag = item

        if (item.hasImg) {
            val mf = ManageFiles(holder.mImg.context)
            holder.mImg.setImageBitmap(mf.getWorldImage(item.uid))
        }

        when (item.genre) {

            "Fantasy" -> {
                holder.mGenre.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.itemView.resources,
                        R.drawable.ic_genre_fantasy,
                        null
                    )
                )
            }

            "Sci-Fi" -> {
                holder.mGenre.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.itemView.resources,
                        R.drawable.ic_genre_scifi,
                        null
                    )
                )
            }

            else -> {
                holder.mGenre.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        holder.itemView.resources,
                        R.drawable.ic_genre_other,
                        null
                    )
                )
            }

        }

    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitle : TextView = mView.tvTitle
        val mDesc : TextView = mView.tvDesc
        val mCard : CardView = mView.cvMain
        val mCont : ConstraintLayout = mView.clCard
        val mImg : ImageView = mView.imgPreview
        val mDel : ImageButton = mView.btnDelete
        val mGenre : ImageView = mView.imgGenre

        override fun toString(): String {
            return super.toString() + " '"
        }
    }

}
