package com.example.umc_stepper.token

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.umc_stepper.utils.extensions.datastore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val YOUTUBE_TOKEN_KEY = stringPreferencesKey("youtube_token")
        private val COOKIE = stringPreferencesKey("cookie")
        private val EXERCISE_ID = stringPreferencesKey("exercise_id")
        private val EXERCISE_CARD_ID = stringPreferencesKey("exercise_card_id")

        private val IS_LIKE_KEY = booleanPreferencesKey("is_like")
        private val IS_SCRAP_KEY = booleanPreferencesKey("is_scrap")
    }

    private val dataStore: DataStore<Preferences> = context.datastore

    fun saveToken(token: String) = runBlocking {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    fun saveYoutubeToken(token: String) = runBlocking {
        dataStore.edit { prefs ->
            prefs[YOUTUBE_TOKEN_KEY] = token
        }
    }

    fun saveExerciseId(eid : String) = runBlocking{
        dataStore.edit { prefs ->
            prefs[EXERCISE_ID] =  eid
        }
    }

    suspend fun saveExerciseCardId(id: String) {
        dataStore.edit { prefs ->
            prefs[EXERCISE_CARD_ID] = id
        }
        Log.d("SaveExerciseCardId", "Saved ID: $id")
    }

    fun getExerciseCardId(): Flow<String?> = dataStore.data
        .map { prefs ->
            prefs[EXERCISE_CARD_ID].also {
                Log.d("GetExerciseCardId", "Retrieved ID: $it")
            }
        }

    suspend fun deleteExerciseCardId() {
        dataStore.edit { prefs ->
            prefs.remove(EXERCISE_CARD_ID)
        }
    }


    fun getExerciseId() : Flow<String?>{
        return dataStore.data.map { prefs ->
            prefs[EXERCISE_ID]
        }
    }

    fun getYoutubeToken() : Flow<String?>{
        return dataStore.data.map { prefs ->
            prefs[YOUTUBE_TOKEN_KEY]
        }
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    // 좋아요 / 스크랩
    fun saveIsLike(isLike: Boolean) = runBlocking {
        dataStore.edit { prefs ->
            prefs[IS_LIKE_KEY] = isLike
        }
    }

    fun getIsLike(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[IS_LIKE_KEY] ?: false
        }
    }

    fun saveIsScrap(isScrap: Boolean) = runBlocking {
        dataStore.edit { prefs ->
            prefs[IS_SCRAP_KEY] = isScrap
        }
    }

    fun getIsScrap(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[IS_SCRAP_KEY] ?: false
        }
    }
}
