package com.gojigo.githubser.di

import android.content.Context
import androidx.room.Room
import com.gojigo.githubser.data.local.room.SavedUserDao
import com.gojigo.githubser.data.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

object DatabaseModule {

    @Provides
    fun provideSavedUserDao(
        database: UserDatabase
    ) : SavedUserDao {
        return database.savedUserDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) : UserDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            UserDatabase::class.java,
            "UserSaved.db"
        ).build()
    }

}