package com.pes.pockles.view.ui.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import timber.log.Timber
import java.util.*

class BaseContextWrapper(base: Context?) : ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, locale: String): ContextWrapper {
            var con = context
            Timber.i(locale)
            val resources = context.resources
            val configuration = resources.configuration
            val newLocale = Locale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                con = context.createConfigurationContext(configuration)
            } else {
               // con = context.createConfigurationContext(configuration)
                configuration.setLocale(newLocale)
                resources.updateConfiguration(configuration, null)
                val sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(con)
                val name = sharedPreferences.getString("Language", "")
                Timber.i(name)
            }
            return ContextWrapper(con)
        }
    }
}