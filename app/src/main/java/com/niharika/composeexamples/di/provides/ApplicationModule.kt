package com.niharika.composeexamples.di.provides

import android.app.Application
import com.niharika.composeexamples.data.remote.api.FileApi
import com.niharika.composeexamples.data.remote.constant.FileApiConst
import com.niharika.composeexamples.data.repository.FileRepository
import com.niharika.composeexamples.util.RonaldoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideFileApi(): FileApi = Retrofit.Builder()
        .baseUrl(FileApiConst.BASE_URL)
        .build()
        .create(FileApi::class.java)

    @Provides
    @Singleton
    fun provideFileRepository(fileApi: FileApi): FileRepository = FileRepository(fileApi)

    @Provides
    @Singleton
    fun provideRonaldoProvider(application: Application): RonaldoProvider = RonaldoProvider(application)
}