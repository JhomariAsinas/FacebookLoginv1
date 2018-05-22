package com.example.jhomasinas.fblogin.Base

interface BaseView{
    fun showMessage(message: String)
    fun showLoading(message: String)
    fun removeLoading()
}