package org.example.project.data.Preferences

interface PreferencesApi {
    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)
}