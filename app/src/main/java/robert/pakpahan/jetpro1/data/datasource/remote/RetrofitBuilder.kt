package robert.pakpahan.jetpro1.data.datasource.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import robert.pakpahan.jetpro1.utils.Constants.TIME_OUT
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    fun <T> service(
        baseUrl: String,
        apiServiceClass: Class<T>,
        okHttpClient: OkHttpClient = provideOkHttpClient()
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(apiServiceClass)
    }
}