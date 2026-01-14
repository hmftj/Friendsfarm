package com.example.friendsfarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
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
    // Transition to main screen after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B5E20)), // Dark Green Background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "FriendsFarm PVT LTD",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = Color.White)
        }
        
        // Footer Credits
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Made by HMFTJ", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Text(text = "Powered by TARA", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun FriendsFarmApp() {
    val baseUrl = "https://hmftj.com/ff/"
    var currentUrl by remember { mutableStateOf(baseUrl) }
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FriendsFarm Livestocks") },
                backgroundColor = Color(0xFF2E7D32),
                contentColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = Color(0xFF2E7D32)) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, "Livestock") },
                    label = { Text("Livestock") },
                    selected = currentUrl == baseUrl,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.6f),
                    onClick = { if (currentUrl != baseUrl) currentUrl = baseUrl }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Phone, "Contact") },
                    label = { Text("Contact") },
                    selected = currentUrl.contains("contact"),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.6f),
                    onClick = { currentUrl = "${baseUrl}contact.php" }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LivestockWebView(
                url = currentUrl,
                onLoadingStateChange = { isLoading = it }
            )
            
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Yellow
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LivestockWebView(url: String, onLoadingStateChange: (Boolean) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onLoadingStateChange(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onLoadingStateChange(false)
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        return false // Keep navigation inside the WebView
                    }
                }
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(url)
            }
        },
        update = { webView ->
            if (webView.url != url) {
                webView.loadUrl(url)
            }
        }
    )
}
