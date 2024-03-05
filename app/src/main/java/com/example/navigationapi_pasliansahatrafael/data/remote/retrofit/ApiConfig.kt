package com.example.navigationapi_pasliansahatrafael.data.remote.retrofit

import com.example.navigationapi_pasliansahatrafael.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)

            if (!BuildConfig.API_KEY.isNullOrEmpty()) {
                val apiKeyInterceptor = Interceptor { chain ->
                    val originalRequest = chain.request()
                    val newUrl = originalRequest.url.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build()
                    val newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .build()
                    chain.proceed(newRequest)
                }
                okHttpClientBuilder.addInterceptor(apiKeyInterceptor)
            }

            val client = okHttpClientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}