package com.pes.pockles.view.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.pes.pockles.R

import kotlinx.android.synthetic.main.activity_new_pock.*

class NewPockActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pock)
        setSupportActionBar(toolbar)


    }

}
