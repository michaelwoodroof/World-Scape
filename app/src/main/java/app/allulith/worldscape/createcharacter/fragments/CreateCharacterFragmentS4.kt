package app.allulith.worldscape.createcharacter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.allulith.worldscape.createcharacter.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R

class CreateCharacterFragmentS4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_character_s4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(3)
        (activity as CreateCharacterActivity).setUpTextChangers(3)
    }

    override fun onResume() {
        (activity as CreateCharacterActivity).h.removeCallbacksAndMessages(null)
        (activity as CreateCharacterActivity).fillFields(3)
        (activity as CreateCharacterActivity).resetFields(3)
        super.onResume()
    }

}