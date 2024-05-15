package com.example.hikingtrails

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Trail::class), version = 1, exportSchema = false)
public abstract class TrailRoomDatabase : RoomDatabase(){
    abstract fun trailDao(): TrailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TrailRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TrailRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrailRoomDatabase::class.java,
                    "HikingTrails.db"
                ).createFromAsset("databases/HikingTrailsDatabase.db").build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}