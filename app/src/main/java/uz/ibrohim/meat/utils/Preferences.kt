package uz.ibrohim.meat.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("admin_caches", Context.MODE_PRIVATE)
    }

    fun setPreference(preferences: SharedPreferences) {
        Preferences.preferences = preferences
    }

    var token: String
        get() = preferences.getString(Preferences::token.name, "")!!
        set(value) {
            preferences.edit().putString(Preferences::token.name, value).apply()
        }

    var phone: String
        get() = preferences.getString(Preferences::phone.name, "")!!
        set(value) {
            preferences.edit().putString(Preferences::phone.name, value).apply()
        }
}