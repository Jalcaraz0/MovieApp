package com.example.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.MovieEntity

@Database(entities=[MovieEntity::class],version=1)
abstract class AppDataBase:RoomDatabase() {

    abstract fun movieDaeo():MovieDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase {
             return INSTANCE ?: synchronized(this){
                 val instance=Room.databaseBuilder(context.applicationContext,
                 AppDataBase::class.java,"movie_table").build()
                 INSTANCE=instance
                 instance
             }


           /* INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "movie_table"
            ).build()
            return INSTANCE!!*/
        }
    }
}