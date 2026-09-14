package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marketing_lessons")
data class MarketingLessonEntity(
    @PrimaryKey
    val id: String,
    val moduleCategory: String, // SEO, SEA_ADS, SOCIAL_MEDIA, EMAILING, ANALYTICS, COPYWRITING
    val titleFr: String,
    val titleEn: String,
    val subtitleFr: String,
    val subtitleEn: String,
    val durationMinutes: Int,
    val level: String, // Débutant, Intermédiaire, Expert
    val isCompleted: Boolean = false,
    val isFavorite: Boolean = false,
    val videoTitle: String,
    val videoDuration: String,
    val videoThumbnailUrl: String,
    val videoThumbnailBase64: String? = null,
    val videoUrl: String,
    val videoChapters: String, // JSON or "00:00|Intro;;03:15|Stratégie;;..."
    val contentMarkdownFr: String,
    val contentMarkdownEn: String,
    val keyTakeawaysFr: String,
    val keyTakeawaysEn: String,
    val completedAt: Long? = null,
    val orderIndex: Int = 0
)

@Entity(tableName = "marketing_exercises")
data class MarketingExerciseEntity(
    @PrimaryKey
    val id: String,
    val lessonId: String,
    val moduleCategory: String,
    val titleFr: String,
    val titleEn: String,
    val descriptionFr: String,
    val descriptionEn: String,
    val exerciseType: String, // AUDIT_SEO, ADS_COPYWRITER, ROAS_CALCULATOR, CERTIFICATION_QUIZ
    val instructionsFr: String,
    val instructionsEn: String,
    val solutionExplanationFr: String,
    val solutionExplanationEn: String,
    val isCompleted: Boolean = false,
    val bestScore: Int = 0, // 0 to 100
    val xpReward: Int = 75
)

@Entity(tableName = "marketing_resources")
data class MarketingResourceEntity(
    @PrimaryKey
    val id: String,
    val titleFr: String,
    val titleEn: String,
    val category: String, // GUIDE_PDF, TEMPLATE, CHECKLIST, CHEAT_SHEET
    val descriptionFr: String,
    val descriptionEn: String,
    val badgeLabel: String,
    val fileSize: String,
    val downloadUrl: String,
    val contentBodyFr: String,
    val contentBodyEn: String,
    val isSaved: Boolean = false
)
