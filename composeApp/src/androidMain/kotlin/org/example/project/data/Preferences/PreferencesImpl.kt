package org.example.project.data.Preferences

import android.content.Context
import android.content.SharedPreferences

actual class PreferencesImpl(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

      actual fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

     actual fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

}