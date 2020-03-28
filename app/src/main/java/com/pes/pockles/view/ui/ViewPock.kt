package com.pes.pockles.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.pes.pockles.R
import com.pes.pockles.databinding.ViewPockBinding

class ViewPock : Fragment() {

    private lateinit var binding: ViewPockBinding

    private lateinit var viewModel: ViewPockViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.view_pock,
            container,
            false
        )

        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(ViewPockViewModel::class.java)

        binding.pockViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        var id: String = ViewPockArgs.fromBundle(arguments!!).pockIdArg
        Toast.makeText(context, "Id: ${id}", Toast.LENGTH_LONG).show()

        viewModel.loadPock(id)

        // Observer for the Game finished event
        viewModel.goBack.observe(viewLifecycleOwner, Observer<Boolean> { backButtonPressed ->
            if (backButtonPressed) goBack()
        })

        viewModel.goShare.observe(viewLifecycleOwner, Observer<Boolean> { shareButtonPressed ->
            if (shareButtonPressed) goShare()
        })

        viewModel.goReport.observe(viewLifecycleOwner, Observer<Boolean> { reportButtonPressed ->
            if (reportButtonPressed) goReport()
        })

        viewModel.goChat.observe(viewLifecycleOwner, Observer<Boolean> { chatButtonPressed ->
            if (chatButtonPressed) goChat()
        })
        return binding.root

    }

    fun goBack(){
        Toast.makeText(activity, "Pock View is closed!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(ViewPockDirections.actionViewPockToMapFragment())
    }

    fun goShare() {
        Toast.makeText(activity, "Loading share options", Toast.LENGTH_SHORT).show()
        shareSuccess()
    }

    fun goReport() {
        Toast.makeText(activity, "Report function not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    fun goChat() {
        Toast.makeText(activity, "Chat function not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, viewModel.pockMessage.value.toString())
        return shareIntent
    }

}