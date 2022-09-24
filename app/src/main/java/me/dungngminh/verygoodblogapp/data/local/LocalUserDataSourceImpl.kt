package me.dungngminh.verygoodblogapp.data.local

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(private val pref: SharedPreferences) : LocalUserDataSource {
    private val prefSubject = BehaviorSubject.createDefault(pref);

    private companion object {
        private const val JWT_KEY = "JWT"
        private const val USERID_KEY = "USERID"

    }
    override fun getJwt(): Observable<String> = prefSubject.map { it.getString(JWT_KEY, "")!! }

    override fun getUserId(): Observable<String> = prefSubject.map { it.getString(USERID_KEY, "")!! }

    // Save JWT and userId be like
    override fun saveTokenAndId(jwt: String, userId: String): Completable =
        prefSubject.firstOrError().editSharedPreferences {
            putString(USERID_KEY, userId)
            putString(JWT_KEY, jwt)
        }

    override fun deleteAll(): Completable = prefSubject.firstOrError().clearSharedPreferences {
        remove(
            USERID_KEY)
        remove(JWT_KEY)
    }

    fun Single<SharedPreferences>.editSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
        flatMapCompletable {
            Completable.fromAction {
                it.edit().also(batch).apply()
            }
        }

    fun Single<SharedPreferences>.clearSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
        flatMapCompletable {
            Completable.fromAction {
                it.edit().also(batch).apply()
            }
        }

}