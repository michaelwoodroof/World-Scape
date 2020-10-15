package com.michaelwoodroof.worldscape.ui.create_character

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.michaelwoodroof.worldscape.R
import kotlinx.android.synthetic.main.fragment_create_character_s1.*

class CreateCharacterFragmentS1 : Fragment() {

    lateinit var uriPointer : Uri
    lateinit var rootParent : View

    companion object {
        // Intent Codes
        private const val MEDIA_PICK_CODE = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_character_s1, container, false)
        rootParent = root

        addAnimation(root)

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

        return root
    }

    private fun addAnimation(root : View) {
        root.findViewById<ExtendedFloatingActionButton>(R.id.fabPickImageCC).setOnClickListener {
            // Only Animate if Condition is Met
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MEDIA_PICK_CODE) {
                val selectedImage = data?.data
                if (selectedImage != null) {
                    imgPreviewCC.setImageURI(selectedImage)
                    uriPointer = selectedImage
                    imgPreviewCC.tag = "hasImage"
                    cvPreviewCC.visibility = View.VISIBLE
                    animatePickImage()
                }
            }
        }
    }

    private fun animatePickImage() {
        if (fabPickImageCC.tag != "run") {
            val r2 = Runnable {
                // Convert to Circle
                fabPickImageCC.shrink()
            }

            r2.run {
                Handler().postDelayed(r2, 400)
            }

            fabPickImageCC.tag = "run"
        }
    }

}