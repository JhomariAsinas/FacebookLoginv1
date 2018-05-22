package com.example.jhomasinas.fblogin.FacebookLogin

import android.app.Activity
import com.example.jhomasinas.fblogin.Base.BasePresenter
import com.example.jhomasinas.fblogin.Config.CallbackWrapper
import com.example.jhomasinas.fblogin.Config.SharedPref
import com.example.jhomasinas.fblogin.FacebookLogin.LoginContract.LoginView
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class LoginPresenter(activity: Activity) : BasePresenter<LoginView>(), LoginContract.LoginAction , FacebookCallback<LoginResult> {

    private val activity: Activity
    private val loginView : LoginContract.LoginView? = null
    private var token : String? = null

    init {
        this.activity = activity
    }

    val productApiserve by lazy {
        FacebookAPI.create()
    }

    override fun onLogin(activity1: Activity) {
//        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email","public_profile"))
        mView?.showMessage("Login")
    }

    override fun onSaveUser(activity2: Activity) {
        disposables.add(productApiserve.getfields(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<FacebookAPI.FBProfile>(){

                    override fun onBegin() {
                        mView?.showLoading("Fetching Product")
                    }

                    override fun onSuccess(t: FacebookAPI.FBProfile) {
                        val username = "@${t.fname} ${t.lname}"
                        SharedPref.getmInstance(activity2).saveUser(username,t.email!!,t.birthday!!,t.name!!,t.picture?.data?.url!!)

                    }

                }))
    }

    override fun onLogout() {

    }

    override fun onSuccess(result: LoginResult?) {
        if(result != null){
            result?.run {
                token  = result.accessToken.token
                mView?.onLoginSuccess("Successfully Login",true)
            }
        }else{
            mView?.onLoginSuccess("Failed to Login Using Facebook",false)
        }
    }

    override fun onCancel() {
        mView?.showMessage("Facebook Login Cancel")
    }

    override fun onError(error: FacebookException?){
        mView?.showMessage("Facebook Login Error")
    }

    }