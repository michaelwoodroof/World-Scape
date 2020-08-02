package com.michaelwoodroof.worldscape.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.WorldDetailActivity
import com.michaelwoodroof.worldscape.content.WorldContent
import kotlinx.android.synthetic.main.default_toolbar.view.*
import kotlinx.android.synthetic.main.world_layout.view.*
import kotlinx.android.synthetic.main.world_layout.view.tvTitle
import org.w3c.dom.Text

class WorldAdapter (private val givenValues: List<WorldContent.WorldItem>)
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

        holder.mAccent.setBackgroundColor(Color.parseColor(item.colour))

        with(holder.mTitle) {
            setOnClickListener {
                onClick(this.context, item)
            }
        }

        with(holder.mDesc) {
            setOnClickListener {
                onClick(this.context, item)
            }
        }

        with (holder.mImg) {
            setOnClickListener {
                onClick(this.context, item)
            }
        }

        with (holder.mAccent) {
            setOnClickListener {
                onClick(this.context, item)
            }
        }

        with(holder.itemView) {
            setOnClickListener {
                onClick(this.context, item)
            }
        }

    }

    private fun onClick(ctx : Context, item : WorldContent.WorldItem) {
        val intent = Intent(ctx, WorldDetailActivity::class.java)
        intent.putExtra("uid", item.uid)
        intent.putExtra("title", item.title)
        ctx.startActivity(intent)
    }

    override fun getItemCount(): Int = givenValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitle : TextView = mView.tvTitle
        val mDesc : TextView = mView.tvDesc
        val mAccent : TextView = mView.tvAccent
        val mCard : CardView = mView.cvMain
        val mCont : ConstraintLayout = mView.clCard
        val mImg : ImageView = mView.imgPreview

        override fun toString(): String {
            return super.toString() + " '"
        }
    }

}