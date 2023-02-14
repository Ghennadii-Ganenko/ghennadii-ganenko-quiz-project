package sports.quiz.betcity.hast.app

import android.app.Application
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.onesignal.OneSignal
import sports.quiz.betcity.hast.R
import sports.quiz.betcity.hast.utils.InternetConnection

class App : Application() {

    private val ONESIGNAL_APP_ID = "2cb879c3-bd90-42b0-b1e1-dd07bdcd3d9a"

    override fun onCreate() {
        super.onCreate()

        // Firebase Initialisation
        if (InternetConnection.checkConnection(this)) {
            setUpFirebase()
        }

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

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

                intent.action = "sports.quiz.betcity.hast.url"
                intent.putExtra("url", Firebase.remoteConfig.getString("url"))
                this@App.sendBroadcast(intent)
            }
        }
    }
}