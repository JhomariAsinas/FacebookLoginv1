package com.example.jhomasinas.fblogin.FacebookLogin


import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.jhomasinas.fblogin.BuildConfig
import com.example.jhomasinas.fblogin.MainpageActivity.HomeActivity
import com.example.jhomasinas.fblogin.R
import com.facebook.*
import com.facebook.login.LoginManager
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class LoginActivity : AppCompatActivity(), LoginContract.LoginView {


    var presenter : LoginPresenter? = null
    lateinit var callbackManager: CallbackManager

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }

    private var progressDialog : ProgressDialog? = null

    override fun onSaveSuccess() {
        startActivity(intentFor<HomeActivity>())
    }

    override fun onLoginSuccess(response: String, state: Boolean) {
        if(state){
            presenter?.onSaveUser(this)
        }else{
            showMessage(response)
        }
    }

    override fun onLogoutTrue() {

    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showLoading(message: String) {
        progressDialog = indeterminateProgressDialog("Fetching Product")
        progressDialog?.setCancelable(false)
        progressDialog?.show()

    }

    override fun removeLoading() {
        progressDialog?.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(this)
        fbLogin()
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.login_button)

        btnLogin.setOnClickListener { view ->
            presenter?.onLogin(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

    fun fbLogin(){
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true)
        }
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager!!,presenter)
    }
}
