package burak.ceylan.weathersapp.di

import android.app.Application
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://dataservice.accuweather.com/")
        .client(okHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(): GithubAPI =
        provideRetrofit().create(GithubAPI::class.java)


}