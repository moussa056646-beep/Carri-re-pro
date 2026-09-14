package com.example.ui.screens.dashboard

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobManager
import com.example.data.local.entity.AppPhotoEntity
import com.example.data.local.entity.GamificationProfileEntity
import com.example.ui.AppTab
import com.example.ui.components.AdMobBanner
import com.example.ui.components.AdMobNativeAd
import com.example.ui.components.AppAsyncImage
import com.example.ui.components.PhotoGallerySection
import com.example.ui.theme.CarriereAmber
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.CarriereEmerald
import com.example.ui.theme.CarriereNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.SkyBlue100
import com.example.ui.theme.SkyBlue50
import com.example.util.AppLanguage
import com.example.util.Localization

data class CareerBadge(
    val id: String,
    val titleFr: String,
    val titleEn: String,
    val descFr: String,
    val descEn: String,
    val icon: ImageVector,
    val color: Color
)

val allBadges = listOf(
    CareerBadge(
        "FIRST_APP",
        "Premier Pas",
        "First Step",
        "Première candidature soumise",
        "First application submitted",
        Icons.Default.Send,
        CarriereBlue
    ),
    CareerBadge(
        "STAR_MASTER",
        "Maître STAR",
        "STAR Master",
        "Simulation STAR validée",
        "Validated STAR pitch",
        Icons.Default.Psychology,
        CarriereEmerald
    ),
    CareerBadge(
        "ATS_PRO",
        "As du CV ATS",
        "ATS Resume Ace",
        "Canevas de CV professionnel exploré",
        "Professional CV template reviewed",
        Icons.Default.Description,
        CarriereAmber
    ),
    CareerBadge(
        "DAILY_STREAK",
        "Discipline de Fer",
        "Iron Will",
        "Série d'entraînement active",
        "Active daily streak",
        Icons.Default.LocalFireDepartment,
        Color(0xFFE11D48)
    ),
    CareerBadge(
        "COMMUNITY_CONTRIBUTOR",
        "Pilier Communauté",
        "Community Hero",
        "Contribution au forum d'entraide",
        "Post shared on community forum",
        Icons.Default.Forum,
        Color(0xFF7C3AED)
    )
)

