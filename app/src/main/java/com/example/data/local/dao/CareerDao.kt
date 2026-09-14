package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
import kotlinx.coroutines.flow.Flow

@Dao
interface CareerDao {
    // Saved Jobs
    @Query("SELECT * FROM saved_jobs ORDER BY savedAt DESC")
    fun getAllSavedJobs(): Flow<List<SavedJobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedJob(job: SavedJobEntity)

    @Query("DELETE FROM saved_jobs WHERE id = :jobId")
    suspend fun deleteSavedJob(jobId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_jobs WHERE id = :jobId)")
    fun isJobSaved(jobId: String): Flow<Boolean>

    // Job Applications
    @Query("SELECT * FROM job_applications ORDER BY appliedDate DESC")
    fun getAllApplications(): Flow<List<JobApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(application: JobApplicationEntity)

    @Update
    suspend fun updateApplication(application: JobApplicationEntity)

    @Query("DELETE FROM job_applications WHERE id = :id")
    suspend fun deleteApplication(id: String)

    // Saved Tips / Favorites
    @Query("SELECT * FROM saved_tips ORDER BY savedAt DESC")
    fun getAllSavedTips(): Flow<List<SavedTipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedTip(tip: SavedTipEntity)

    @Query("DELETE FROM saved_tips WHERE id = :tipId")
    suspend fun deleteSavedTip(tipId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_tips WHERE id = :tipId)")
    fun isTipSaved(tipId: String): Flow<Boolean>

    // Forum Posts
    @Query("SELECT * FROM forum_posts ORDER BY timestamp DESC")
    fun getAllForumPosts(): Flow<List<ForumPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForumPost(post: ForumPostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForumPosts(posts: List<ForumPostEntity>)

    @Query("UPDATE forum_posts SET upvotes = upvotes + 1 WHERE id = :postId")
    suspend fun upvotePost(postId: String)

    // Forum Comments
    @Query("SELECT * FROM forum_comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getCommentsForPost(postId: String): Flow<List<ForumCommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForumComment(comment: ForumCommentEntity)

    @Query("UPDATE forum_posts SET commentsCount = commentsCount + 1 WHERE id = :postId")
    suspend fun incrementPostCommentCount(postId: String)

    // Gamification Profile
    @Query("SELECT * FROM gamification_profile WHERE id = 'user_profile'")
    fun getGamificationProfile(): Flow<GamificationProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGamificationProfile(profile: GamificationProfileEntity)

    @Query("UPDATE gamification_profile SET avatarUrl = :url, avatarBase64 = :base64 WHERE id = 'user_profile'")
    suspend fun updateAvatar(url: String?, base64: String?)

    // Photos & Documents (Hosted URL & Database Stored)
    @Query("SELECT * FROM app_photos ORDER BY timestamp DESC")
    fun getAllPhotos(): Flow<List<AppPhotoEntity>>

    @Query("SELECT * FROM app_photos WHERE category = :category ORDER BY timestamp DESC")
    fun getPhotosByCategory(category: String): Flow<List<AppPhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: AppPhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<AppPhotoEntity>)

    @Query("DELETE FROM app_photos WHERE id = :id")
    suspend fun deletePhoto(id: String)

    @Query("SELECT COUNT(*) FROM app_photos")
    suspend fun getPhotoCount(): Int

    // Digital Marketing Training Modules & Lessons
    @Query("SELECT * FROM marketing_lessons ORDER BY orderIndex ASC")
    fun getAllMarketingLessons(): Flow<List<MarketingLessonEntity>>

    @Query("SELECT * FROM marketing_lessons WHERE moduleCategory = :category ORDER BY orderIndex ASC")
    fun getMarketingLessonsByCategory(category: String): Flow<List<MarketingLessonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketingLessons(lessons: List<MarketingLessonEntity>)

    @Query("UPDATE marketing_lessons SET isCompleted = :completed, completedAt = :completedAt WHERE id = :id")
    suspend fun updateLessonCompletion(id: String, completed: Boolean, completedAt: Long?)

    @Query("UPDATE marketing_lessons SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleLessonFavorite(id: String)

    @Query("SELECT COUNT(*) FROM marketing_lessons")
    suspend fun getMarketingLessonsCount(): Int

    // Marketing Exercises
    @Query("SELECT * FROM marketing_exercises ORDER BY id ASC")
    fun getAllMarketingExercises(): Flow<List<MarketingExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketingExercises(exercises: List<MarketingExerciseEntity>)

    @Query("UPDATE marketing_exercises SET isCompleted = :isCompleted, bestScore = :bestScore WHERE id = :id")
    suspend fun updateExerciseScore(id: String, isCompleted: Boolean, bestScore: Int)

    @Query("SELECT COUNT(*) FROM marketing_exercises")
    suspend fun getMarketingExercisesCount(): Int

    // Marketing Resources
    @Query("SELECT * FROM marketing_resources ORDER BY id ASC")
    fun getAllMarketingResources(): Flow<List<MarketingResourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketingResources(resources: List<MarketingResourceEntity>)

    @Query("UPDATE marketing_resources SET isSaved = NOT isSaved WHERE id = :id")
    suspend fun toggleResourceSaved(id: String)

    @Query("SELECT COUNT(*) FROM marketing_resources")
    suspend fun getMarketingResourcesCount(): Int
}
