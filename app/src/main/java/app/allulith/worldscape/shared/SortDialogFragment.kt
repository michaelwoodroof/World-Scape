package app.allulith.worldscape.shared

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.michaelwoodroof.worldscape.R
import app.allulith.worldscape.utils.assignTouch

class SortDialogFragment: DialogFragment() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_dialog_sort, container, false)
        val toolbar = root.findViewById<ConstraintLayout>(R.id.incToolbarSort)

        val title = toolbar.findViewById<TextView>(R.id.tvTitle)
        title.text = "World Scape"

        val button = toolbar.findViewById<Button>(R.id.btnSave)
        button.text = "SORT"

        val close = toolbar.findViewById<ImageButton>(R.id.btnClose)
        close.setOnClickListener {
            this.dismiss()
        }
        close.setOnTouchListener(View.OnTouchListener { view, event ->
            return@OnTouchListener assignTouch(
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

}