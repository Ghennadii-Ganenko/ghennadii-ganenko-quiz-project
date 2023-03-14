package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.fragments.webview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.R
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.databinding.FragmentWebviewBinding
import kotlin.properties.Delegates


class WebViewFragment : Fragment(R.layout.fragment_webview) {

    private var binding: FragmentWebviewBinding by Delegates.notNull()
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(activity?.window)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    }
                }
            }

        activity?.onBackPressedDispatcher?.addCallback(this, callback);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)

        if(savedInstanceState != null) {
            binding.webView.restoreState(savedInstanceState)
        }

        binding.webView.webViewClient = WebViewClient()

        setWebSettings(binding.webView)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.webView.loadUrl(args.url)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }


    private fun hideSystemUI(window: Window?) =
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

    private fun setWebSettings(webView: WebView) {
        val webSettings = webView.settings
        val cookieManager: CookieManager = CookieManager.getInstance()

        cookieManager.setAcceptCookie(true)

        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.allowContentAccess = true
        webSettings.allowFileAccess = true
        webSettings.setSupportZoom(false)
        webSettings.databaseEnabled = true
    }
}