package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AdMobStats(
    val bannerImpressions: Int = 0,
    val interstitialImpressions: Int = 0,
    val estimatedEarningsEur: Double = 0.0,
    val lastAdShownTime: Long = 0L
)

class AdMobManager(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null
    private var isLoadingInterstitial = false

    private val _stats = MutableStateFlow(AdMobStats())
    val stats: StateFlow<AdMobStats> = _stats.asStateFlow()

    // State for Compose fallback interstitial dialog if live ad not ready
    private val _showFallbackInterstitial = MutableStateFlow<String?>(null)
    val showFallbackInterstitial: StateFlow<String?> = _showFallbackInterstitial.asStateFlow()

    // Minimum cooldown between interstitials in ms (40 seconds for optimal user experience)
    private val cooldownMs = 40_000L
    private var lastInterstitialTimestamp = 0L

    private fun isHardwareRenderNodeAvailable(): Boolean {
        return try {
            val dri = java.io.File("/dev/dri")
            dri.exists() && (dri.list()?.isNotEmpty() == true)
        } catch (_: Throwable) {
            false
        }
    }

    init {
        try {
            val testConfig = RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR))
                .build()
            MobileAds.setRequestConfiguration(testConfig)
            if (isHardwareRenderNodeAvailable()) {
                MobileAds.initialize(context) { status ->
                    Log.d("AdMobManager", "MobileAds initialized: $status")
                    loadInterstitialAd()
                }
            } else {
                Log.d("AdMobManager", "Using in-app visual ads mode for emulator environment")
            }
        } catch (e: Exception) {
            Log.e("AdMobManager", "Error initializing MobileAds", e)
        }
    }

    fun loadInterstitialAd() {
        if (!isHardwareRenderNodeAvailable() || interstitialAd != null || isLoadingInterstitial) return

        isLoadingInterstitial = true
        val adRequest = AdRequest.Builder().build()
        // Standard Google AdMob test interstitial unit ID
        val testInterstitialUnitId = "ca-app-pub-3940256099942544/1033173712"

        InterstitialAd.load(
            context,
            testInterstitialUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoadingInterstitial = false
                    Log.d("AdMobManager", "Interstitial Ad loaded successfully")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoadingInterstitial = false
                    Log.w("AdMobManager", "Failed to load interstitial: ${error.message}")
                }
            }
        )
    }

    /**
     * Strategic interstitial trigger at milestone actions:
     * - Returns true if ad is shown (live or fallback), false if in cooldown.
     */
    fun showInterstitialIfAllowed(
        activity: Activity?,
        triggerReason: String,
        onAdClosed: () -> Unit
    ): Boolean {
        val currentTime = System.currentTimeMillis()
        val elapsed = currentTime - lastInterstitialTimestamp

        // Check if cooldown has elapsed
        if (elapsed < cooldownMs) {
            Log.d("AdMobManager", "Interstitial on cooldown (${(cooldownMs - elapsed) / 1000}s remaining). Skipping to preserve UX.")
            onAdClosed()
            return false
        }

        lastInterstitialTimestamp = currentTime
        recordImpression(isInterstitial = true)

        if (interstitialAd != null && activity != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitialAd()
                    onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    loadInterstitialAd()
                    onAdClosed()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("AdMobManager", "Live interstitial shown for: $triggerReason")
                }
            }
            interstitialAd?.show(activity)
            return true
        } else {
            // Live Ad not ready or in preview emulator environment:
            // Display polished, compliant in-app fallback interstitial modal
            _showFallbackInterstitial.value = triggerReason
            // Also preload next ad
            loadInterstitialAd()
            return true
        }
    }

    fun dismissFallbackInterstitial(onAdClosed: () -> Unit) {
        _showFallbackInterstitial.value = null
        onAdClosed()
    }

    fun recordBannerImpression() {
        recordImpression(isInterstitial = false)
    }

    private fun recordImpression(isInterstitial: Boolean) {
        val current = _stats.value
        val bannerInc = if (!isInterstitial) 1 else 0
        val interstitialInc = if (isInterstitial) 1 else 0
        // Simulated eCPM: banner ~ 0.003€ per impression, interstitial ~ 0.035€ per impression
        val addedRevenue = if (isInterstitial) 0.035 else 0.003

        _stats.value = current.copy(
            bannerImpressions = current.bannerImpressions + bannerInc,
            interstitialImpressions = current.interstitialImpressions + interstitialInc,
            estimatedEarningsEur = current.estimatedEarningsEur + addedRevenue,
            lastAdShownTime = System.currentTimeMillis()
        )
    }

    companion object {
        const val BANNER_TEST_ID = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL_TEST_ID = "ca-app-pub-3940256099942544/1033173712"
    }
}
