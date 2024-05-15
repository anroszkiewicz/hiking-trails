package com.example.hikingtrails

import kotlinx.coroutines.flow.Flow

class TrailRepository(private val trailDao : TrailDao) {
    val allTrails: Flow<List<Trail>> = trailDao.getList()
    //val trail: Flow<Trail> = trailDao.getDetails()
}