package app.allulith.worldscape.createcharacter.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import app.allulith.worldscape.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import app.allulith.worldscape.createcharacter.CreateCharacterActivity
import app.allulith.worldscape.databinding.FragmentCreateCharacterS1Binding

class CreateCharacterFragmentS1 : Fragment() {

    private lateinit var binding: FragmentCreateCharacterS1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCreateCharacterS1Binding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= 23) {
            val sl = View.OnScrollChangeListener { _, _, sy, _, osy ->
                if (sy > osy) {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.shrink()
                } else {
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fabNext)?.extend()
                }
            }

            binding.svCreateCharacter.setOnScrollChangeListener(sl)
        }


        binding.btnPickImageCC.setOnClickListener {
            pickImage()
        }

        binding.btnPickImageCCShrunk.setOnClickListener {
            pickImage()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreateCharacterActivity).setUpFocusChangers(0)
        (activity as CreateCharacterActivity).setUpTextChangers(0)
    }

    override fun onStart() {
        super.onStart()
        binding.btnLinkCurrentLoc.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.avd_link_to_warning, null))
        binding.btnLinkCurrentLoc.tag = "ne"
        binding.btnLinkPlace.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.avd_link_to_warning, null))
        binding.btnLinkPlace.tag = "ne"

        val drawableOne = binding.btnLinkCurrentLoc.drawable as AnimatedVectorDrawable
        val drawableTwo = binding.btnLinkPlace.drawable as AnimatedVectorDrawable
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
                    binding.imgPreviewCC.setImageURI(selectedImage)
                    (activity as CreateCharacterActivity).uriPointer = selectedImage
                    binding.imgPreviewCC.tag = "hasImage"
                    binding.cvPreviewCC.visibility = View.VISIBLE
                    binding.btnPickImageCC.visibility = View.GONE
                    binding.btnPickImageCCShrunk.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

}