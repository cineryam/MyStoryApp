package com.example.mystoryapp.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mystoryapp.R

class CustomEditText: AppCompatEditText, View.OnTouchListener {
    private lateinit var icError: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        icError = ContextCompat.getDrawable(context, R.drawable.ic_error) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateErrorState()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

    fun isInputValid(): Boolean {
        val text = text?.toString() ?: ""
        return text.length >= 8
    }

    private fun updateErrorState() {
        val text = text?.toString() ?: ""
        if (text.length < 8) {
            setError(context.getString(R.string.password_too_short), icError)
            setCompoundDrawablesWithIntrinsicBounds(null, null, icError, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            setError(null)
        }
    }
}