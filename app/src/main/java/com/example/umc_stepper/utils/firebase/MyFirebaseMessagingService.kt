package com.example.umc_stepper.utils.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("new token", token)
        saveTokenLocally(token)
    }

    override fun onCreate() {
        super.onCreate()
        // 앱이 시작될 때마다 토큰을 다시 요청
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                saveTokenLocally(token)
            } else {
                Log.e("Firebase", "Failed to get token")
            }
        }
        //토픽 구독
        FirebaseMessaging.getInstance().subscribeToTopic("testMessage")
        val sharedPreferences = applicationContext.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val accessToken: String? = sharedPreferences.getString("access_token", "")
        if (accessToken != null) {
            val savedToken = sharedPreferences.getString("firebase_token", "")
            Log.d("SavedFCMToken", savedToken.toString());
            //TokenSender().sendTokenToServer(applicationContext, savedToken, accessToken)
        }
    }

    private fun saveTokenLocally(token: String) {
        val shredPref = applicationContext.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(shredPref.edit()) {
            putString("firebase_token", token)
            apply()
        }
        val sharedPreferences = applicationContext.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("firebase_token", "")
        Log.d("SharedPreferences", "Saved token: $savedToken")
    }

}