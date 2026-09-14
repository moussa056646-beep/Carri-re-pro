package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_jobs")
data class SavedJobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val location: String,
    val sector: String,
    val contractType: String,
    val salary: String,
    val remotePolicy: String,
    val description: String,
    val imageUrl: String? = null,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "job_applications")
data class JobApplicationEntity(
    @PrimaryKey val id: String,
    val jobId: String,
    val jobTitle: String,
    val company: String,
    val appliedDate: Long = System.currentTimeMillis(),
    val status: String, // "Envoyée", "Entretien planifié", "Offre reçue", "En attente"
    val notes: String = ""
)

@Entity(tableName = "saved_tips")
data class SavedTipEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "forum_posts")
data class ForumPostEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val authorRole: String,
    val category: String,
    val content: String,
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val upvotes: Int = 0,
    val commentsCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "forum_comments")
data class ForumCommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val author: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "gamification_profile")
data class GamificationProfileEntity(
    @PrimaryKey val id: String = "user_profile",
    val xp: Int = 380,
    val streakDays: Int = 4,
    val level: Int = 3,
    val unlockedBadges: String = "FIRST_APP,STAR_MASTER,ATS_PRO", // Comma-separated badge keys
    val avatarUrl: String? = null,
    val avatarBase64: String? = null
)

@Entity(tableName = "app_photos")
data class AppPhotoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String, // "Profil & CV", "Entreprise", "Attestation & Diplôme", "Conseils & Schémas"
    val imageUrl: String? = null, // Hosted URL link
    val imageBase64: String? = null, // Stored directly in Room SQLite database
    val sourceType: String = "HOSTED_URL", // "HOSTED_URL" or "DATABASE_STORAGE"
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
