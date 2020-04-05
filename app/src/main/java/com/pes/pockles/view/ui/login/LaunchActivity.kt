package com.pes.pockles.view.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.util.livedata.EventObserver
import com.pes.pockles.view.ui.MainActivity
import com.pes.pockles.view.ui.base.BaseActivity
import timber.log.Timber

class LaunchActivity : BaseActivity() {

    private val viewModel: LaunchActivityViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LaunchActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            createSignInIntent()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )
        //Creates the custom layout and binds buttons to login methods
        val customLayout =
            AuthMethodPickerLayout.Builder(R.layout.activity_login)
                .setFacebookButtonId(R.id.facebookButton)
                .setEmailButtonId(R.id.emailButton)
                .setGoogleButtonId(R.id.googleButton)
                .build()

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAuthMethodPickerLayout(customLayout)
                .setTheme(R.style.Login)
                .build(),
            RC_SIGN_IN
        )
    }

    // When login process finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                viewModel.userExists(user!!.uid).observe(this, Observer { value ->
                    when (value) {
                        is Resource.Success<Boolean> -> {
                            if (value.data) {
                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
//                                startActivity(Intent(this, RegisterActivity::class.java))
                                viewModel
                                    .registerUser("#ff0044", 860189821, 5)
                                    .observe(this, EventObserver(::doWhatever))
                            }
                        }
                        is Resource.Error -> Toast.makeText(
                            this,
                            "ME CAGO EN LA PUTA",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }

    private fun doWhatever(b: Boolean) {
        Timber.d("Register user went with value $b")
        if (b) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(
                this,
                "CREA TU PERFIL CERDO",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //This method will be useful for User profile
    private fun delete() {
        // full account delete
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // TODO Make something at removing the account
            }

    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}