package com.example.words

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WordMatchGame()
                }
            }
        }
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun exitApp() {
            finishAffinity()
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }

        @JavascriptInterface
        fun openWebsite() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://meetqonline.com"))
            startActivity(intent)
        }
    }
}

@Composable
fun WordMatchGame() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    addJavascriptInterface((context as MainActivity).WebAppInterface(), "Android")
                    settings.allowFileAccess = false
                    settings.allowContentAccess = false
                    settings.setSupportZoom(true)
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true

                    val assetLoader = WebViewAssetLoader.Builder()
                        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
                        .build()

                    webViewClient = object : WebViewClient() {
                        override fun shouldInterceptRequest(
                            view: WebView,
                            request: WebResourceRequest
                        ): WebResourceResponse? {
                            return assetLoader.shouldInterceptRequest(request.url)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.evaluateJavascript("""
                                (function() {
                                    document.body.style.margin = "0";
                                    document.body.style.padding = "16px";
                                    document.body.style.width = "100%";
                                    document.body.style.minHeight = "100%";
                                    document.body.style.overflowY = "auto";
                                    
                                    var stickySection = document.querySelector('.game-intro-sticky');
                                    if (stickySection) {
                                        stickySection.style.position = 'fixed';
                                        stickySection.style.top = '0';
                                        stickySection.style.left = '0';
                                        stickySection.style.right = '0';
                                        stickySection.style.zIndex = '1000';
                                        stickySection.style.backgroundColor = 'white';
                                        stickySection.style.padding = '16px';
                                        stickySection.style.boxShadow = '0 2px 4px rgba(0,0,0,0.1)';
                                        
                                        var contentBelow = stickySection.nextElementSibling;
                                        if (contentBelow) {
                                            contentBelow.style.marginTop = (stickySection.offsetHeight + 32) + 'px';
                                        }
                                    }
                                })();
                            """.trimIndent(), null)
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                            android.util.Log.d(
                                "JS_CONSOLE",
                                "${consoleMessage?.message()} -- From line ${consoleMessage?.lineNumber()} of ${consoleMessage?.sourceId()}"
                            )
                            return true
                        }
                    }

                    loadUrl("https://appassets.androidplatform.net/assets/word_match_game.html")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
