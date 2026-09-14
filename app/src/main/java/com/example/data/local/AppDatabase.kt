package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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

@Database(
    entities = [
        SavedJobEntity::class,
        JobApplicationEntity::class,
        SavedTipEntity::class,
        ForumPostEntity::class,
        ForumCommentEntity::class,
        GamificationProfileEntity::class,
        AppPhotoEntity::class,
        MarketingLessonEntity::class,
        MarketingExerciseEntity::class,
        MarketingResourceEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun careerDao(): CareerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carriere_pro_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
