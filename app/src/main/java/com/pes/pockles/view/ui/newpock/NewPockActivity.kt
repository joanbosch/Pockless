package com.pes.pockles.view.ui.newpock

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityNewPockBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.viewmodel.ViewModelFactory

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

        viewModel.networkCallback?.observe(
            this,
            Observer { value: Resource<Pock>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<*> -> handleSuccess()
                        is Resource.Error -> handleError(value.exception)
                    }
                }
            })

        val categories = resources.getStringArray(R.array.categories)

        val spinner = binding.categoriesDropdown
        spinner?.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, categories
            )
        )
    }

    private fun handleSuccess() {
        Toast.makeText(this, "Pock añadido", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

}
