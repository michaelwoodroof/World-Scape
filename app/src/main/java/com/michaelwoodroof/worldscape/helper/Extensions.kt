package com.michaelwoodroof.worldscape.helper

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText


fun Int.toPixels(): Int = (this * Resources.getSystem().displayMetrics.density.toInt())

fun Int.toDP(): Int = (this / Resources.getSystem().displayMetrics.density.toInt())

fun String.toEditable() : Editable {
    return Editable.Factory.getInstance().newEditable(this)
}

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(e: Editable?) {
            afterTextChanged.invoke(e.toString())
        }

    })
}

fun AutoCompleteTextView.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(e: Editable?) {
            afterTextChanged.invoke(e.toString())
        }

    })
}

