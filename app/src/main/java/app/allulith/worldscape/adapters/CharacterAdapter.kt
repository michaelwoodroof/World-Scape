package app.allulith.worldscape.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.allulith.worldscape.R
import app.allulith.worldscape.databinding.CharacterLayoutBinding
import com.google.android.material.card.MaterialCardView
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.structure.MyCharacter

class CharacterAdapter (private val givenValues: List<MyCharacter>)
    : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = givenValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(private val binding: CharacterLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MyCharacter) {

            binding.tvCharacterName.text = data.name
            binding.tvDescription.text = data.biography
            binding.cvMain.tag = data

            if (data.hasImg) {
                val mf = ManageFiles(itemView.context)
                binding.imgPreview.setImageBitmap(mf.getCharacterImage(data.wuid, data.uid))
            }

        }

    }

}