package com.serioussem.phgim.school.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.serioussem.phgim.school.presentation.navigation.Navigation
import com.serioussem.phgim.school.presentation.ui.components.AdMobInterstitial
import com.serioussem.phgim.school.presentation.ui.theme.PhgimSchoolTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var adMobInterstitial: AdMobInterstitial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        adMobInterstitial.loadAd()
        setContent {
            PhgimSchoolTheme(
                darkTheme = false,
                dynamicColor = false,
            ) {
                Navigation{
                    adMobInterstitial.showAdd(this)
                }
            }
        }
    }

}
