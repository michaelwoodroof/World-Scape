package com.michaelwoodroof.worldscape.ui.create_character

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R
import kotlinx.android.synthetic.main.fragment_create_character_s1.*

class CreateCharacterFragmentS1 : Fragment() {

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_character_s1, container, false)

        if (Build.VERSION.SDK_INT >= 23) {
            val sl = View.OnScrollChangeListener { _, _, sy, _, osy ->
                if (sy > osy) {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.shrink()
                } else {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.extend()
                }
            }

            val sv = root.findViewById<ScrollView>(R.id.svCreateCharacter)
            sv.setOnScrollChangeListener(sl)
        }

        val btnPickImage = root.findViewById<AppCompatButton>(R.id.btnPickImageCC)
        val btnPickImageShrunk = root.findViewById<AppCompatButton>(R.id.btnPickImageCCShrunk)

        btnPickImage.setOnClickListener {
            pickImage()
        }

        btnPickImageShrunk.setOnClickListener {
            pickImage()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(0)
        (activity as CreateCharacterActivity).setUpTextChangers(0)
    }

    override fun onStart() {
        super.onStart()
        btnLinkCurrentLoc.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.link_to_warning, null))
        btnLinkCurrentLoc.tag = "ne"
        btnLinkPlace.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.link_to_warning, null))
        btnLinkPlace.tag = "ne"
        val drawableOne = btnLinkCurrentLoc.drawable as AnimatedVectorDrawable
        val drawableTwo = btnLinkPlace.drawable as AnimatedVectorDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableOne.reset()
            drawableTwo.reset()
        }
    }

    override fun onResume() {
        (activity as CreateCharacterActivity).h.removeCallbacksAndMessages(null)
        (activity as CreateCharacterActivity).resetFields(0)
        (activity as CreateCharacterActivity).fillFields(0)
        super.onResume()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ("image/*")

        // Mime Types
        val acceptedTypes = ArrayList<String>()
        acceptedTypes.add("image/png")
        acceptedTypes.add("image/jpg")
        acceptedTypes.add("image/jpeg")

        intent.putExtra(Intent.EXTRA_MIME_TYPES, acceptedTypes)
        startActivityForResult(intent, MEDIA_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MEDIA_PICK_CODE) {
                val selectedImage = data?.data
                if (selectedImage != null) {
                    imgPreviewCC.setImageURI(selectedImage)
                    (activity as CreateCharacterActivity).uriPointer = selectedImage
                    imgPreviewCC.tag = "hasImage"
                    cvPreviewCC.visibility = View.VISIBLE
                    btnPickImageCC.visibility = View.GONE
                    btnPickImageCCShrunk.visibility = View.VISIBLE
                }
            }
        }
    }

}