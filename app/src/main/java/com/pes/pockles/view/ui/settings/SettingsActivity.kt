package com.pes.pockles.view.ui.settings


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.pes.pockles.R
import com.pes.pockles.view.ui.aboutus.AboutUsActivity
import com.pes.pockles.view.ui.base.BaseActivity


class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
        // Add Top Bar
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowHomeEnabled(true)
        setTitle(R.string.settings)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            //   setResult(Activity.RESULT_OK)
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    class SettingsFragment : PreferenceFragment() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
            findPreference("AboutUs")
                .setOnPreferenceClickListener {
                    val intent = Intent(activity, AboutUsActivity::class.java)
                    startActivity(intent)
                    return@setOnPreferenceClickListener true
                }
           val lp = findPreference("Language") as ListPreference;
                lp.setOnPreferenceClickListener {
                    activity.setResult(Activity.RESULT_OK)
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, R.string.changedLanguage, duration)
                    toast.show()
                    return@setOnPreferenceClickListener true
                }
        }
    }

}