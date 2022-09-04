package com.lbc.data.di

import android.content.Context
import com.lbc.data.remote.AlbumServiceApi
import com.lbc.data.utils.Constants
import com.lbc.data.utils.ReachabilityImpl
import com.lbc.domain.utils.Reachability
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpInterceptorClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesAlbumServiceApi(client: OkHttpClient, moshi: Moshi): AlbumServiceApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AlbumServiceApi::class.java)
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideReachability(@ApplicationContext appContext: Context): Reachability  =  ReachabilityImpl(appContext)
}