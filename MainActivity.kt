package com.hmftj.friendsfarm

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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

// MainActivity
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

// Main Navigation with SplashScreen
@Composable
fun MainNavigation() {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(onTimeout = { showSplash = false })
    } else {
        FriendsFarmApp()
    }
}

// Splash Screen Composable
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds splash
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .painterResource(id = R.drawable.ffbg),  // Background image
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "FriendsFarm PVT LTD",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(color = Color.White)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Made by HMFTJ", color = Color.White.copy(0.7f), fontSize = 12.sp)
            Text("Powered by TARA", color = Color.White.copy(0.7f), fontSize = 12.sp)
        }
    }
}

// Main Content (WebView App)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsFarmApp() {
    // URLs
    val livestockUrl = "https://hmftj.com/ff/"
    val contactUrl = "https://hmftj.com/ff/contact.php"

    var currentUrl by remember { mutableStateOf(livestockUrl) }
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FriendsFarm Livestocks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF2E7D32),
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = currentUrl == livestockUrl,
                    onClick = { currentUrl = livestockUrl },
                    label = { Text("Livestock") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.plus_foreground),
                            contentDescription = "Livestock"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = Color(0xFF1B5E20)
                    )
                )
                NavigationBarItem(
                    selected = currentUrl == contactUrl,
                    onClick = { currentUrl = contactUrl },
                    label = { Text("Contact") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.contact_foreground),
                            contentDescription = "Contact"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = Color(0xFF1B5E20)
                    )
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

// Check for Internet Connectivity
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

// WebView Composable
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HybridWebView(url: String, onLoading: (Boolean) -> Unit) {
    val context = LocalContext.current
    val isInternetConnected = context.isInternetAvailable()

    if (!isInternetConnected) {
        // Fallback UI when no internet is available
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No Internet Connection", color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(onClick = {
                    // Handle retry or refresh logic here
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Retry",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    } else {
        // WebView if internet is available
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    addJavascriptInterface(WebAppInterface(context), "Android")
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(v: WebView?, u: String?, f: android.graphics.Bitmap?) {
                            onLoading(true)
                        }
                        override fun onPageFinished(v: WebView?, u: String?) {
                            onLoading(false)
                        }
                    }
                    loadUrl(url)
                }
            },
            update = { webView ->
                if (webView.url != url) webView.loadUrl(url)
            }
        )
    }
}
