package com.michaelwoodroof.worldscape.helper

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton

object AssignTouchEvent {

    fun assignTouch(view : View, event : MotionEvent, cd1 : AnimatedVectorDrawable, cd2 : AnimatedVectorDrawable) : Boolean {
        val chosenDrawable : AnimatedVectorDrawable
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if (view.tag == "na") {
                    val b = view as ImageButton
                    chosenDrawable = cd1
                    b.tag = "es"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (view.tag == "es") {
                    val b = view as ImageButton

                    val xE = event.x
                    val yE = event.y

                    if ((xE < 0 || xE > b.width || yE < 0 || yE > b.height)) {
                        chosenDrawable = cd2
                        b.tag = "na"
                        b.setImageDrawable(chosenDrawable)
                        chosenDrawable.start()
                    }
                }
                return false
            }
            MotionEvent.ACTION_UP -> {
                if (view.tag == "es") {
                    val b = view as ImageButton
                    chosenDrawable = cd2
                    b.tag = "na"
                    b.setImageDrawable(chosenDrawable)
                    chosenDrawable.start()

                    val xE = event.x
                    val yE = event.y

                    if (!(xE < 0 || xE > b.width || yE < 0 || yE > b.height)) {
                        // Is Within Bounds
                        view.performClick()
                    }
                }
                return false
            }
            else -> {return false}
        }
    }

    fun swipeToDelete(view : View, event : MotionEvent) : Boolean {
        return true
    }

}