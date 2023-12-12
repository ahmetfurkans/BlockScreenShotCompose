package com.galataapplab.securescreencapture

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.galataapplab.securescreencapture.ui.theme.SecureScreenCaptureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureScreenCaptureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ProtectedScreen("Android")
                }
            }
        }
    }
}

@Composable
fun ProtectedScreen(name: String, modifier: Modifier = Modifier) {

    val activity = LocalContext.current.findActivity()

    ComposableLifeCycle { source, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            Lifecycle.Event.ON_STOP -> {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }

            else -> {}
        }
    }

    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ProtectedScreenPreview() {
    SecureScreenCaptureTheme {
        ProtectedScreen("Android")
    }
}