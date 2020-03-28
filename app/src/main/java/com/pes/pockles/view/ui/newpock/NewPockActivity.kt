package com.pes.pockles.view.ui.newpock

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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

        initializeObservers()

        binding.closeButton.setOnClickListener {
            finish()
        }

        val spinner = binding.categoriesDropdown
        spinner?.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.categories)
            )
        )
    }

    private fun handleSuccess() {
        Toast.makeText(this, resources.getString(R.string.added_pock_message), Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun handleError(apiError: Boolean) {
        if (apiError)
            Toast.makeText(
                this,
                resources.getString(R.string.api_error_message),
                Toast.LENGTH_SHORT
            )
                .show()
        else
            binding.pockContentField.error = resources.getString(R.string.pock_content_error)
    }

    private fun initializeObservers() {
        //It will handle the behavior of the app when we try to insert a pock into DB
        viewModel.networkCallback.observe(
            this,
            Observer { value: Resource<Pock>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<*> -> handleSuccess()
                        is Resource.Error -> {
                            handleError(true)
                        }
                    }
                }
            })

        //Closes keyboard when the pock is being inserted to DB
        viewModel.keyboardCallback.observe(
            this,
            Observer { value: Boolean ->
                value?.let {
                    if (value) {
                        val inputManager: InputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(
                            currentFocus?.windowToken,
                            InputMethodManager.SHOW_FORCED
                        )
                    }

                }
            }
        )
        //In case there are any error
        viewModel.errorHandlerCallback.observe(
            this,
            Observer { value: Boolean ->
                value?.let {
                    if (value)
                        handleError(false)
                }
            })
    }

}
