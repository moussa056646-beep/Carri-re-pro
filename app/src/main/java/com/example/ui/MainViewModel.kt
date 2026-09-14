package com.example.ui

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.AdMobManager
import com.example.ads.AdMobStats
import com.example.data.CareerCatalog
import com.example.data.local.AppDatabase
import com.example.data.local.entity.AppPhotoEntity
import com.example.data.local.entity.ForumCommentEntity
import com.example.data.local.entity.ForumPostEntity
import com.example.data.local.entity.GamificationProfileEntity
import com.example.data.local.entity.JobApplicationEntity
import com.example.data.local.entity.SavedJobEntity
import com.example.data.local.entity.SavedTipEntity
import com.example.data.model.CvTemplate
import com.example.data.model.InterviewQuestion
import com.example.data.model.JobOffer
import com.example.data.repository.CareerRepository
import com.example.util.AppLanguage
import com.example.util.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppTab(val title: String) {
    DASHBOARD("Tableau de Bord"),
    OFFRES("Offres d'emploi"),
    ENTRETIENS("Entretiens"),
    CV_EXEMPLES("Exemples CV"),
    FORUM("Forum"),
    FAVORIS("Mon Espace"),
    FAQ("FAQ")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = CareerRepository(database.careerDao())
    val adMobManager = AdMobManager(application)

    // Language State (FR / EN)
    private val _appLanguage = MutableStateFlow(AppLanguage.FR)
    val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _appLanguage.value = language
    }

    // Offline Mode Simulation / Status
    private val _isOfflineMode = MutableStateFlow(false)
    val isOfflineMode: StateFlow<Boolean> = _isOfflineMode.asStateFlow()

    fun toggleOfflineMode() {
        _isOfflineMode.value = !_isOfflineMode.value
    }

    // Current Navigation Tab (Default to DASHBOARD)
    private val _currentTab = MutableStateFlow(AppTab.DASHBOARD)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // --- GAMIFICATION PROFILE ---
    val gamificationProfile: StateFlow<GamificationProfileEntity?> = repository.gamificationProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun claimDailyChallenge(activity: Activity?) {
        viewModelScope.launch {
            repository.addXp(50, "DAILY_STREAK")
            adMobManager.showInterstitialIfAllowed(
                activity = activity,
                triggerReason = "Validation du défi du jour (+50 XP)",
                onAdClosed = {}
            )
        }
    }

    // --- FORUM STATE ---
    val forumPosts: StateFlow<List<ForumPostEntity>> = repository.forumPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedForumPost = MutableStateFlow<ForumPostEntity?>(null)
    val selectedForumPost: StateFlow<ForumPostEntity?> = _selectedForumPost.asStateFlow()

    private val _postComments = MutableStateFlow<List<ForumCommentEntity>>(emptyList())
    val postComments: StateFlow<List<ForumCommentEntity>> = _postComments.asStateFlow()

    // --- PHOTOS & DOCUMENTS STATE ---
    val allPhotos: StateFlow<List<AppPhotoEntity>> = repository.allPhotos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.initializeForumIfEmpty()
            repository.initializePhotosIfEmpty()
        }
    }

    fun addPhotoUrl(title: String, category: String, url: String, description: String = "") {
        viewModelScope.launch {
            val photo = AppPhotoEntity(
                id = "photo_${UUID.randomUUID()}",
                title = title.ifBlank { "Photo en ligne" },
                category = category,
                imageUrl = url,
                imageBase64 = null,
                sourceType = "HOSTED_URL",
                description = description
            )
            repository.addPhoto(photo)
        }
    }

    fun addPhotoDatabase(title: String, category: String, base64: String, description: String = "") {
        viewModelScope.launch {
            val photo = AppPhotoEntity(
                id = "photo_${UUID.randomUUID()}",
                title = title.ifBlank { "Document en base" },
                category = category,
                imageUrl = null,
                imageBase64 = base64,
                sourceType = "DATABASE_STORAGE",
                description = description
            )
            repository.addPhoto(photo)
        }
    }

    fun addPresetDatabasePhoto(title: String, category: String, bgColorHex: Int, text1: String, text2: String, description: String) {
        viewModelScope.launch {
            val base64 = repository.generateVectorPngBase64(bgColorHex, text1, text2)
            val photo = AppPhotoEntity(
                id = "photo_${UUID.randomUUID()}",
                title = title,
                category = category,
                imageUrl = null,
                imageBase64 = base64,
                sourceType = "DATABASE_STORAGE",
                description = description
            )
            repository.addPhoto(photo)
        }
    }

    fun deletePhoto(id: String) {
        viewModelScope.launch {
            repository.deletePhoto(id)
        }
    }

    fun setAsAvatar(photo: AppPhotoEntity) {
        viewModelScope.launch {
            repository.updateAvatar(photo.imageUrl, photo.imageBase64)
        }
    }

    fun selectForumPost(post: ForumPostEntity?) {
        _selectedForumPost.value = post
        if (post != null) {
            viewModelScope.launch {
                repository.getCommentsForPost(post.id).collect { comments ->
                    _postComments.value = comments
                }
            }
        } else {
            _postComments.value = emptyList()
        }
    }

    fun createForumPost(
        title: String,
        category: String,
        content: String,
        author: String = "Moi (Candidat)",
        imageUrl: String? = null,
        imageBase64: String? = null
    ) {
        viewModelScope.launch {
            repository.createForumPost(title, category, content, author, imageUrl, imageBase64)
        }
    }

    fun upvoteForumPost(postId: String) {
        viewModelScope.launch {
            repository.upvoteForumPost(postId)
        }
    }

    fun addForumComment(postId: String, content: String, author: String = "Moi (Candidat)") {
        viewModelScope.launch {
            repository.addComment(postId, content, author)
        }
    }

    // Push Notifications
    fun sendTestPushNotification() {
        val lang = _appLanguage.value
        val title = if (lang == AppLanguage.FR) {
            "🚀 Carrière Pro : Nouvelle Offre Recommandée !"
        } else {
            "🚀 Carrière Pro: New Recommended Job Offer!"
        }
        val msg = if (lang == AppLanguage.FR) {
            "TechCorp recherche un Développeur Fullstack (CDI - 55K€). Postulez dès maintenant !"
        } else {
            "TechCorp is looking for a Fullstack Engineer (Permanent - €55k). Apply now!"
        }
        NotificationHelper.sendNotification(getApplication(), title, msg)
    }

    // --- JOBS AGGREGATOR STATE ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSector = MutableStateFlow("Tous les secteurs")
    val selectedSector: StateFlow<String> = _selectedSector.asStateFlow()

    private val _selectedContractType = MutableStateFlow("Tous")
    val selectedContractType: StateFlow<String> = _selectedContractType.asStateFlow()

    private val _onlyRemote = MutableStateFlow(false)
    val onlyRemote: StateFlow<Boolean> = _onlyRemote.asStateFlow()

    private val _selectedJob = MutableStateFlow<JobOffer?>(null)
    val selectedJob: StateFlow<JobOffer?> = _selectedJob.asStateFlow()

    private val _applyingJob = MutableStateFlow<JobOffer?>(null)
    val applyingJob: StateFlow<JobOffer?> = _applyingJob.asStateFlow()

    // Room Saved Jobs
    val savedJobs: StateFlow<List<SavedJobEntity>> = repository.savedJobs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Room Applications
    val applications: StateFlow<List<JobApplicationEntity>> = repository.applications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectSector(sector: String) {
        _selectedSector.value = sector
    }

    fun selectContractType(type: String) {
        _selectedContractType.value = type
    }

    fun toggleOnlyRemote() {
        _onlyRemote.value = !_onlyRemote.value
    }

    fun selectJob(job: JobOffer?) {
        _selectedJob.value = job
    }

    fun startApplying(job: JobOffer?) {
        _applyingJob.value = job
    }

    fun isJobSaved(jobId: String): Boolean {
        return savedJobs.value.any { it.id == jobId }
    }

    fun toggleSaveJob(job: JobOffer) {
        viewModelScope.launch {
            if (isJobSaved(job.id)) {
                repository.removeSavedJob(job.id)
            } else {
                repository.saveJob(job)
            }
        }
    }

    fun submitApplication(job: JobOffer, notes: String, activity: Activity?, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.submitApplication(job, notes)
            _applyingJob.value = null
            // Trigger strategic interstitial after submitting application
            adMobManager.showInterstitialIfAllowed(
                activity = activity,
                triggerReason = "Envoi de votre candidature pour ${job.title}",
                onAdClosed = onComplete
            )
        }
    }

    fun deleteApplication(id: String) {
        viewModelScope.launch {
            repository.deleteApplication(id)
        }
    }

    // --- INTERVIEW PREPARATION STATE ---
    private val _selectedInterviewCategory = MutableStateFlow("Toutes les questions")
    val selectedInterviewCategory: StateFlow<String> = _selectedInterviewCategory.asStateFlow()

    private val _selectedQuestion = MutableStateFlow<InterviewQuestion?>(null)
    val selectedQuestion: StateFlow<InterviewQuestion?> = _selectedQuestion.asStateFlow()

    private val _showStarSimulator = MutableStateFlow(false)
    val showStarSimulator: StateFlow<Boolean> = _showStarSimulator.asStateFlow()

    private val _showFlashcardQuiz = MutableStateFlow(false)
    val showFlashcardQuiz: StateFlow<Boolean> = _showFlashcardQuiz.asStateFlow()

    // Room Saved Tips
    val savedTips: StateFlow<List<SavedTipEntity>> = repository.savedTips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectInterviewCategory(cat: String) {
        _selectedInterviewCategory.value = cat
    }

    fun selectQuestion(question: InterviewQuestion?) {
        _selectedQuestion.value = question
    }

    fun toggleStarSimulator(show: Boolean) {
        _showStarSimulator.value = show
    }

    fun toggleFlashcardQuiz(show: Boolean) {
        _showFlashcardQuiz.value = show
    }

    fun isTipSaved(tipId: String): Boolean {
        return savedTips.value.any { it.id == tipId }
    }

    fun toggleSaveTip(tip: InterviewQuestion) {
        viewModelScope.launch {
            val isSaved = isTipSaved(tip.id)
            repository.toggleTipSaved(tip.id, tip.question, tip.category, isSaved)
        }
    }

    // --- CV EXAMPLES STATE ---
    private val _selectedCvSector = MutableStateFlow("Tous les secteurs")
    val selectedCvSector: StateFlow<String> = _selectedCvSector.asStateFlow()

    private val _selectedCvTemplate = MutableStateFlow<CvTemplate?>(null)
    val selectedCvTemplate: StateFlow<CvTemplate?> = _selectedCvTemplate.asStateFlow()

    fun selectCvSector(sector: String) {
        _selectedCvSector.value = sector
    }

    fun selectCvTemplate(template: CvTemplate?) {
        _selectedCvTemplate.value = template
    }

    fun onCvTemplateExported(activity: Activity?, cvTitle: String, onDone: () -> Unit) {
        // Trigger strategic AdMob interstitial on CV download/export
        adMobManager.showInterstitialIfAllowed(
            activity = activity,
            triggerReason = "Téléchargement du modèle de CV : $cvTitle",
            onAdClosed = onDone
        )
    }

    // AdMob Interstitial State
    val fallbackInterstitialTrigger: StateFlow<String?> = adMobManager.showFallbackInterstitial
    val adMobStats: StateFlow<AdMobStats> = adMobManager.stats

    fun dismissFallbackInterstitial() {
        adMobManager.dismissFallbackInterstitial {
            // Callback when closed
        }
    }

    // Filtered Jobs
    fun getFilteredJobs(): List<JobOffer> {
        val q = _searchQuery.value.trim().lowercase()
        val sector = _selectedSector.value
        val contract = _selectedContractType.value
        val remote = _onlyRemote.value

        return CareerCatalog.jobOffers.filter { job ->
            val matchesQuery = q.isEmpty() ||
                    job.title.lowercase().contains(q) ||
                    job.company.lowercase().contains(q) ||
                    job.location.lowercase().contains(q) ||
                    job.missions.any { it.lowercase().contains(q) } ||
                    job.requirements.any { it.lowercase().contains(q) }

            val matchesSector = sector == "Tous les secteurs" || job.sector == sector
            val matchesContract = contract == "Tous" || job.contractType == contract
            val matchesRemote = !remote || job.remotePolicy.contains("Télétravail", ignoreCase = true)

            matchesQuery && matchesSector && matchesContract && matchesRemote
        }
    }

    // Filtered Questions
    fun getFilteredQuestions(): List<InterviewQuestion> {
        val cat = _selectedInterviewCategory.value
        return if (cat == "Toutes les questions") {
            CareerCatalog.interviewQuestions
        } else {
            CareerCatalog.interviewQuestions.filter { it.category == cat }
        }
    }

    // Filtered CV Templates
    fun getFilteredCvTemplates(): List<CvTemplate> {
        val sec = _selectedCvSector.value
        return if (sec == "Tous les secteurs") {
            CareerCatalog.cvTemplates
        } else {
            CareerCatalog.cvTemplates.filter { it.sector == sec || it.sector == "Tous secteurs" }
        }
    }
}
