package app.allulith.worldscape.adapters

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
import app.allulith.worldscape.R
import app.allulith.worldscape.databinding.WorldLayoutBinding
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.structure.World

class WorldAdapter(private val givenValues: List<World>)
    : RecyclerView.Adapter<WorldAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorldLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = givenValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = givenValues.size

    class ViewHolder(private val binding: WorldLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: World) {

            binding.tvTitle.text = data.title
            binding.tvDesc.text = data.desc
            binding.clCard.tag = data.uid
            binding.btnDelete.tag = data.uid
            itemView.tag = data.uid + "," + data.title
            binding.cvMain.tag = data

            if (data.hasImg) {
                val mf = ManageFiles(itemView.context)
                binding.imgPreview.setImageBitmap(mf.getWorldImage(data.uid))
            }

            when (data.genre) {

                "Fantasy" -> {
                    binding.imgGenre.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_genre_fantasy,
                            null
                        )
                    )
                }

                "Horror" -> {
                    binding.imgGenre.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_genre_horror,
                            null
                        )
                    )
                }

                "Sci-Fi" -> {
                    binding.imgGenre.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_genre_scifi,
                            null
                        )
                    )
                }

                else -> {
                    binding.imgGenre.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_genre_other,
                            null
                        )
                    )
                }

            }

        }

    }

    companion object {
        const val cardHeight: Int = 167
    }

}
