package com.pes.pockles.view.ui.editpock

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.ActivityEditPockBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.view.ui.base.BaseActivity

class EditPockActivity : BaseActivity() {

    private lateinit var binding: ActivityEditPockBinding
    private val viewModel: EditPockViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EditPockViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_pock)
        binding.lifecycleOwner = this
        binding.editPockViewModel = viewModel

        // Add back-button to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        initializeObservers()

        binding.updatePockButton.setOnClickListener {
            viewModel.updatePock()
        }

        //Button to allow someone to set the category to General and to solve the problem with the dropdown
        binding.emptyCategoryButton.setOnClickListener {
            viewModel.unselectCategory()
        }

        val message = intent.getStringExtra("pock_message")
        val category = intent.getStringExtra("pock_category")
        val chatAccess = intent.getBooleanExtra("pock_chatAccess", true)
        viewModel.fillFields(message!!, category!!, chatAccess)

        val spinner = binding.categoriesDropdown
        spinner.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.categories)
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun handleSuccess() {
        hideLoading()
        Toast.makeText(this, resources.getString(R.string.updated_pock_message), Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun handleError(apiError: Boolean) {
        hideLoading()
        if (apiError)
            Toast.makeText(
                this,
                resources.getString(R.string.api_error_updating_message),
                Toast.LENGTH_SHORT
            )
                .show()
        else
            binding.pockContentField.error = resources.getString(R.string.pock_content_error)
    }

    private fun initializeObservers() {
        //It will handle the behavior of the app when we try to edit a pock
        viewModel.networkCallback.observe(
            this,
            Observer { value: Resource<Pock>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<*> -> handleSuccess()
                        is Resource.Error -> {
                            handleError(true)
                        }
                        is Resource.Loading -> showLoading()
                    }
                }
            })
        //In case there are any error
        viewModel.errorHandlerCallback.observe(
            this,
            Observer { value: Boolean ->
                value.let {
                    if (value)
                        handleError(false)
                }
            })
    }

    private fun showLoading() {
        val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )

        binding.editPockProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.editPockProgressBar.visibility = View.GONE
    }

}