package com.example.jhomasinas.fblogin.FacebookLogin

import android.app.Activity
import com.example.jhomasinas.fblogin.Base.BaseView

interface LoginContract {

    interface LoginView : BaseView {
        fun onLoginSuccess(response: String,state:Boolean)
        fun onLogoutTrue()
        fun onSaveSuccess()
    }

    interface LoginAction {
        fun onLogin(activity: Activity)
        fun onSaveUser(activity1: Activity)
        fun onLogout()
    }

}