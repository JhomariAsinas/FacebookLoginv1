package com.example.jhomasinas.fblogin.Base

import android.content.Context
import android.support.annotation.NonNull
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T>{

    protected var mView: T? = null
    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected var context : Context? = null

    fun attachView(@NonNull mView : T) {
        this.mView = mView
    }

    fun detachView() {
        mView = null;
        safelyDispose(disposables)
    }

    fun isViewAttached() : Boolean {
        return mView != null;
    }

    fun safelyDispose(compositeDisposable : CompositeDisposable) {
        compositeDisposable.dispose()
    }
}