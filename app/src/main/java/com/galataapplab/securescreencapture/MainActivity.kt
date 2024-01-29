package com.galataapplab.securescreencapture

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.galataapplab.securescreencapture.ui.theme.SecureScreenCaptureTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            SecureScreenCaptureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = "A", modifier = modifier
    ) {
        composable(route = "A") {
            UnprotectedScreen(navigateToProtectedScreen = { navController.navigate("B") })
        }
        composable(route = "B") {
            ProtectedScreen()
        }
    }
}


@Composable
fun ProtectedScreen(modifier: Modifier = Modifier) {

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

    Text(text = "Screen B")
}

@Composable
fun UnprotectedScreen(modifier: Modifier = Modifier, navigateToProtectedScreen: () -> Unit) {

    ComposableLifeCycle { source, event ->

    }

    Text(text = "Screen A", modifier = modifier.clickable {
        navigateToProtectedScreen()
    })
}
