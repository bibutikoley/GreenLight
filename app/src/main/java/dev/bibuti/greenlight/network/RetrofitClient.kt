package dev.bibuti.greenlight.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dev.bibuti.greenlight.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val URL = "https://jsonplaceholder.typicode.com"

    val endpoints = networkInstance!!.create<EndpointsInterface>(EndpointsInterface::class.java)

    @Volatile
    private var retrofitInstance: Retrofit? = null

    private val networkInstance: Retrofit?
        get() {
            if (retrofitInstance == null) {
                synchronized(RetrofitClient::class.java) {
                    if (retrofitInstance == null) {
                        retrofitInstance = Retrofit.Builder()
                                .baseUrl(URL)
                                .client(customHttpClient())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                    }
                }
            }
            return retrofitInstance
        }

    private fun customHttpClient(): OkHttpClient {
        val okHttpClient: OkHttpClient
        if (BuildConfig.DEBUG) {
            okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BASIC).loggable(true).build())
                    .build()
        } else {
            okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .build()
        }
        return okHttpClient
    }

}
