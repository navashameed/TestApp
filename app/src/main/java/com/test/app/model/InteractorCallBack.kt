package com.test.app.model

interface InteractorCallBack<R> {
    fun onSuccess(response: R)
    fun onFailure(throwable: Throwable)
}
