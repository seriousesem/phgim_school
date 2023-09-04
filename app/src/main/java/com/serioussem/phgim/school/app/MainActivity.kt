package com.serioussem.phgim.school.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.serioussem.phgim.school.presentation.navigation.Navigation
import com.serioussem.phgim.school.presentation.ui.theme.PhgimSchoolTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var adMobInterstitial: AdMobInterstitial

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.startAppWorker(this)
//        adMobInterstitial.loadAd()

        setContent {
            PhgimSchoolTheme(
                darkTheme = false,
                dynamicColor = false,
            ) {
                Navigation {
                    viewModel.showAddMob(this@MainActivity)
                }
            }
        }
    }
}
