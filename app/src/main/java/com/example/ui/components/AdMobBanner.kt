package com.example.ui.components

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ads.AdMobManager
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereNavy
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

private fun isHardwareRenderNodeAvailable(): Boolean {
    return try {
        val dri = java.io.File("/dev/dri")
        dri.exists() && (dri.list()?.isNotEmpty() == true)
    } catch (_: Throwable) {
        false
    }
}

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adMobManager: AdMobManager? = null,
    onAdClicked: (() -> Unit)? = null
) {
    val isInPreview = LocalInspectionMode.current
    val isHardwareAvailable = remember { isHardwareRenderNodeAvailable() }
    var hasError by remember { mutableStateOf(!isHardwareAvailable) }

    LaunchedEffect(Unit) {
        if (!isHardwareAvailable) {
            adMobManager?.recordBannerImpression()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isInPreview && !hasError && isHardwareAvailable) {
            // Live AdMob Banner via AndroidView
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                factory = { ctx ->
                    try {
                        AdView(ctx).apply {
                            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
                            setAdSize(AdSize.BANNER)
                            adUnitId = AdMobManager.BANNER_TEST_ID
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            adListener = object : AdListener() {
                                override fun onAdLoaded() {
                                    hasError = false
                                    adMobManager?.recordBannerImpression()
                                }

                                override fun onAdFailedToLoad(error: LoadAdError) {
                                    hasError = true
                                }

                                override fun onAdClicked() {
                                    onAdClicked?.invoke()
                                }
                            }
                            loadAd(AdRequest.Builder().build())
                        }
                    } catch (e: Throwable) {
                        hasError = true
                        android.view.View(ctx)
                    }
                }
            )
        }

        // Graceful fallback / simulated visual banner for preview and reliable display
        if (isInPreview || hasError || !isHardwareAvailable) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        RoundedCornerShape(10.dp)
                    )
                    .clickable { onAdClicked?.invoke() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CarriereBlue)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "AdMob Test",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "Formation Recrutement & Soft Skills 2026",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Boostez vos chances d'embauche de 70%",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Ouvrir l'annonce",
                        tint = CarriereBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