@Composable
fun DashboardScreen(
    profile: GamificationProfileEntity?,
    isOffline: Boolean,
    language: AppLanguage,
    adMobManager: AdMobManager,
    photos: List<AppPhotoEntity> = emptyList(),
    onAddPhotoUrl: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onAddPhotoDatabase: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onAddPresetDatabase: (String, String, Int, String, String, String) -> Unit = { _, _, _, _, _, _ -> },
    onDeletePhoto: (String) -> Unit = {},
    onSetAsAvatar: (AppPhotoEntity) -> Unit = {},
    onNavigateTab: (AppTab) -> Unit,
    onClaimChallenge: () -> Unit,
    onToggleOffline: () -> Unit,
    onSendTestPush: () -> Unit
) {
    val xp = profile?.xp ?: 430
    val level = profile?.level ?: 3
    val streak = profile?.streakDays ?: 4
    val unlockedBadgeKeys = (profile?.unlockedBadges ?: "FIRST_APP,STAR_MASTER,ATS_PRO").split(",")

    val levelTitle = when (level) {
        1 -> if (language == AppLanguage.FR) "Candidat Débutant" else "Beginner Candidate"
        2 -> if (language == AppLanguage.FR) "Candidat Motivé" else "Motivated Candidate"
        3 -> if (language == AppLanguage.FR) "Stratège Carrière" else "Career Strategist"
        4 -> if (language == AppLanguage.FR) "Talent Confirmé" else "Proven Talent"
        else -> if (language == AppLanguage.FR) "Top Performer Pro" else "Top Performer Pro"
    }

    val xpForNextLevel = 650
    val progress = (xp % 350).toFloat() / 300f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Header Card with Dynamic Blue Gradient
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                CarriereDeepBlue,
                                ElectricBlue
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            val userAvatarModel: Any? = profile?.avatarBase64 ?: profile?.avatarUrl
                            AppAsyncImage(
                                model = userAvatarModel,
                                contentDescription = "Avatar Alexandre",
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape),
                                shape = CircleShape,
                                fallbackIcon = Icons.Default.Person,
                                enableZoomOnClick = true
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = Localization.t("dash_title", language),
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = if (language == AppLanguage.FR) "Bonjour Alexandre 👋" else "Hello Alexandre 👋",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Streak Indicator Badge
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = CarriereAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$streak ${if (language == AppLanguage.FR) "jours" else "days"}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Readiness Score Card inside Hero
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(CarriereEmerald)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = Localization.t("dash_readiness", language),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = levelTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = CarriereDeepBlue
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = { progress.coerceIn(0.1f, 0.95f) },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = CarriereBlue,
                                    trackColor = SkyBlue100
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$xp / $xpForNextLevel XP",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Circular Score Percentage
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(64.dp)
                            ) {
                                CircularProgressIndicator(
                                    progress = { 0.88f },
                                    modifier = Modifier.fillMaxSize(),
                                    color = CarriereEmerald,
                                    trackColor = Color(0xFFE2E8F0),
                                    strokeWidth = 6.dp
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "88%",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 15.sp,
                                        color = CarriereDeepBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Offline Mode Banner / Toggle
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isOffline) Color(0xFFFEF3C7) else SkyBlue50
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isOffline) CarriereAmber else CarriereBlue.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isOffline) CarriereAmber.copy(alpha = 0.2f) else SkyBlue100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isOffline) Icons.Default.WifiOff else Icons.Default.Wifi,
                                contentDescription = null,
                                tint = if (isOffline) CarriereAmber else CarriereBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isOffline) {
                                    Localization.t("status_offline", language)
                                } else {
                                    Localization.t("status_online", language)
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = Localization.t("dash_offline_desc", language),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Switch(
                        checked = isOffline,
                        onCheckedChange = { onToggleOffline() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CarriereAmber,
                            checkedTrackColor = CarriereAmber.copy(alpha = 0.3f),
                            uncheckedThumbColor = CarriereBlue,
                            uncheckedTrackColor = SkyBlue100
                        )
                    )
                }
            }
        }

        // Daily Challenge Gamification Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = CarriereAmber,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = Localization.t("dash_daily_challenge", language),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CarriereEmerald.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "+50 XP",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = CarriereEmerald
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (language == AppLanguage.FR) {
                            "Entraînez-vous sur la question clé : 'Pourquoi devrions-nous vous choisir vous plutôt qu'un autre ?' avec la méthode STAR."
                        } else {
                            "Practice the key interview question: 'Why should we hire you over other candidates?' using the STAR framework."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onClaimChallenge,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = CarriereBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.t("dash_start_challenge", language),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Native Ad Placement
        item {
            AdMobNativeAd(adMobManager = adMobManager)
        }

        // Quick Actions Grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = Localization.t("dash_quick_actions", language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Psychology,
                        title = Localization.t("dash_act_star", language),
                        badge = "Coaching",
                        color = CarriereBlue,
                        onClick = { onNavigateTab(AppTab.ENTRETIENS) }
                    )
                    QuickActionTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Work,
                        title = Localization.t("dash_act_jobs", language),
                        badge = "12 offres",
                        color = CarriereEmerald,
                        onClick = { onNavigateTab(AppTab.OFFRES) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Description,
                        title = Localization.t("dash_act_cv", language),
                        badge = "ATS Ready",
                        color = CarriereAmber,
                        onClick = { onNavigateTab(AppTab.CV_EXEMPLES) }
                    )
                    QuickActionTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Forum,
                        title = Localization.t("dash_act_forum", language),
                        badge = "Communauté",
                        color = Color(0xFF7C3AED),
                        onClick = { onNavigateTab(AppTab.FORUM) }
                    )
                }
            }
        }

        // Gamification Badges Carousel
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Localization.t("dash_gamification_title", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${unlockedBadgeKeys.size}/${allBadges.size} ${if (language == AppLanguage.FR) "débloqués" else "unlocked"}",
                        fontSize = 12.sp,
                        color = CarriereBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allBadges) { badge ->
                        val isUnlocked = unlockedBadgeKeys.contains(badge.id)
                        Card(
                            modifier = Modifier.width(150.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                width = 1.dp,
                                color = if (isUnlocked) badge.color.copy(alpha = 0.4f) else Color.Transparent
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(if (isUnlocked) badge.color.copy(alpha = 0.15f) else Color.LightGray.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = badge.icon,
                                        contentDescription = null,
                                        tint = if (isUnlocked) badge.color else Color.Gray,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (language == AppLanguage.FR) badge.titleFr else badge.titleEn,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (language == AppLanguage.FR) badge.descFr else badge.descEn,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Photo & Media Assets Manager (Hosted URL & SQLite database stored)
        item {
            PhotoGallerySection(
                photos = photos,
                onAddPhotoUrl = onAddPhotoUrl,
                onAddPhotoDatabase = onAddPhotoDatabase,
                onAddPresetDatabase = onAddPresetDatabase,
                onDeletePhoto = onDeletePhoto,
                onSetAsAvatar = onSetAsAvatar
            )
        }

        // Push Notification Test Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SkyBlue50),
                border = androidx.compose.foundation.BorderStroke(1.dp, CarriereBlue.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(CarriereBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = CarriereBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Localization.t("dash_push_title", language),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = CarriereNavy
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (language == AppLanguage.FR) {
                                "Recevez quotidiennement un conseil ou une alerte sur mesure."
                            } else {
                                "Receive tailored daily job alerts and interview tips."
                            },
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = onSendTestPush,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = Localization.t("dash_test_push", language),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CarriereBlue
                            )
                        }
                    }
                }
            }
        }

        // Footer Banner
        item {
            AdMobBanner(adMobManager = adMobManager)
        }
    }
}

@Composable
fun QuickActionTile(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    badge: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SkyBlue100)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CarriereDeepBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
