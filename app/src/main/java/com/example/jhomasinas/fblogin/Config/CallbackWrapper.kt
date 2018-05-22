package com.example.jhomasinas.fblogin.Config

import com.example.jhomasinas.fblogin.Base.BaseResponse
import io.reactivex.observers.DisposableObserver

abstract class CallbackWrapper <T : BaseResponse> : DisposableObserver<T>() {

    abstract protected fun onSuccess(t: T)
    abstract protected fun onBegin()

    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        onBegin()
        super.onStart()
    }
}