package com.example.echat.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.echat.MainViewModel
import com.example.echat.server.auth.api.AuthApi
import com.example.echat.server.auth.repository.AuthRepository
import com.example.echat.server.auth.repository.AuthRepositoryImpl
import com.example.echat.server.auth.AuthViewModel
import com.example.echat.server.chat.ChatViewModel
import com.example.echat.server.chat.chatApi.ChatApi
import com.example.echat.server.chat.repository.ChatRepository
import com.example.echat.server.chat.repository.ChatRepositoryImpl
import com.example.echat.server.main.api.MainApi
import com.example.echat.server.main.repository.MainRepository
import com.example.echat.server.main.repository.MainRepositoryImpl
import com.example.echat.server.session.repository.SessionRepository
import com.example.echat.server.session.repository.SessionRepositoryImpl
import com.example.echat.server.session.sessionApi.SessionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "http://192.168.1.2:8080/"
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    @Provides
    @Singleton
    fun provideMainApi(): MainApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainApi::class.java)
    }
    @Provides
    @Singleton
    fun provideChatApi(): ChatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSessionApi(): SessionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SessionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMainViewModel(prefs: SharedPreferences): MainViewModel {
        return MainViewModel(prefs)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        prefs: SharedPreferences,
        mainViewModel: MainViewModel,
    ): AuthRepository {
        return AuthRepositoryImpl(api, prefs, mainViewModel)
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        api: MainApi,
    ): MainRepository {
        return MainRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        api: ChatApi,
    ): ChatRepository {
        return ChatRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        api: SessionApi,
    ): SessionRepository {
        return SessionRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthViewModel(authRepository: AuthRepository): AuthViewModel {
        return AuthViewModel(authRepository)
    }

}