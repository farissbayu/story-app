package com.example.submissionuserstoryapp1.data.local

import android.content.Context

class AuthPreferences(context: Context) {
    companion object {
        private const val TOKEN_PREFS = "token_prefs"
        private const val TOKEN_VALUE = "token_value"
    }

    private val preferences = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN_VALUE, token)
        editor.apply()
    }

    fun getToken(): String? {
        val token = preferences.getString(TOKEN_VALUE, "")
        return token
    }

    fun clearToken() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}