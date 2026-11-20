package com.example.cashapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserRepository (val context: Context){
    companion object{
        val ONBOARDING = booleanPreferencesKey("is_finished")
        val IS_REGISTERED = booleanPreferencesKey("is_registered")

        val IS_LOGIN = booleanPreferencesKey("is_login")
    }

    val onBoardingFinished: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING] ?: false
        }

    suspend fun setOnBoardingFinished(finished: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING] = finished
        }
    }

    val isRegistered: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_REGISTERED] ?: false
        }

    suspend fun setRegistered(registered: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_REGISTERED] = registered
        }
    }

    val isLogin: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGIN] ?: false
        }

    suspend fun setLogin(registered: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN] = registered
        }
    }

}