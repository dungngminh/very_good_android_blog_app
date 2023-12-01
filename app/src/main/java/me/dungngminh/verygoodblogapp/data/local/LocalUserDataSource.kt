package me.dungngminh.verygoodblogapp.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalUserDataSource
    @Inject
    constructor(private val pref: SharedPreferences) {
        private companion object {
            private const val JWT_KEY = "JWT"
            private const val USERID_KEY = "USERID"
        }

        var jwt: String?
            get() = pref.getString(JWT_KEY, null)
            set(value) {
                pref.edit { putString(JWT_KEY, value) }
            }

        var userId: String?
            get() = pref.getString(USERID_KEY, null)
            set(value) {
                pref.edit { putString(USERID_KEY, value) }
            }

        fun deleteAll() {
            pref.edit {
                clear()
            }
        }
    }
