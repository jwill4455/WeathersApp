package burak.ceylan.weathersapp.di

import android.app.Application
import burak.ceylan.weathersapp.database.CityDatabase
import burak.ceylan.weathersapp.service.WeatherApi
import burak.ceylan.weathersapp.service.WeatherApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun interceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun okHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor())
        .build()

    @Provides
    @Singleton
    fun getGithubDatabase(app:Application): CityDatabase{
        return CityDatabase.getGithubDBInstance(app)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(): WeatherApi =
        provideRetrofit().create(WeatherApi::class.java)

}