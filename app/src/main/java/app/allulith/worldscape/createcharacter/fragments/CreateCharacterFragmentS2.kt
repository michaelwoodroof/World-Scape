package app.allulith.worldscape.createcharacter.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import app.allulith.worldscape.createcharacter.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R

class CreateCharacterFragmentS2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_character_s2, container, false)

        if (Build.VERSION.SDK_INT >= 23) {
            val sl = View.OnScrollChangeListener { _, _, sy, _, osy ->
                if (sy > osy) {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.shrink()
                } else {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.extend()
                }
            }

            val sv = root.findViewById<ScrollView>(R.id.svCreateCharacterS2)
            sv.setOnScrollChangeListener(sl)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(1)
        (activity as CreateCharacterActivity).setUpTextChangers(1)
    }

    override fun onResume() {
        (activity as CreateCharacterActivity).h.removeCallbacksAndMessages(null)
        (activity as CreateCharacterActivity).fillFields(1)
        (activity as CreateCharacterActivity).resetFields(1)
        super.onResume()
    }

}