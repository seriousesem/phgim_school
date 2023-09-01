package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.serioussem.phgim.school.R

@Composable
fun AdMobBanner() {
    AndroidView(factory = { appContext ->
        AdView(appContext).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = appContext.getString(R.string.ad_mod_banner_id)
            val adRequest = AdRequest.Builder().build()
            loadAd(adRequest)
        }
    },
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)
    )
}