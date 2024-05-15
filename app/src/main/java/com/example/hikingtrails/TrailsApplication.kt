package com.example.hikingtrails

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TrailsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TrailRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TrailRepository(database.trailDao()) }
}