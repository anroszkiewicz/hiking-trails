package com.example.hikingtrails

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/*class Trail {
    var id: Int = 0
    var name: String? = null
    var type: String? = null
    var stages: String? = null
    var description: String? = null
    var time: Int = 0
    var difficulty: Int = 0

    constructor(id: Int, name: String, type: String, stages: String, description: String, time: Int, difficulty: Int) {
        this.id = id
        this.name = name
        this.type = type
        this.stages = stages
        this.description = description
        this.time = time
        this.difficulty = difficulty
    }

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }
}*/

@Entity(tableName = "Trails")
data class Trail(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "stages") val stages: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "time") val time: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Int
)

@Dao
interface TrailDao {
    @Query("SELECT * FROM Trails")
    fun getList(): Flow<List<Trail>>

    @Query("SELECT * FROM Trails WHERE type = :category")
    fun getListByType(category: String): Flow<List<Trail>>

    @Query("SELECT * FROM Trails WHERE id = :index")
    fun getDetails(index: Int): Flow<Trail>
}

@Database(entities = [Trail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trailDao(): TrailDao
}