package org.example.project.data.Preferences

actual class PreferencesImpl  {
//    private val userDefaults = NSUserDefaults.standardUserDefaults()
//
//    actual override fun getString(key: String, defaultValue: String): String {
//        return userDefaults.stringForKey(key) ?: defaultValue
//    }
//
//    actual override fun putString(key: String, value: String) {
//        userDefaults.setObject(value, forKey = key)
//    }
     fun getString(key: String): String? {
        TODO("Not yet implemented")
    }

     fun putString(key: String, value: String) {
        TODO("Not yet implemented")
    }
}