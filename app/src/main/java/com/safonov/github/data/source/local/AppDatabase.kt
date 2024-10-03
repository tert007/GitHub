package com.safonov.github.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safonov.github.data.source.local.dao.UserDao
import com.safonov.github.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1) // TODO provide database version
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}