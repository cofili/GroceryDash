package com.example.groceryapp.helpers

import com.example.groceryapp.models.User
import android.content.Context

class SessionManager(var mContext: Context) {


    private val FILE_NAME = "grocery_pref"
    private val KEY_ID = "id"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_NAME = "name"
    private val KEY_MOBILE = "mobile"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun register(user: User) {
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PASSWORD, user.password)

        editor.commit()

    }

    fun addUser(user: User){
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putString(KEY_NAME, user.firstName)
        editor.putString(KEY_MOBILE, user.mobile)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)

        editor.commit()
    }

    fun getUser(): User{
        var userId = sharedPreferences.getString(KEY_ID, null)
        var email = sharedPreferences.getString(KEY_EMAIL, null)
        var name = sharedPreferences.getString(KEY_NAME, null)
        var mobile = sharedPreferences.getString(KEY_MOBILE, null)
        var password = sharedPreferences.getString(KEY_PASSWORD, null)


        return User(userId!!, email!!, name!!, mobile!!, password!!)
    }
}