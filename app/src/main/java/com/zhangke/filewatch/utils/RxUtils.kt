package com.zhangke.filewatch.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by ZhangKe on 2020/11/28.
 */

fun <T> Single<T>.toMaybeIgnoreError(): Maybe<T> {
    return Maybe.create { emitter ->
        this.subscribe({
            emitter.onSuccess(it)
        }, {
            //ignore
        })
    }
}

fun <T> Observable<T>.ioSubscribeUiObserve(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.ioSubscribeUiObserve(): Maybe<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.ioSubscribeUiObserve(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.ioSubscribeUiObserve(): Flowable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.ioSubscribeUiObserve(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Disposable.neverDispose() {
}
