package com.pes.pockles.view.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityNewPockBinding

class NewPockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_pock)

        val categories = resources.getStringArray(R.array.categories)

        val spinner = binding.categoriesDropdown
        spinner?.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, categories
            )
        )
    }

}
