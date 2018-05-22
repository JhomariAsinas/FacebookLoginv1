package com.example.jhomasinas.fblogin.Config

import android.content.Context

class SharedPref private constructor(context: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
                return true
            } else {
                return false
            }
        }

    val codeProd: String?
        get() {
            val sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_FULLNAME, null)
        }

    init {
        con = context
    }

    fun saveUser(username: String, email: String, birth: String, fullname: String, picture: String): Boolean {

        val sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()

        edit.putString(KEY_USERNAME, username)
        edit.putString(KEY_EMAIL, email)
        edit.putString(KEY_FULLNAME, fullname)
        edit.putString(KEY_PICTURE, picture )
        edit.putString(KEY_BIRTHDATE, birth)

        edit.apply()

        return true
    }



    fun logout(): Boolean {
        val sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }

    companion object {
        private var mInstance: SharedPref? = null
        private lateinit var con: Context

        private val SHARED_PREF_NAME = "sharedpref1212"
        private val KEY_USERNAME  = "username"
        private val KEY_FULLNAME  = "fullname"
        private val KEY_PICTURE   = "picture"
        private val KEY_EMAIL     =   "email"
        private val KEY_BIRTHDATE = "birthdate"

        @Synchronized
        fun getmInstance(context: Context): SharedPref {
            if (mInstance == null) {
                mInstance = SharedPref(context)
            }
            return mInstance as SharedPref
        }
    }
}