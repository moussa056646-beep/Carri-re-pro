package com.example.data.repository

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Base64
import com.example.data.CareerCatalog
import com.example.data.local.dao.CareerDao
import com.example.data.local.entity.AppPhotoEntity
import com.example.data.local.entity.ForumCommentEntity
import com.example.data.local.entity.ForumPostEntity
import com.example.data.local.entity.GamificationProfileEntity
import com.example.data.local.entity.JobApplicationEntity
import com.example.data.local.entity.MarketingExerciseEntity
import com.example.data.local.entity.MarketingLessonEntity
import com.example.data.local.entity.MarketingResourceEntity
import com.example.data.local.entity.SavedJobEntity
import com.example.data.local.entity.SavedTipEntity
import com.example.data.model.CvTemplate
import com.example.data.model.InterviewQuestion
import com.example.data.model.JobOffer
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import java.util.UUID

class CareerRepository(private val careerDao: CareerDao) {

    // Jobs
    fun getAllJobs(): List<JobOffer> = CareerCatalog.jobOffers

    fun getJobById(id: String): JobOffer? = CareerCatalog.jobOffers.find { it.id == id }

    val savedJobs: Flow<List<SavedJobEntity>> = careerDao.getAllSavedJobs()

    fun isJobSaved(jobId: String): Flow<Boolean> = careerDao.isJobSaved(jobId)

    suspend fun saveJob(job: JobOffer) {
        careerDao.insertSavedJob(
            SavedJobEntity(
                id = job.id,
                title = job.title,
                company = job.company,
                location = job.location,
                sector = job.sector,
                contractType = job.contractType,
                salary = job.salary,
                remotePolicy = job.remotePolicy,
                description = job.description,
                imageUrl = job.imageUrl
            )
        )
    }

    suspend fun removeSavedJob(jobId: String) {
        careerDao.deleteSavedJob(jobId)
    }

    // Applications
    val applications: Flow<List<JobApplicationEntity>> = careerDao.getAllApplications()

    suspend fun submitApplication(job: JobOffer, notes: String = "") {
        val app = JobApplicationEntity(
            id = UUID.randomUUID().toString(),
            jobId = job.id,
            jobTitle = job.title,
            company = job.company,
            appliedDate = System.currentTimeMillis(),
            status = "Envoyée",
            notes = notes
        )
        careerDao.insertApplication(app)
        addXp(100, "FIRST_APP")
    }

    suspend fun updateApplicationStatus(appId: String, newStatus: String, notes: String) {
        // can be implemented if needed
    }

    suspend fun deleteApplication(id: String) {
        careerDao.deleteApplication(id)
    }

    // Interview Questions & Tips
    fun getAllInterviewQuestions(): List<InterviewQuestion> = CareerCatalog.interviewQuestions

    fun getQuestionById(id: String): InterviewQuestion? =
        CareerCatalog.interviewQuestions.find { it.id == id }

    val savedTips: Flow<List<SavedTipEntity>> = careerDao.getAllSavedTips()

    fun isTipSaved(tipId: String): Flow<Boolean> = careerDao.isTipSaved(tipId)

    suspend fun toggleTipSaved(tipId: String, title: String, category: String, isCurrentlySaved: Boolean) {
        if (isCurrentlySaved) {
            careerDao.deleteSavedTip(tipId)
        } else {
            careerDao.insertSavedTip(
                SavedTipEntity(
                    id = tipId,
                    title = title,
                    category = category
                )
            )
            addXp(25, null)
        }
    }

    // CV Templates
    fun getCvTemplates(): List<CvTemplate> = CareerCatalog.cvTemplates

    fun getCvTemplateById(id: String): CvTemplate? =
        CareerCatalog.cvTemplates.find { it.id == id }

    // Forum Discussions
    val forumPosts: Flow<List<ForumPostEntity>> = careerDao.getAllForumPosts()

    suspend fun initializeForumIfEmpty() {
        val initialPosts = listOf(
            ForumPostEntity(
                id = "post_1",
                title = "Négociation salariale : comment avez-vous abordé les primes variables ?",
                author = "Sophie B.",
                authorRole = "Product Manager",
                category = "Négociation",
                content = "Bonjour la commu ! J'ai un entretien final demain pour un poste de Senior PM. L'entreprise propose un fixe un peu en dessous de mes attentes mais parle de variable à 15%. Des conseils pour border les KPI dès l'offre écrite ?",
                imageUrl = "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=800&auto=format&fit=crop&q=80",
                upvotes = 24,
                commentsCount = 6,
                timestamp = System.currentTimeMillis() - 3600000 * 5
            ),
            ForumPostEntity(
                id = "post_2",
                title = "Retour d'expérience entretien : le piège de la question sur les défauts",
                author = "Marc V.",
                authorRole = "Développeur Fullstack",
                category = "Retours d'Entretiens",
                content = "J'ai passé 3 tours chez une licorne tech. La recruteuse m'a testé sur 'Citez-moi un échec marquant'. Grâce à la méthode STAR vue sur l'appli, j'ai cadré la situation et montré les correctifs techniques. Résultat : offre reçue hier !",
                imageUrl = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800&auto=format&fit=crop&q=80",
                upvotes = 42,
                commentsCount = 9,
                timestamp = System.currentTimeMillis() - 3600000 * 24
            ),
            ForumPostEntity(
                id = "post_3",
                title = "CV ATS : attention aux icônes de compétences graphiques",
                author = "Amélie D.",
                authorRole = "RH & Talent Acquisition",
                category = "Conseils CV",
                content = "En tant que recruteuse, je vois encore trop de jauges 'Anglais 4/5 étoiles' sur les CV. Les ATS ne savent pas lire cela ! Écrivez clairement 'Anglais : Courant (C1 - Score TOEIC 920)' pour passer sans accroc.",
                imageUrl = "https://images.unsplash.com/photo-1586281380349-632531db7ed4?w=800&auto=format&fit=crop&q=80",
                upvotes = 38,
                commentsCount = 12,
                timestamp = System.currentTimeMillis() - 3600000 * 48
            )
        )
        careerDao.insertForumPosts(initialPosts)
    }

