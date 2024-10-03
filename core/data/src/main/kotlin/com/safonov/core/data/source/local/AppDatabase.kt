package com.safonov.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safonov.core.data.source.local.dao.UserDao
import com.safonov.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}