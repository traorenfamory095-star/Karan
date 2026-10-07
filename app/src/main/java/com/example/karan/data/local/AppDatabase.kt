package com.example.karan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.karan.data.local.dao.FicheDao
import com.example.karan.data.local.dao.MatiereDao
import com.example.karan.data.local.dao.QuestionDao
import com.example.karan.data.local.dao.SessionQuizDao
import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question
import com.example.karan.models.SessionQuiz
import com.example.karan.models.ErreurQuestion
import com.example.karan.data.local.dao.ErreurQuestionDao


@Database(
    entities = [
        Matiere::class,
        Question::class,
        SessionQuiz::class,
        Fiche::class,
        ErreurQuestion::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun matiereDao(): MatiereDao
    abstract fun questionDao(): QuestionDao
    abstract fun sessionQuizDao(): SessionQuizDao
    abstract fun ficheDao(): FicheDao
    abstract fun erreurQuestionDao(): ErreurQuestionDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "karan_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}