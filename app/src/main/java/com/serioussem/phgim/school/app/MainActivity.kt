package com.serioussem.phgim.school.app
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.serioussem.phgim.school.presentation.navigation.Navigation
import com.serioussem.phgim.school.presentation.ui.components.AdMobInterstitial
import com.serioussem.phgim.school.presentation.ui.theme.PhgimSchoolTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var adMobInterstitial: AdMobInterstitial
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        adMobInterstitial.loadAd()
        setContent {
            PhgimSchoolTheme(
                darkTheme = false,
                dynamicColor = false,
            ) {
                val postNotificationPermission=
                    rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                LaunchedEffect(key1 = true ){
                    if(!postNotificationPermission.status.isGranted){
                        postNotificationPermission.launchPermissionRequest()
                    }
                }
                Navigation {
                    adMobInterstitial.showAdd(this)
                }
            }
        }
    }
}
