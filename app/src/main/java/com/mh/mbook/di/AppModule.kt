package com.mh.mbook.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mh.mbook.api.BookService
import com.mh.mbook.db.MBookDb
import com.mh.mbook.di.viewmodel.ViewModelModule
import com.mh.mbook.util.LiveDataCallAdapterFactory
import com.mh.mbook.util.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(sp: SharedPreferences): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val tokenInterceptor = TokenInterceptor(sp)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): MBookDb {
        return Room.databaseBuilder(
            app, MBookDb::class.java,
            "mbook.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        val name = "SharedPreference:MBook"
        val mode = Context.MODE_PRIVATE
        return app.getSharedPreferences(name, mode)
    }

//    @Singleton
//    @Provides
//    fun provideUserDao(db: MBookDb): UserDao {
//        return db.userDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRepoDao(db: MBookDb): RepoDao {
//        return db.repoDao()
//    }
}
