package com.antonioleiva.usecases.example_use

import com.antonioleiva.usecases.factory.UseCase

import io.reactivex.rxjava3.core.Single
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class SingleUseCase<T, in Params> : UseCase() {

    internal abstract fun buildUseCaseSingle(params: Params): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {},
        params: Params
    ) {
        disposeLast()
        lastDisposable = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)
        lastDisposable?.let { compositeDisposable.add(it) }
    }
}