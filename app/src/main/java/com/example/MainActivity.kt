package com.example

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppTab
import com.example.ui.MainViewModel
import com.example.ui.components.AdMobInterstitialDialog
import com.example.ui.screens.cv.CvDetailSheet
import com.example.ui.screens.cv.CvExamplesScreen
import com.example.ui.screens.dashboard.DashboardScreen
import com.example.ui.screens.faq.FaqScreen
import com.example.ui.screens.favorites.FavoritesScreen
import com.example.ui.screens.forum.ForumScreen
import com.example.ui.screens.interview.FlashcardQuizDialog
import com.example.ui.screens.interview.InterviewQuestionDetailSheet
import com.example.ui.screens.interview.InterviewScreen
import com.example.ui.screens.interview.StarMethodDialog
import com.example.ui.screens.jobs.ApplyJobDialog
import com.example.ui.screens.jobs.JobDetailSheet
import com.example.ui.screens.jobs.JobsScreen
import com.example.ui.theme.CarriereAmber
import com.example.ui.theme.CarriereBlue
import com.example.ui.theme.CarriereDeepBlue
import com.example.ui.theme.CarriereEmerald
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SkyBlue100
import com.example.ui.theme.SkyBlue50
import com.example.util.AppLanguage
import com.example.util.Localization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val savedJobs by viewModel.savedJobs.collectAsStateWithLifecycle()
    val applications by viewModel.applications.collectAsStateWithLifecycle()
    val language by viewModel.appLanguage.collectAsStateWithLifecycle()
    val isOffline by viewModel.isOfflineMode.collectAsStateWithLifecycle()
    val profile by viewModel.gamificationProfile.collectAsStateWithLifecycle()
    val forumPosts by viewModel.forumPosts.collectAsStateWithLifecycle()
    val selectedForumPost by viewModel.selectedForumPost.collectAsStateWithLifecycle()
    val postComments by viewModel.postComments.collectAsStateWithLifecycle()

    val selectedJob by viewModel.selectedJob.collectAsStateWithLifecycle()
    val applyingJob by viewModel.applyingJob.collectAsStateWithLifecycle()
    val selectedQuestion by viewModel.selectedQuestion.collectAsStateWithLifecycle()
    val showStarSimulator by viewModel.showStarSimulator.collectAsStateWithLifecycle()
    val showFlashcardQuiz by viewModel.showFlashcardQuiz.collectAsStateWithLifecycle()
    val selectedCvTemplate by viewModel.selectedCvTemplate.collectAsStateWithLifecycle()
    val fallbackInterstitialTrigger by viewModel.fallbackInterstitialTrigger.collectAsStateWithLifecycle()
    val photos by viewModel.allPhotos.collectAsStateWithLifecycle()

    // Notification permission launcher for Android 13+
    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.sendTestPushNotification()
        }
    }

    val requestNotificationAndSend: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                notificationLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.sendTestPushNotification()
            }
        } else {
            viewModel.sendTestPushNotification()
        }
    }

    val totalSaved = savedJobs.size + applications.size

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(CarriereBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("CP", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Carrière Pro",
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.titleMedium,
                                color = CarriereDeepBlue
                            )
                            Text(
                                text = if (language == AppLanguage.FR) "Guide & Opportunités" else "Guide & Opportunities",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                actions = {
                    // Offline badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isOffline) Color(0xFFFEF3C7) else Color(0xFFD1FAE5))
                            .clickable { viewModel.toggleOfflineMode() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(if (isOffline) CarriereAmber else CarriereEmerald)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isOffline) "Hors ligne" else "En ligne",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isOffline) CarriereAmber else CarriereEmerald
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Language Selector (FR / EN)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SkyBlue100)
                            .clickable {
                                val nextLang = if (language == AppLanguage.FR) AppLanguage.EN else AppLanguage.FR
                                viewModel.setLanguage(nextLang)
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${language.flag} ${language.name}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CarriereBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            ScrollableTabRow(
                selectedTabIndex = currentTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = CarriereBlue,
                edgePadding = 8.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[currentTab.ordinal]),
                        color = CarriereBlue,
                        height = 3.dp
                    )
                }
            ) {
                AppTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    val tabTitle = when (tab) {
                        AppTab.DASHBOARD -> Localization.t("tab_dashboard", language)
                        AppTab.OFFRES -> Localization.t("tab_jobs", language)
                        AppTab.ENTRETIENS -> Localization.t("tab_interviews", language)
                        AppTab.CV_EXEMPLES -> Localization.t("tab_cv", language)
                        AppTab.FORUM -> Localization.t("tab_forum", language)
                        AppTab.FAQ -> Localization.t("tab_faq", language)
                        AppTab.FAVORIS -> Localization.t("tab_favorites", language)
                    }

                    val icon = when (tab) {
                        AppTab.DASHBOARD -> Icons.Default.Dashboard
                        AppTab.OFFRES -> Icons.Default.Work
                        AppTab.ENTRETIENS -> Icons.Default.Psychology
                        AppTab.CV_EXEMPLES -> Icons.Default.Description
                        AppTab.FORUM -> Icons.Default.Forum
                        AppTab.FAQ -> Icons.Default.HelpOutline
                        AppTab.FAVORIS -> Icons.Default.Bookmark
                    }

                    Tab(
                        selected = isSelected,
                        onClick = { viewModel.setTab(tab) },
                        text = {
                            Text(
                                text = tabTitle,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) CarriereBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        icon = {
                            if (tab == AppTab.FAVORIS && totalSaved > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(containerColor = CarriereAmber, contentColor = Color.Black) {
                                            Text("$totalSaved")
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = tabTitle,
                                        tint = if (isSelected) CarriereBlue else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = tabTitle,
                                    tint = if (isSelected) CarriereBlue else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.DASHBOARD -> DashboardScreen(
                    profile = profile,
                    isOffline = isOffline,
                    language = language,
                    adMobManager = viewModel.adMobManager,
                    photos = photos,
                    onAddPhotoUrl = { title, category, url, desc ->
                        viewModel.addPhotoUrl(title, category, url, desc)
                    },
                    onAddPhotoDatabase = { title, category, b64, desc ->
                        viewModel.addPhotoDatabase(title, category, b64, desc)
                    },
                    onAddPresetDatabase = { title, category, colorHex, line1, line2, desc ->
                        viewModel.addPresetDatabasePhoto(title, category, colorHex, line1, line2, desc)
                    },
                    onDeletePhoto = { photoId -> viewModel.deletePhoto(photoId) },
                    onSetAsAvatar = { photo -> viewModel.setAsAvatar(photo) },
                    onNavigateTab = { tab -> viewModel.setTab(tab) },
                    onClaimChallenge = { viewModel.claimDailyChallenge(activity) },
                    onToggleOffline = { viewModel.toggleOfflineMode() },
                    onSendTestPush = requestNotificationAndSend
                )
                AppTab.OFFRES -> JobsScreen(viewModel = viewModel)
                AppTab.ENTRETIENS -> InterviewScreen(viewModel = viewModel)
                AppTab.CV_EXEMPLES -> CvExamplesScreen(viewModel = viewModel)
                AppTab.FORUM -> ForumScreen(
                    posts = forumPosts,
                    selectedPost = selectedForumPost,
                    comments = postComments,
                    language = language,
                    adMobManager = viewModel.adMobManager,
                    onSelectPost = { viewModel.selectForumPost(it) },
                    onUpvotePost = { viewModel.upvoteForumPost(it) },
                    onCreatePost = { title, cat, content, imgUrl, imgB64 ->
                        viewModel.createForumPost(
                            title = title,
                            category = cat,
                            content = content,
                            imageUrl = imgUrl,
                            imageBase64 = imgB64
                        )
                    },
                    onAddComment = { postId, text -> viewModel.addForumComment(postId, text) }
                )
                AppTab.FAQ -> FaqScreen(
                    language = language,
                    adMobManager = viewModel.adMobManager
                )
                AppTab.FAVORIS -> FavoritesScreen(viewModel = viewModel)
            }
        }
    }

    // --- OVERLAY SHEETS & DIALOGS ---

    // Job Details Bottom Sheet
    selectedJob?.let { job ->
        val isSaved = viewModel.isJobSaved(job.id)
        JobDetailSheet(
            job = job,
            isSaved = isSaved,
            onDismiss = { viewModel.selectJob(null) },
            onToggleSave = { viewModel.toggleSaveJob(job) },
            onApply = {
                viewModel.selectJob(null)
                viewModel.startApplying(job)
            }
        )
    }

    // Quick Apply Dialog
    applyingJob?.let { job ->
        ApplyJobDialog(
            job = job,
            onDismiss = { viewModel.startApplying(null) },
            onSubmit = { notes ->
                viewModel.submitApplication(job, notes, activity) {
                    // Application submitted callback
                }
            }
        )
    }

    // Interview Question Detail Sheet
    selectedQuestion?.let { question ->
        val isSaved = viewModel.isTipSaved(question.id)
        InterviewQuestionDetailSheet(
            question = question,
            isSaved = isSaved,
            onDismiss = { viewModel.selectQuestion(null) },
            onToggleSave = { viewModel.toggleSaveTip(question) }
        )
    }

    // STAR Method Interactive Simulator
    if (showStarSimulator) {
        StarMethodDialog(
            onDismiss = { viewModel.toggleStarSimulator(false) },
            onComplete = {
                viewModel.toggleStarSimulator(false)
                viewModel.adMobManager.showInterstitialIfAllowed(
                    activity = activity,
                    triggerReason = "Validation de votre pitch méthode STAR",
                    onAdClosed = {}
                )
            }
        )
    }

    // Flashcard Quiz Dialog
    if (showFlashcardQuiz) {
        FlashcardQuizDialog(
            onDismiss = { viewModel.toggleFlashcardQuiz(false) },
            onFinishSession = {
                viewModel.toggleFlashcardQuiz(false)
                viewModel.adMobManager.showInterstitialIfAllowed(
                    activity = activity,
                    triggerReason = "Complétion de la session Flashcards Entretien",
                    onAdClosed = {}
                )
            }
        )
    }

    // CV Template Detail Sheet
    selectedCvTemplate?.let { template ->
        CvDetailSheet(
            template = template,
            onDismiss = { viewModel.selectCvTemplate(null) },
            onExportOrCopy = {
                viewModel.onCvTemplateExported(activity, template.title) {
                    // Closed callback
                }
            }
        )
    }

    // Strategic AdMob Fallback Interstitial Dialog
    fallbackInterstitialTrigger?.let { triggerReason ->
        AdMobInterstitialDialog(
            triggerReason = triggerReason,
            onDismiss = { viewModel.dismissFallbackInterstitial() }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Bienvenue sur Carrière Pro, $name!", modifier = modifier)
}

