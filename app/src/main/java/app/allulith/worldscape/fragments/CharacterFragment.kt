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
import app.allulith.worldscape.detail.WorldDetailActivity
import app.allulith.worldscape.utils.ManageFiles
import app.allulith.worldscape.utils.SetGradient
import app.allulith.worldscape.structure.MyCharacter
import kotlinx.android.synthetic.main.fragment_character.*

class CharacterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_character, container, false)
        val rvCharacter = root.findViewById(R.id.rvCharacters) as RecyclerView
        val fab = root.findViewById(R.id.fabCreateCharacter) as ExtendedFloatingActionButton
        rvCharacter.layoutManager = LinearLayoutManager(activity)
        val dataSet = loadCharacters()
        rvCharacter.adapter = CharacterAdapter(dataSet)

        val sl = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fab.shrink()
                } else {
                    fab.extend()
                }
            }
        }

        rvCharacter.addOnScrollListener(sl)

        SetGradient.assign(fab, root.context)

        return root
    }

    override fun onStart() {
        super.onStart()
        val dataSet = loadCharacters()
        rvCharacters.adapter = CharacterAdapter(dataSet)
    }

    private fun loadCharacters() : ArrayList<MyCharacter> {
        val mf = activity?.baseContext?.let { ManageFiles(it) }
        return mf?.getCharacters((activity as WorldDetailActivity).intent.getStringExtra("uid").toString(), -1) ?: ArrayList()
    }

}