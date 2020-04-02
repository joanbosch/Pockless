package com.pes.pockles.view.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityLoginBinding
import com.pes.pockles.model.PreferencesManager
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        createSignInIntent()
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
//            AuthUI.IdpConfig.TwitterBuilder().build())
        )

        val customLayout =
            AuthMethodPickerLayout.Builder(R.layout.activity_login)
                .setFacebookButtonId(binding.facebookButton.id)
                .setEmailButtonId(binding.emailbutton.id)
                .setPhoneButtonId(binding.telephopnebutton.id)
                .build()

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                user?.getIdToken(true)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        PreferencesManager.setToken(idToken)
                        Timber.d(idToken)
                        // Send token to the backend???
                    } else { // Handle error -> task.getException() and make things;
                    }
                }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // TODO Make something at log out
            }
        // [END auth_fui_signout]
    }

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