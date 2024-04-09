package com.zebra.runtimepermissionapplication

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.zebra.runtimepermissionapplication.ui.theme.RuntimePermissionApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuntimePermissionApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    // Use DisposableEffect to cleaned up observer properly
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START){
                permissionState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionState.permissions.forEach {
            when(it.permission){
                Manifest.permission.RECORD_AUDIO->{
                    when{
                        it.hasPermission ->{
                            Text(text = "Audio Record Permission accepted")
                        }
                        it.shouldShowRationale ->{
                            // Usually show dialog for permission
                            Text(text = "Audio Record Permission needed to record the audio")
                        }
                        it.isPermanentlyDenied()->{
                            Text(text = "Audio Record Permission was permanently denied. You can enable it from app setting.")
                        }
                    }
                }
                Manifest.permission.CAMERA->{
                    when{
                        it.hasPermission ->{
                            Text(text = "Camera Permission accepted")
                        }
                        it.shouldShowRationale ->{
                            // Usually show dialog for permission
                            Text(text = "Camera Permission needed to taking photo")
                        }
                        it.isPermanentlyDenied()->{
                            Text(text = "Camera Permission was permanently denied. You can enable it from app setting.")

                        }
                    }
                }
                Manifest.permission.ACCESS_COARSE_LOCATION->{
                    when{
                        it.hasPermission ->{
                            Text(text = "Location Permission accepted")
                        }
                        it.shouldShowRationale ->{
                            // Usually show dialog for permission
                            Text(text = "Location Permission needed to current location")
                        }
                        it.isPermanentlyDenied()->{
                            Text(text = "Location Permission was permanently denied. You can enable it from app setting.")
                        }
                    }
                }
                Manifest.permission.ACCESS_FINE_LOCATION->{
                    when{
                        it.hasPermission ->{

                        }
                        it.shouldShowRationale ->{

                        }
                        it.isPermanentlyDenied()->{

                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RuntimePermissionApplicationTheme {
        Greeting("Android")
    }
}