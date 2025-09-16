package com.jrexl.novanector.objectBuild

import android.app.Activity
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreManager {
    private val Context.dataStore by preferencesDataStore(name = "userData")

    private val userName = stringPreferencesKey("userName")
    private val userPhone = stringPreferencesKey("userPhone")
    private val userEmail = stringPreferencesKey("userEmail")
    private var userProfilePic = stringPreferencesKey("user_profile_pic")

    suspend fun saveUser(context: Context, name: String,phone:String,email:String, profilePicUrl: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[userName] = name
            prefs[userPhone] = phone
            prefs[userEmail] = email
            prefs[userProfilePic] = profilePicUrl
        }
    }
    suspend fun getUserName(context: Context): String? {
        return context.dataStore.data.map { it[userName] ?: "" }.first()
    }
    suspend fun getProfilePic(context: Context): String {
        return context.dataStore.data.map { it[userProfilePic] ?: "" }.first()
    }


    suspend fun getUserEmail(context: Context): String? {
        return context.dataStore.data.map { it[userEmail] ?: "" }.first()
    }
    suspend fun getUserPhone(context: Context): String? {
        return context?.dataStore?.data?.map { it[userPhone] ?: "" }?.first()
    }



}
