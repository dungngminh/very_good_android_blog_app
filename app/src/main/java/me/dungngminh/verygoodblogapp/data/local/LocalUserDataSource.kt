package me.dungngminh.verygoodblogapp.data.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface LocalUserDataSource {
    fun getJwt() : Observable<String>

    fun getUserId(): Observable<String>

    fun saveTokenAndId(jwt: String, userId: String) : Completable

    fun deleteAll() : Completable
}