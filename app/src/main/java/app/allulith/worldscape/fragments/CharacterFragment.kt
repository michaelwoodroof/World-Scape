package app.allulith.worldscape.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.allulith.worldscape.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import app.allulith.worldscape.adapters.CharacterAdapter
import app.allulith.worldscape.databinding.FragmentCharacterBinding
import app.allulith.worldscape.detail.WorldDetailActivity
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.utils.SetGradient
import app.allulith.worldscape.structure.MyCharacter

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)

        binding.rvCharacters.layoutManager = LinearLayoutManager(activity)
        val dataSet = loadCharacters()
        binding.rvCharacters.adapter = CharacterAdapter(dataSet)

        val sl = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fabCreateCharacter.shrink()
                } else {
                    binding.fabCreateCharacter.extend()
                }
            }
        }

        binding.rvCharacters.addOnScrollListener(sl)

        SetGradient.assign(binding.fabCreateCharacter, requireContext())

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataSet = loadCharacters()
        binding.rvCharacters.adapter = CharacterAdapter(dataSet)
    }

    private fun loadCharacters() : ArrayList<MyCharacter> {
        val mf = activity?.baseContext?.let { ManageFiles(it) }
        return mf?.getCharacters((activity as WorldDetailActivity).intent.getStringExtra("uid").toString(), -1) ?: ArrayList()
    }

}