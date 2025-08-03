package com.example.macrotracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.macrotracker.model.FoodItem
import com.example.macrotracker.model.FoodLog
import com.example.macrotracker.model.User

@Database(
    entities = [User::class, FoodItem::class, FoodLog::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun foodLogDao(): FoodLogDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "macro_db"
                )
                    .fallbackToDestructiveMigration()  // <- Aquí está el cambio
                    .build().also { instance = it }
            }
    }
}
