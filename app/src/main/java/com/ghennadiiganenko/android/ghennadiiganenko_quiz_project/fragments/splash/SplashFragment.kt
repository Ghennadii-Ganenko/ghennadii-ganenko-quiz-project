package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.fragments.splash

import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.R
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.databinding.FragmentSplashBinding
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.utils.InternetConnection
import kotlin.properties.Delegates


const val APP_PREFERENCES = "serversettings"

const val APP_PREFERENCES_URL = "TargetUrl"

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var binding: FragmentSplashBinding by Delegates.notNull()
    private var url: String = ""

    private val brUrl: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1 != null) {
                url = p1.getStringExtra("url").toString()

                val sharedPreferences =
                    requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

                if (sharedPreferences.contains(APP_PREFERENCES_URL)) {
                    url = sharedPreferences.getString(APP_PREFERENCES_URL, "").toString()
                } else if (url != "") {
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(APP_PREFERENCES_URL, url)
                    editor.apply()
                }

                if(url.isEmpty()
                    || Build.MANUFACTURER == "Google"
                    || !isSIMInserted(requireContext())
                    || isEmulator() ) {

                    Handler(Looper.getMainLooper()).postDelayed({
                        val actionToCategoriesFragment =
                            SplashFragmentDirections.actionSplashFragmentToMenuFragment()

                        navigateTo(actionToCategoriesFragment)
                    }, 1000)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val actionToCategoriesFragment =
                            SplashFragmentDirections.actionSplashFragmentToWebViewFragment(url = url)

                        navigateTo(actionToCategoriesFragment)
                    }, 1000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(activity?.window)

        requireContext().registerReceiver(
            brUrl,
            IntentFilter("com.ghennadiiganenko.android.url")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!InternetConnection.checkConnection(requireContext())) {
            Handler(Looper.getMainLooper()).postDelayed({
                val actionToInternetExceptionFragment =
                    SplashFragmentDirections.actionSplashFragmentToInternetExceptionFragment()

                navigateTo(actionToInternetExceptionFragment)
            }, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(brUrl)
    }

    private fun hideSystemUI(window: Window?) =
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

    private fun navigateTo(action: NavDirections) =
        findNavController().navigate(action)

    private fun isSIMInserted(context: Context): Boolean =
        TelephonyManager.SIM_STATE_ABSENT != (context.getSystemService(Context.TELEPHONY_SERVICE)
                as TelephonyManager).simState

    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }
}