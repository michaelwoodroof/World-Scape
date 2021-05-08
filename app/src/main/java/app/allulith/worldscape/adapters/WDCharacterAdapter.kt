package app.allulith.worldscape.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.allulith.worldscape.R
import app.allulith.worldscape.databinding.WdCharacterLayoutBinding
import com.google.android.material.card.MaterialCardView
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.structure.MyCharacter

class WDCharacterAdapter (private val givenValues: List<MyCharacter>)
: RecyclerView.Adapter<WDCharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WdCharacterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = givenValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(private val binding: WdCharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MyCharacter) {

            binding.tvCharacterName.text = data.name
            binding.cvWDCharacter.tag = data

            if (data.hasImg) {
                val mf = ManageFiles(itemView.context)
                binding.imgCharacterView.setImageBitmap(mf.getCharacterImage(data.wuid, data.uid))
            }

        }

    }


}