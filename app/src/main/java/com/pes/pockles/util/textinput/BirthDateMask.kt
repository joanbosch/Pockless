package com.pes.pockles.util.textinput

import com.google.android.material.textfield.TextInputLayout

class BirthDateMask constructor(private val inputLayout: TextInputLayout) :
    CleanErrorWatcher(inputLayout) {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)


    }
}