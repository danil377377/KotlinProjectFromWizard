package org.example.project.data.Preferences

import platform.Foundation.NSUserDefaults

actual class PreferencesImpl {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    actual fun getString(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    actual fun putString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }
}