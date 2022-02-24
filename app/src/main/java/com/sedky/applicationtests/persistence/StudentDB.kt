package com.sedky.applicationtests.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sedky.applicationtests.data.model.SleepNight
import com.sedky.applicationtests.data.model.Students

@Database(entities = [Students::class, SleepNight::class], version = 2, exportSchema = false)
abstract class StudentDB : RoomDatabase() {

    abstract val studentDatabaseDao: StudentDao
    abstract val sleepDataDao: SleepDataDao

//    companion object {
//        @Volatile
//        private var INSTANCE: StudentDB? = null
//
//        fun getInstance(context: Context): StudentDB {
//
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        StudentDB::class.java,
//                        "students_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }

    companion object{

        var INSTANCE : StudentDB? = null
        fun getInstance(context: Context) : StudentDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDB::class.java,
                    "students_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}