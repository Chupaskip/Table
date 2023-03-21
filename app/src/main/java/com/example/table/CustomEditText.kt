package com.example.table

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.util.AttributeSet
import android.widget.Toast

class CustomEditText(context: Context, attributeSet: AttributeSet? = null) :
    androidx.appcompat.widget.AppCompatEditText(context, attributeSet) {


    @SuppressLint("WrongConstant")
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val focus = (context as? Activity)?.currentFocus
        text?.also {
            if (it.toString() != "") {
                if (text.toString().toInt() !in 0..5) {
                    this.setBackgroundColor(Color.RED)
                    Toast.makeText(context, "Недопустимое значение!", Toast.LENGTH_SHORT).show()
                } else {
                    this.setBackgroundColor(Color.WHITE)
                    focus?.focusSearch(FOCUS_FORWARD)?.requestFocus()
                }
            } else {
                this.setBackgroundColor(Color.WHITE)
            }
        }
    }

    override fun setFilters(filters: Array<out InputFilter>?) {
        super.setFilters(arrayOf(InputFilter.LengthFilter(1)))
    }
}