package com.pes.pockles.view.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.pes.pockles.R
import com.pes.pockles.databinding.ActivityLoginBinding
import com.pes.pockles.model.PreferencesManager
import com.pes.pockles.view.ui.MainActivity


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
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )
        //Creates the custom layout and binds buttons to login methods
        val customLayout =
            AuthMethodPickerLayout.Builder(R.layout.activity_login)
                .setFacebookButtonId(binding.facebookButton.id)
                .setEmailButtonId(binding.emailbutton.id)
                .setGoogleButtonId(binding.googleButton.id)
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
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                user?.getIdToken(true)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        PreferencesManager.setToken(idToken)
                    }
                }
                //TODO Pick up the user and pass it to the profile
                //The user has logged in
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }

    //This method will be useful for User profile
    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // TODO Make something at log out
            }
        // [END auth_fui_signout]
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