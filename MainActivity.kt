package com.example.friendsfarm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay

// --- AGENTIC BRIDGE ---
class WebAppInterface(private val mContext: Context) {
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(onTimeout = { showSplash = false })
    } else {
        FriendsFarmApp()
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // 3 Seconds Splash
        onTimeout()
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1B5E20)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("FriendsFarm PVT LTD", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(color = Color.White)
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Made by HMFTJ", color = Color.White.copy(0.7f), fontSize = 12.sp)
            Text("Powered by TARA", color = Color.White.copy(0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun FriendsFarmApp() {
    // CHANGE YOUR WEBSITE URLS HERE
    val livestockUrl = "https://hmftj.com/ff/" 
    val contactUrl = "https://hmftj.com/ff/contact.php" 

    var currentUrl by remember { mutableStateOf(livestockUrl) }
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("FriendsFarm Livestocks") }, backgroundColor = Color(0xFF2E7D32), contentColor = Color.White)
        },
        bottomBar = {
            BottomNavigation(backgroundColor = Color(0xFF2E7D32)) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, "Livestock") },
                    label = { Text("Livestock") },
                    selected = currentUrl == livestockUrl,
                    onClick = { currentUrl = livestockUrl }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Phone, "Contact") },
                    label = { Text("Contact") },
                    selected = currentUrl == contactUrl,
                    onClick = { currentUrl = contactUrl }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            HybridWebView(url = currentUrl, onLoading = { isLoading = it })
            if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color.Yellow)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HybridWebView(url: String, onLoading: (Boolean) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                // Registering the "Android" object for JS
                addJavascriptInterface(WebAppInterface(context), "Android")
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(v: WebView?, u: String?, f: android.graphics.Bitmap?) = onLoading(true)
                    override fun onPageFinished(v: WebView?, u: String?) = onLoading(false)
                }
                loadUrl(url)
            }
        },
        update = { webView -> if (webView.url != url) webView.loadUrl(url) }
    )
}
