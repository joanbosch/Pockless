package com.pes.pockles.view.ui.settings


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.view.MenuItem
import com.pes.pockles.R
import com.pes.pockles.view.ui.base.BaseActivity
import com.pes.pockles.view.ui.editprofile.EditProfileActivity


class SettingsActivity : BaseActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    // Can be useful to get the shared preferences.
    private lateinit var sharedPref: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        // This should add the top bar to go back...
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)

        setTitle(R.string.settings)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "Language") {
            /*
            MUST IMPLEMENTS THE LANGUAGE CHANGE
             */
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)

            findPreference("AboutUs")
                .setOnPreferenceClickListener {
                    val intent = Intent(activity, EditProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnPreferenceClickListener true
                }
        }
    }

}