    suspend fun createForumPost(
        title: String,
        category: String,
        content: String,
        author: String = "Moi (Candidat Pro)",
        imageUrl: String? = null,
        imageBase64: String? = null
    ) {
        val post = ForumPostEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            author = author,
            authorRole = "Candidat Actif",
            category = category,
            content = content,
            imageUrl = imageUrl,
            imageBase64 = imageBase64,
            upvotes = 1,
            commentsCount = 0,
            timestamp = System.currentTimeMillis()
        )
        careerDao.insertForumPost(post)
        addXp(40, "COMMUNITY_CONTRIBUTOR")
    }

    suspend fun upvoteForumPost(postId: String) {
        careerDao.upvotePost(postId)
        addXp(10, null)
    }

    fun getCommentsForPost(postId: String): Flow<List<ForumCommentEntity>> =
        careerDao.getCommentsForPost(postId)

    suspend fun addComment(postId: String, content: String, author: String = "Moi (Candidat)") {
        val comment = ForumCommentEntity(
            id = UUID.randomUUID().toString(),
            postId = postId,
            author = author,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        careerDao.insertForumComment(comment)
        careerDao.incrementPostCommentCount(postId)
        addXp(20, null)
    }

    // Photos & Documents Management (Hosted URLs & SQLite Database Storage)
    val allPhotos: Flow<List<AppPhotoEntity>> = careerDao.getAllPhotos()

    fun getPhotosByCategory(category: String): Flow<List<AppPhotoEntity>> =
        careerDao.getPhotosByCategory(category)

    suspend fun addPhoto(photo: AppPhotoEntity) {
        careerDao.insertPhoto(photo)
        addXp(30, "MEDIA_EXPLORER")
    }

    suspend fun deletePhoto(id: String) {
        careerDao.deletePhoto(id)
    }

    suspend fun updateAvatar(url: String?, base64: String?) {
        careerDao.updateAvatar(url, base64)
    }

    suspend fun initializePhotosIfEmpty() {
        if (careerDao.getPhotoCount() > 0) return

        // Génération d'images binaires PNG encodées en Base64 pour le stockage direct en base SQLite
        val badgeBase64 = generateVectorPngBase64(0xFF0D9488.toInt(), "CERTIF", "PRO 2026")
        val avatarBase64 = generateVectorPngBase64(0xFF1E3A8A.toInt(), "AVATAR", "ALEXANDRE")
        val diplomaBase64 = generateVectorPngBase64(0xFFD97706.toInt(), "MASTER", "DIPLÔME")

        val initialPhotos = listOf(
            // 1. Image hébergée en ligne : Espace Tech moderne
            AppPhotoEntity(
                id = "photo-url-1",
                title = "Open-Space Scale-Up NexTech (Paris)",
                category = "Entreprise",
                imageUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800&auto=format&fit=crop&q=80",
                imageBase64 = null,
                sourceType = "HOSTED_URL",
                description = "Locaux modernes avec espaces de travail collaboratifs et équipements ergonomiques."
            ),
            // 2. Image intégrée directement en base SQLite : Avatar Pro
            AppPhotoEntity(
                id = "photo-db-1",
                title = "Photo de Profil Pro (Base64)",
                category = "Profil & CV",
                imageUrl = null,
                imageBase64 = avatarBase64,
                sourceType = "DATABASE_STORAGE",
                description = "Photo de profil Alexandre encodée en Base64 et stockée dans la table SQLite Room."
            ),
            // 3. Image hébergée en ligne : Établissement de Santé
            AppPhotoEntity(
                id = "photo-url-2",
                title = "Complexe Médical Harmonie (Lyon)",
                category = "Entreprise",
                imageUrl = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800&auto=format&fit=crop&q=80",
                imageBase64 = null,
                sourceType = "HOSTED_URL",
                description = "Plateau technique moderne certifié haute qualité de soins."
            ),
            // 4. Image intégrée directement en base SQLite : Badge de Certification
            AppPhotoEntity(
                id = "photo-db-2",
                title = "Certification Kotlin & Android Jetpack",
                category = "Attestation & Diplôme",
                imageUrl = null,
                imageBase64 = badgeBase64,
                sourceType = "DATABASE_STORAGE",
                description = "Badge officiel attestant des compétences avancées en Compose et architecture réactive."
            ),
            // 5. Image hébergée en ligne : Tour Financière
            AppPhotoEntity(
                id = "photo-url-3",
                title = "Siège Axiom Finance (Nantes)",
                category = "Entreprise",
                imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800&auto=format&fit=crop&q=80",
                imageBase64 = null,
                sourceType = "HOSTED_URL",
                description = "Architecture contemporaine au cœur du quartier d'affaires d'Euronantes."
            ),
            // 6. Image intégrée directement en base SQLite : Attestation
            AppPhotoEntity(
                id = "photo-db-3",
                title = "Diplôme Master d'Ingénierie Logicielle",
                category = "Attestation & Diplôme",
                imageUrl = null,
                imageBase64 = diplomaBase64,
                sourceType = "DATABASE_STORAGE",
                description = "Copie numérique du diplôme stockée localement dans la base SQLite."
            ),
            // 7. Image hébergée en ligne : Modèle de CV ATS
            AppPhotoEntity(
                id = "photo-url-4",
                title = "Schéma d'Optimisation CV ATS",
                category = "Conseils & Schémas",
                imageUrl = "https://images.unsplash.com/photo-1586281380349-632531db7ed4?w=800&auto=format&fit=crop&q=80",
                imageBase64 = null,
                sourceType = "HOSTED_URL",
                description = "Guide visuel pour réussir le passage des filtres de recrutement automatisés."
            )
        )

        careerDao.insertPhotos(initialPhotos)
    }

    /**
     * Génère une image PNG valide encodée en Base64 pour tester et démontrer
     * le stockage direct d'images binaires en base de données SQLite.
     */
    fun generateVectorPngBase64(bgColor: Int, line1: String, line2: String): String {
        return try {
            val bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(bgColor)

            val paint = Paint().apply {
                color = Color.WHITE
                textSize = 28f
                isAntiAlias = true
                isFakeBoldText = true
                textAlign = Paint.Align.CENTER
            }
            canvas.drawText(line1, 120f, 110f, paint)

            paint.textSize = 22f
            paint.isFakeBoldText = false
            paint.color = Color.WHITE.and(0xDDFFFFFF.toInt())
            canvas.drawText(line2, 120f, 150f, paint)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val bytes = outputStream.toByteArray()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (_: Throwable) {
            ""
        }
    }

    // Gamification Profile
    val gamificationProfile: Flow<GamificationProfileEntity?> = careerDao.getGamificationProfile()

    suspend fun addXp(points: Int, badgeToUnlock: String?) {
        val current = GamificationProfileEntity(
            id = "user_profile",
            xp = 380,
            streakDays = 4,
            level = 3,
            unlockedBadges = "FIRST_APP,STAR_MASTER,ATS_PRO"
        )
        val newXp = current.xp + points
        val newLevel = when {
            newXp >= 1000 -> 5
            newXp >= 650 -> 4
            newXp >= 350 -> 3
            newXp >= 150 -> 2
            else -> 1
        }
        val currentBadges = current.unlockedBadges.split(",").toMutableSet()
        if (badgeToUnlock != null) {
            currentBadges.add(badgeToUnlock)
        }
        val updated = current.copy(
            xp = newXp,
            level = newLevel,
            unlockedBadges = currentBadges.joinToString(",")
        )
        careerDao.saveGamificationProfile(updated)
    }

    // Digital Marketing Training Modules
    val marketingLessons: Flow<List<MarketingLessonEntity>> = careerDao.getAllMarketingLessons()
    val marketingExercises: Flow<List<MarketingExerciseEntity>> = careerDao.getAllMarketingExercises()
    val marketingResources: Flow<List<MarketingResourceEntity>> = careerDao.getAllMarketingResources()

    suspend fun initializeMarketingDataIfEmpty() {
        if (careerDao.getMarketingLessonsCount() == 0) {
            careerDao.insertMarketingLessons(MarketingDataProvider.getDefaultLessons())
        }
        if (careerDao.getMarketingExercisesCount() == 0) {
            careerDao.insertMarketingExercises(MarketingDataProvider.getDefaultExercises())
        }
        if (careerDao.getMarketingResourcesCount() == 0) {
            careerDao.insertMarketingResources(MarketingDataProvider.getDefaultResources())
        }
    }

    suspend fun completeLesson(lessonId: String) {
        careerDao.updateLessonCompletion(lessonId, true, System.currentTimeMillis())
        addXp(50, "MARKETING_STARTER")
    }

    suspend fun toggleLessonFavorite(lessonId: String) {
        careerDao.toggleLessonFavorite(lessonId)
    }

    suspend fun completeExercise(exerciseId: String, score: Int) {
        careerDao.updateExerciseScore(exerciseId, true, score)
        val badge = if (score >= 80) "MARKETING_EXPERT" else "MARKETING_PRACTITIONER"
        addXp(75, badge)
    }

    suspend fun toggleResourceSaved(resourceId: String) {
        careerDao.toggleResourceSaved(resourceId)
    }
}
