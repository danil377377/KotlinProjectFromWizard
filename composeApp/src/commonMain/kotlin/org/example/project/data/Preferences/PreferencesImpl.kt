package org.example.project.data.Preferences

expect class PreferencesImpl{
    fun getString(key: String): String?

    fun putString(key: String, value: String)
}