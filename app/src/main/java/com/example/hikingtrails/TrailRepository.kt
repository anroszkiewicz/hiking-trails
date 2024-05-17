package com.example.hikingtrails

import kotlinx.coroutines.flow.Flow

class TrailRepository(private val trailDao : TrailDao) {
    val allTrails: Flow<List<Trail>> = trailDao.getList()
    fun getTrail(id: Int) = trailDao.getDetails(id)
    fun getTrailsByType(category: String) = trailDao.getListByType(category)
}