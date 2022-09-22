package me.dungngminh.verygoodblogapp.utils

import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject

inline fun <T : Any> Relay<T>.asObservable(): Observable<T> = this

inline fun <T : Any> Subject<T>.asObservable(): Observable<T> = this

inline fun <T : Any, R : Any> Observable<T>.exhaustMap(crossinline transform: (T) -> Observable<R>): Observable<R> {
    return this
        .toFlowable(BackpressureStrategy.DROP)
        .flatMap({ transform(it).toFlowable(BackpressureStrategy.MISSING) }, 1)
        .toObservable()
}

inline fun <T : Any, S : Any> Observable<T>.mapNotNull(
    crossinline transform: (T) -> S?
): Observable<S> {
    return this
        .flatMap {
            val result = transform(it)
            if (result == null) {
                Observable.empty()
            } else {
                Observable.just(result)
            }
        }
}