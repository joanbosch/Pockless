package com.pes.pockles.data.api

import com.pes.pockles.BuildConfig
import com.pes.pockles.model.Pock
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiService {

    /*
    To create a new API call, just put something like this:

    @METHOD("path")
    fun methodName(variables): Single<ReturnType>

    - METHOD        -> must be GET, PUT, POST, ...
    - path          -> path of the endpoint without the initial slash (/)
    - methodName    -> whatever you want
    - variables     -> whatever you need. If you need an endpoint with a variable on the path, put
                       a variable with @Path("namePath") variableName: typeVariable and in the path
                       put {namePath} where namePath could be whatever, they will link automatically.
                       Example: https://github.com/VictorBG/Raco/blob/master/app/src/main/java/com/victorbg/racofib/data/api/ApiService.java#L63-L65
    - ReturnType    -> The type of the object the API returns, it can be a List (List<ReturnType)

     */

    @POST("pock")
    fun newPock(@Body pock: Pock): Single<Pock>

    @GET("pock")
    fun getNearPocks(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Single<List<Pock>>

    @GET("pock/{id}")
    fun viewPock(@Path("id") id: String): Single<Pock>

    companion object {
        /**
         * Gets the @see [ApiService] ready to use it.
         *
         * Specific headers are added so the API can work correctly (like the AppClient).
         *
         * To call locally change the baseUrl (line 63) to where the local API is running,
         * usually it would be http://localhost:5001/pockles/us-central1/api/ (do not
         * forget the last slash or it will not work). This only works for emulator.
         */
        fun get(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

            val appClientInterceptor = Interceptor { chain: Interceptor.Chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader("AppClient", "PockleS")
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
                .baseUrl("https://us-central1-pockles.cloudfunctions.net/api/")
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}