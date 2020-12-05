package bado.ignacio.events.di

import bado.ignacio.events.BuildConfig
import bado.ignacio.events.data.MyEventsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
            val url = request.url()
                .newBuilder()
                .addQueryParameter("token", BuildConfig.API_TOKEN)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideMyEventService(retrofit: Retrofit): MyEventsService {
        return retrofit.create(MyEventsService::class.java)
    }
}