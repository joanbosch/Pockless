package com.pes.pockles.data.api

import com.pes.pockles.BuildConfig
import com.pes.pockles.data.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager constructor(var tokenManager: TokenManager) {

    companion object {
        const val PROD_URL = "https://us-central1-pockles.cloudfunctions.net/api/"
        const val DEV_URL = "http://localhost:5001/pockles/us-central1/api/"
    }

    /**
     * Creates an API service of type [service] and adds interceptors to
     * add necessary data to the headers
     */
    fun <T> createApi(service: Class<T>): T {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

        val appClientInterceptor = Interceptor { chain: Interceptor.Chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("AppClient", "PockleS")
            // TODO: What happens if there's no token but the user is currently logged?
            tokenManager.token?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
            chain.proceed(requestBuilder.build())
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(appClientInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PROD_URL)
            .client(client)
            .build()

        return retrofit.create(service)
    }
}