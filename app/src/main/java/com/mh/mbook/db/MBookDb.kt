package com.mh.mbook.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mh.mbook.vo.User

//@Database(
//    entities = [
////        User::class
//    ],
//    version = 3,
//    exportSchema = false
//)
abstract class MBookDb : RoomDatabase() {
//    abstract fun userDao(): UserDao
//    abstract fun repoDao(): RepoDao
}
