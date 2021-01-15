package com.michaelwoodroof.worldscape.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.michaelwoodroof.worldscape.CreateCharacterActivity
import com.michaelwoodroof.worldscape.R
import com.michaelwoodroof.worldscape.helper.AssignTouchEvent

class StatDialogFragment : DialogFragment() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_stat_sheet, container, false)
        val toolbar = root.findViewById<ConstraintLayout>(R.id.incToolbarStat)
        val title = toolbar.findViewById<TextView>(R.id.tvTitle)
        title.text = getString(R.string.stats)
        val close = toolbar.findViewById<ImageButton>(R.id.btnClose)
        close.setOnClickListener {
            this.dismiss()
            (activity as CreateCharacterActivity).popStack()
            (activity as CreateCharacterActivity).isDialogLoaded = false
        }

        close.setOnTouchListener(View.OnTouchListener() { view, event ->
            return@OnTouchListener AssignTouchEvent.assignTouch(
                view as ImageButton, event,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.close_expand,
                    null
                ) as AnimatedVectorDrawable,
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.close_shrink,
                    null
                ) as AnimatedVectorDrawable
            )
        })

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}