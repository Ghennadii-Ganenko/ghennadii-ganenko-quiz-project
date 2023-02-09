package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.app

import android.app.Application
import android.content.Intent
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.R
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.utils.InternetConnection
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (InternetConnection.checkConnection(this)) {
            setUpFirebase()
        }
    }

    private fun setUpFirebase() {
        FirebaseApp.initializeApp(this)
        Firebase.remoteConfig.apply {
            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate().addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }

                val intent = Intent()

                intent.action = "com.ghennadiiganenko.android.url"
                intent.putExtra("url", Firebase.remoteConfig.getString("url"))
                this@App.sendBroadcast(intent)
            }
        }
    }
}