package com.pes.pockles.view.ui.newpock

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityNewPockBinding

class NewPockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPockBinding
    private val viewModel: NewPockViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(NewPockViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_pock)
        binding.lifecycleOwner = this
        binding.newPockViewModel = viewModel

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
